package com.devrimcatak.egitim.ui.exchanges

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.exchanges.Exchanges
import com.devrimcatak.egitim.ui.lessons.LessonsRepository
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 9.07.2022.
 */
@HiltViewModel
class ExchangesViewModel @Inject constructor(private val repository: ExchangesRepository): ViewModel() {

    val exchangesResponse: MutableLiveData<Exchanges?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData( true)
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun exchanges(token: String) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.exchanges(token)
        when(request){
            is NetworkResult.Success -> {
                val coursesResponse2 = request.data;
                coursesResponse2.let {
                    if (it!!.status!!){
                        exchangesResponse.value = request.data
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