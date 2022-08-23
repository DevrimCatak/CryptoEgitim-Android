package com.devrimcatak.egitim.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.databinding.ActivityMainBinding
import com.devrimcatak.egitim.databinding.MainMenuBinding
import com.devrimcatak.egitim.model.EventBusModel
import com.devrimcatak.egitim.model.enums.EventBusType
import com.devrimcatak.egitim.model.enums.EventBusType.*
import com.devrimcatak.egitim.ui.exchanges.ExchangesFragment
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mobeedev.library.SlidingMenuBuilder
import com.mobeedev.library.SlidingNavigation
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode

import org.greenrobot.eventbus.Subscribe




@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    private lateinit var slidingNavigation : SlidingNavigation
    lateinit var binding : ActivityMainBinding
    private var mRewardedAd: RewardedAd? = null
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        slidingNavigation = SlidingMenuBuilder(this@MainActivity)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(false)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.main_menu)
            .inject()

        MobileAds.initialize(this) {}
        loadAd()

    }

    private fun loadAd(){
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this,"ca-app-pub-7144460067251020/4404214400", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    private fun showMenu(){
        slidingNavigation.openMenu()
        val llExchange = findViewById<LinearLayout> (R.id.llExchanges)
        val llLogout = findViewById<LinearLayout> (R.id.llLogout)

        llExchange.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.exchangesFragment)
            slidingNavigation.closeMenu()
        }

        llLogout.setOnClickListener {
            showExitConfirmation()
        }
    }

    private fun showExitConfirmation() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_exit_confirmation)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textNo = dialog.findViewById<TextView>(R.id.textNo)
        val textYes = dialog.findViewById<TextView>(R.id.textYes)

        textNo.setOnClickListener { dialog.dismiss() }
        textYes.setOnClickListener {
            SharedPreferencesHelper().clearValues(this)
        }
        dialog.show()
        val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusModel?) {
        when(event?.name) {
            MenuShow -> {
                if (event.booleanValue!!) {
                    showMenu()
                }
            }
            LessonComplete -> {
                if (mRewardedAd != null) {
                    mRewardedAd?.show(this, OnUserEarnedRewardListener() {
                        fun onUserEarnedReward(rewardItem: RewardItem) {
                            var rewardAmount = rewardItem.amount
                            var rewardType = rewardItem.type
                            Log.d(TAG, "User earned the reward.")
                        }
                    })
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.")
                }
            }
            OpenExchanges -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.exchangesFragment)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
            window.decorView.rootView.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

}