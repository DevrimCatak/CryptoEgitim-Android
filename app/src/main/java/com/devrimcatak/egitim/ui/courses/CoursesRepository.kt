package com.devrimcatak.egitim.ui.courses

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
class CoursesRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun courses(token: String) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.courses(newToken)
    }

}