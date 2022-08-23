package com.devrimcatak.egitim.ui.lesson

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentLessonBinding
import com.devrimcatak.egitim.ui.lessons.LessonsFragmentDirections
import com.devrimcatak.egitim.utils.Constants.WEB_URL_LESSON
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonFragment : BaseFragment<FragmentLessonBinding, LessonViewModel>(
    FragmentLessonBinding::inflate) {

    override val viewModel by viewModels<LessonViewModel>()
    private val args by navArgs<LessonFragmentArgs>()

    override fun onCreateFinished() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(WEB_URL_LESSON+args.lessonId)

        examClick()
        backClick()
    }

    override fun initializeListeners() {  }

    override fun observeEvents() { }

    private fun examClick(){
        binding.btnStartExam.setOnClickListener {
            val navigation = LessonFragmentDirections.actionLessonFragmentToQuestionsFragment(args.lessonId!!)
            Navigation.findNavController(requireView()).navigate(navigation)
        }
    }

    private fun backClick() {
        binding.textTitle.text = args.title
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}