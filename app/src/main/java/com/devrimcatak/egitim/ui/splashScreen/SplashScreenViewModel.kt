package com.devrimcatak.egitim.ui.splashScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrimcatak.egitim.model.login.Login
import com.devrimcatak.egitim.model.userControl.User
import com.devrimcatak.egitim.ui.register.RegisterRepository
import com.devrimcatak.egitim.utils.NetworkResult
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 6.07.2022.
 */
@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val repository: SplashScreenRepository): ViewModel() {

    val userResponse: MutableLiveData<User?> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun user(token: String) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.user(token)
        when(request){
            is NetworkResult.Success -> {
                val userResponseData = request.data;
                userResponseData.let {
                    if (userResponseData!!.status){
                        userResponse.value = request.data
                    } else {
                        onError.value = userResponseData.message
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