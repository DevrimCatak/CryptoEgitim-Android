package com.devrimcatak.egitim.ui.lessons

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentLessonsBinding
import com.devrimcatak.egitim.model.enums.LessonsCompletedType
import com.devrimcatak.egitim.model.lessons.Data
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class LessonsFragment : BaseFragment<FragmentLessonsBinding, LessonsViewModel>(
    FragmentLessonsBinding::inflate) {

    override val viewModel by viewModels<LessonsViewModel>()
    private val args by navArgs<LessonsFragmentArgs>()
    var firstNotCompletedLessons = false

    override fun onCreateFinished() {
        viewModel.lessons(SharedPreferencesHelper().getToken(requireContext())!!, args.courseId)

        backClick()
    }

    override fun initializeListeners() { }

    override fun observeEvents() {
        with(viewModel){
            lessonsResponse.observe(viewLifecycleOwner, Observer {
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
        for (i in data.indices){
            if (data[i].completed == 1){
                data[i].completedType = LessonsCompletedType.Complete
            } else {
                if (!firstNotCompletedLessons) {
                    data[i].completedType = LessonsCompletedType.OpenLessons
                    firstNotCompletedLessons = true
                } else {
                    data[i].completedType = LessonsCompletedType.NotComplete
                }
            }
        }
        firstNotCompletedLessons = false

        binding.recyclerLessons.layoutManager= LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val mAdapter = LessonsAdapter(requireContext(),data,object: ItemClickListener {
            override fun onItemClick(lesson: Data) {
                val navigation = LessonsFragmentDirections.actionLessonsFragmentToLessonFragment(lesson.id!!, lesson.title!!)
                Navigation.findNavController(requireView()).navigate(navigation)
            }
        })
        binding.recyclerLessons.adapter = mAdapter
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
        binding.textTitle.text = args.title
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}