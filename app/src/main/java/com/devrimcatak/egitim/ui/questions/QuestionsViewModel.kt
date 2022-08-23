package com.devrimcatak.egitim.ui.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.CompleteLesson
import com.devrimcatak.egitim.model.courses.Courses
import com.devrimcatak.egitim.model.questions.Questions
import com.devrimcatak.egitim.ui.courses.CoursesRepository
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 26.06.2022.
 */
@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionsRepository): ViewModel(){

    val questionsResponse: MutableLiveData<Questions?> = MutableLiveData()
    val completeLessonResponse: MutableLiveData<CompleteLesson?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData( true)
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun questions(token: String, id: Int) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.questions(token, id)
        when(request){
            is NetworkResult.Success -> {
                val coursesResponse2 = request.data;
                coursesResponse2.let {
                    if (it!!.status!!){
                        questionsResponse.value = request.data
                    } else {
                        onError.value = coursesResponse2?.message
                    }
                }
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = false
            }
        }
    }

    fun completeLesson(token: String, id: Int) = viewModelScope.launch {
        isLoading.value = true
        when(val request = repository.completeLesson(token, id)){
            is NetworkResult.Success -> {
                val coursesResponse2 = request.data;
                coursesResponse2.let {
                    if (it!!.status!!){
                        completeLessonResponse.value = request.data
                    } else {
                        onError.value = coursesResponse2?.message
                    }
                }
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = false
            }
        }
    }


}