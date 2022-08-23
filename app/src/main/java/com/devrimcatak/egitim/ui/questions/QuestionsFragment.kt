package com.devrimcatak.egitim.ui.questions

import android.os.Bundle
import android.util.Log
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
import com.devrimcatak.egitim.databinding.FragmentQuestionsBinding
import com.devrimcatak.egitim.model.EventBusModel
import com.devrimcatak.egitim.model.enums.EventBusType
import com.devrimcatak.egitim.model.enums.LessonsCompletedType
import com.devrimcatak.egitim.model.enums.QuestionAnswersType
import com.devrimcatak.egitim.model.questions.Answer
import com.devrimcatak.egitim.model.questions.Data
import com.devrimcatak.egitim.ui.lesson.LessonFragmentDirections
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class QuestionsFragment : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>(FragmentQuestionsBinding::inflate) {

    override val viewModel by viewModels<QuestionsViewModel>()
    private val args by navArgs<QuestionsFragmentArgs>()
    lateinit var answersList : List<Answer>
    lateinit var questionsList : List<Data>
    var showingAnswer = 0
    var mAdapter : QuestionsAdapter? = null
    var answerClick = false

    override fun onCreateFinished() {
        viewModel.questions(SharedPreferencesHelper().getToken(requireContext())!!, args.lessonId)
        nextClick()
    }

    override fun initializeListeners() { }

    override fun observeEvents() {
        with(viewModel){
            questionsResponse.observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    handleViews(false)
                    response.data?.let { it1 ->
                        setRecycler(it1)
                        binding.progressBar.max = response.data.size
                        answersList = response.data[0].answers!!
                        questionsList = response.data
                        binding.textTitle.text = response.data[0].question
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
            completeLessonResponse.observe(viewLifecycleOwner, Observer {
                if (it?.status!!) {
                    Tools().showToast(requireContext(), it.message!!)
                    val navigation = QuestionsFragmentDirections.actionQuestionsFragmentToLessonsFragment(SharedPreferencesHelper().getSelectedCourse(requireContext()))
                    Navigation.findNavController(requireView()).navigate(navigation)
                    EventBus.getDefault().post(EventBusModel(EventBusType.LessonComplete, "", true))
                }
            })
        }
    }

    private fun setRecycler(data: List<Data>){
        binding.recyclerQuestions.layoutManager= LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mAdapter = data[showingAnswer].answers?.let {
            QuestionsAdapter(requireContext(), it, object: ItemClickListener {
                override fun onItemClick(answer: Answer) {
                    if (!answerClick) {
                        for (i in answersList.indices){
                            if (answer == answersList[i]) {
                                if (answer.correct.equals("1")) {
                                    answersList[i].answersType = QuestionAnswersType.True
                                } else {
                                    answersList[i].answersType = QuestionAnswersType.Wrong
                                }
                                answerClick = true
                            }
                        }
                        mAdapter?.notifyDataSetChanged()
                        binding.btnNextQuestion.isEnabled = true
                        binding.btnNextQuestion.setBackgroundResource(R.drawable.rounded_orange)
                    }
                }
            })
        }
        binding.recyclerQuestions.adapter = mAdapter
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

    private fun nextClick(){
        binding.btnNextQuestion.setOnClickListener {
            if (showingAnswer + 1 == questionsList.size) {
                handleViews(true)
                viewModel.completeLesson(SharedPreferencesHelper().getToken(requireContext())!!, args.lessonId)
            } else {
                showingAnswer += 1
                setRecycler(questionsList)
                binding.textTitle.text = questionsList[showingAnswer].question
                binding.progressBar.progress = showingAnswer + 1
                answerClick = false
                answersList = questionsList[showingAnswer].answers!!
                binding.btnNextQuestion.isEnabled = false
                binding.btnNextQuestion.setBackgroundResource(R.drawable.rounded_orange_enabled)
            }

        }
    }
}