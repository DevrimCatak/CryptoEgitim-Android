package com.devrimcatak.egitim.ui.lessons

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */
class LessonsRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun lessons(token: String, id: Int) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.lessons(newToken, id)
    }
}