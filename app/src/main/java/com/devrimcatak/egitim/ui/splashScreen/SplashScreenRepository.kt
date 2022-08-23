package com.devrimcatak.egitim.ui.splashScreen

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 6.07.2022.
 */
class SplashScreenRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun user(token: String) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.user(newToken)
    }
}