package com.devrimcatak.egitim.ui.splashScreen

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentSplashScreenBinding
import com.devrimcatak.egitim.ui.register.RegisterFragmentDirections
import com.devrimcatak.egitim.ui.register.RegisterViewModel
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>(FragmentSplashScreenBinding::inflate) {

    override val viewModel by viewModels<SplashScreenViewModel>()

    override fun onCreateFinished() {
        if (SharedPreferencesHelper().getLoginStatus(requireContext())) {
            Log.d("DEVRIM22", SharedPreferencesHelper().getToken(requireContext())!!)
            viewModel.user(SharedPreferencesHelper().getToken(requireContext())!!)
        } else {
            val navigation = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
            Navigation.findNavController(requireView()).navigate(navigation)
        }
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel){
            userResponse.observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    //handleViews(false)
                    SharedPreferencesHelper().setLoginStatus(requireContext(), true)
                    val navigation = SplashScreenFragmentDirections.actionSplashScreenFragmentToCoursesFragment()
                    Navigation.findNavController(requireView()).navigate(navigation)
                }
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                //handleViews(it)
            })
            onError.observe(viewLifecycleOwner, Observer {
                SharedPreferencesHelper().setLoginStatus(requireContext(), true)
                SharedPreferencesHelper().clearValues(requireContext())
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                val navigation = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
                Navigation.findNavController(requireView()).navigate(navigation)
                //handleViews(false)
            })
        }
    }
}