package com.devrimcatak.egitim.ui.login

import android.util.Log
import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
class LoginRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiRequest {
        apiFactory.login(email,password)
    }

}