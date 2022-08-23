package com.devrimcatak.egitim.ui.lesson

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */
class LessonRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun lessons(token: String, id: Int) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.lesson(newToken, id)
    }
}