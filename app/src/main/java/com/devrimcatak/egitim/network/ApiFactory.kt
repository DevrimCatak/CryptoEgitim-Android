package com.devrimcatak.egitim.network

import com.devrimcatak.egitim.model.CompleteLesson
import com.devrimcatak.egitim.model.courses.Courses
import com.devrimcatak.egitim.model.exchanges.Exchanges
import com.devrimcatak.egitim.model.lesson.Lesson
import com.devrimcatak.egitim.model.lessons.Lessons
import com.devrimcatak.egitim.model.login.Login
import com.devrimcatak.egitim.model.questions.Questions
import com.devrimcatak.egitim.model.userControl.User
import retrofit2.http.*


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
interface ApiFactory {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Login

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Login

    @GET("user")
    suspend fun user(
        @Header("Authorization") token: String
    ) : User

    @GET("courses")
    suspend fun courses(
        @Header("Authorization") token: String
    ) : Courses

    @GET("lessons/{id}")
    suspend fun lessons(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Lessons

    @GET("lesson/{id}")
    suspend fun lesson(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Lesson

    @GET("questions/{id}")
    suspend fun questions(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Questions

    @GET("complete_lesson/{id}")
    suspend fun completeLesson(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : CompleteLesson

    @GET("exchanges")
    suspend fun exchanges(
        @Header("Authorization") token: String
    ) : Exchanges
}