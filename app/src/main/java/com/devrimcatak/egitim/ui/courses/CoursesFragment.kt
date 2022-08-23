package com.devrimcatak.egitim.ui.courses

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentCoursesBinding
import com.devrimcatak.egitim.model.EventBusModel
import com.devrimcatak.egitim.model.courses.Data
import com.devrimcatak.egitim.model.enums.EventBusType
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import java.lang.Exception
import org.greenrobot.eventbus.EventBus




@AndroidEntryPoint
class CoursesFragment : BaseFragment<FragmentCoursesBinding, CoursesViewModel>(FragmentCoursesBinding::inflate) {

    override val viewModel by viewModels<CoursesViewModel>()

    override fun onCreateFinished() {
        viewModel.login(SharedPreferencesHelper().getToken(requireContext())!!)
        menuClick()
    }

    private fun menuClick() {
        binding.imgMenu.setOnClickListener {
            EventBus.getDefault().post(EventBusModel(EventBusType.MenuShow, "", true))
        }

        binding.imgExchanges.setOnClickListener {
            EventBus.getDefault().post(EventBusModel(EventBusType.OpenExchanges, "", true))
        }
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel){
            coursesResponse.observe(viewLifecycleOwner, Observer {
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
        try {
            binding.recyclerCourses.onFlingListener = null
            binding.recyclerCourses.layoutManager = SkidRightLayoutManager(1.7f, 0.95f)
        } catch (e: Exception) {
            Log.d("Recycler","SkidRight",e)
        }

        val mAdapter = CoursesAdapter(requireContext(),data,object: ItemClickListener {
            override fun onItemClick(course: Data) {
                SharedPreferencesHelper().setSelectedCourse(context!!, course.id!!)
                val navigation = CoursesFragmentDirections.actionCoursesFragmentToLessonsFragment(course.id!!, course.title!!)
                Navigation.findNavController(requireView()).navigate(navigation)
            }
        })
        binding.recyclerCourses.adapter = mAdapter
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
}