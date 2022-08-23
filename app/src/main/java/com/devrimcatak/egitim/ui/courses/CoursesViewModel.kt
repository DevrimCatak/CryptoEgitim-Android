package com.devrimcatak.egitim.ui.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.courses.Courses
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 31.05.2022.
 */
@HiltViewModel
class CoursesViewModel @Inject constructor(private val repository: CoursesRepository): ViewModel() {

    val coursesResponse: MutableLiveData<Courses?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData( true)
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun login(token: String) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.courses(token)
        when(request){
            is NetworkResult.Success -> {
                val coursesResponse2 = request.data;
                coursesResponse2.let {
                    if (it!!.status!!){
                        coursesResponse.value = request.data
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