package com.devrimcatak.egitim.ui.exchanges

import com.devrimcatak.egitim.base.BaseRepository
import com.devrimcatak.egitim.network.ApiFactory
import javax.inject.Inject


/**
 * Created by @Devrim Çatak on 9.07.2022.
 */
class ExchangesRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun exchanges(token: String) = safeApiRequest {
        val newToken = "Bearer $token"
        apiFactory.exchanges(newToken)
    }
}