package com.devrimcatak.egitim.ui.register

import android.util.Log
import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
class RegisterRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun register(name: String, email: String, password: String) = safeApiRequest {
        apiFactory.register(name, email, password)
    }

}