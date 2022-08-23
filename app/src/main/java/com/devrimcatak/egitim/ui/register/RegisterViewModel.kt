package com.devrimcatak.egitim.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.login.Login
import com.devrimcatak.egitim.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    val registerResponse: MutableLiveData<Login?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.register(name, email, password)
        when(request){
            is NetworkResult.Success -> {
                val loginResponse2 = request.data;
                loginResponse2.let {
                    if (loginResponse2!!.status){
                        registerResponse.value = request.data
                    } else {
                        onError.value = loginResponse2.message
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