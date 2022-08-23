package com.devrimcatak.egitim.ui.lessons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.lessons.Lessons
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */

@HiltViewModel
class LessonsViewModel @Inject constructor(private val repository: LessonsRepository): ViewModel() {

    val lessonsResponse: MutableLiveData<Lessons?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData( true)
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun lessons(token: String, id: Int) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.lessons(token, id)
        when(request){
            is NetworkResult.Success -> {
                val coursesResponse2 = request.data;
                coursesResponse2.let {
                    if (it!!.status!!){
                        lessonsResponse.value = request.data
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