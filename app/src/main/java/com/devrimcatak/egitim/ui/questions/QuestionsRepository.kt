package com.devrimcatak.egitim.ui.questions

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 26.06.2022.
 */
class QuestionsRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun questions(token: String, id: Int) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.questions(newToken, id)
    }

    suspend fun completeLesson(token: String, id: Int) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.completeLesson(newToken, id)
    }

}