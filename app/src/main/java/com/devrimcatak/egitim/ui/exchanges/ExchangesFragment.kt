package com.devrimcatak.egitim.ui.exchanges

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentExchangesBinding
import com.devrimcatak.egitim.databinding.FragmentLessonsBinding
import com.devrimcatak.egitim.model.enums.LessonsCompletedType
import com.devrimcatak.egitim.model.exchanges.Data
import com.devrimcatak.egitim.ui.exchanges.*
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri


@AndroidEntryPoint
class ExchangesFragment : BaseFragment<FragmentExchangesBinding, ExchangesViewModel>(
    FragmentExchangesBinding::inflate) {

    override val viewModel by viewModels<ExchangesViewModel>()

    override fun onCreateFinished() {
        viewModel.exchanges(SharedPreferencesHelper().getToken(requireContext())!!)

        backClick()
    }

    override fun initializeListeners() { }

    override fun observeEvents() {
        with(viewModel){
            exchangesResponse.observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    handleViews(false)
                    response.data?.let { it1 ->
                        setRecycler(it1)
                    }
                }
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                handleViews(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                it?.let { it1 ->
                    Tools().showToast(requireContext(), it1)
                }
                handleViews(false)
            })
        }
    }

    private fun setRecycler(data: List<Data>){
        binding.recyclerExchanges.layoutManager=LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,
                false)
        val mAdapter = ExchangesAdapter(
            requireContext(),
            data, object : ItemClickListener {
                override fun onItemClick(exchange: Data) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(exchange.url)
                    startActivity(i)
                }
            })
        binding.recyclerExchanges.adapter = mAdapter
    }

    private fun handleViews(isLoading: Boolean = false){
        if (isLoading){
            binding.clLoading.visibility = View.VISIBLE
            binding.animationView.playAnimation()
        } else {
            binding.clLoading.visibility = View.GONE
            binding.animationView.pauseAnimation()
        }
    }

    private fun backClick() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}