package com.devrimcatak.egitim.base

import com.devrimcatak.egitim.model.ErrorResponse
import com.devrimcatak.egitim.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest: suspend () -> T): NetworkResult<T>{
        return withContext(Dispatchers.IO){
            try {
                NetworkResult.Success(apiRequest.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        NetworkResult.Error(false, errorBodyParser(throwable.response()?.errorBody()?.string()))
                    }
                    else ->  NetworkResult.Error(true,throwable.localizedMessage)
                }
            }
        }
    }
}

private fun errorBodyParser(error: String?): String{
    error?.let {
        return try {
            val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
            val errorMessage = errorResponse.message
            errorMessage ?: "Bilinmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz."
        }catch (e: Exception){
            "Bilinmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz."
        }
    }
    return "Bilinmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz."
}