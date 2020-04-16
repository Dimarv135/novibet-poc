package com.example.myapplication.service

import com.example.myapplication.model.Game
import com.example.myapplication.model.Headline
import com.example.myapplication.model.Login
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainService {

    /*  1) token -> http://www.mocky.io/v2/5d8e4bd9310000a2612b5448
      2) games -> http://www.mocky.io/v2/5d7113513300000b2177973a
      3) headlines -> http://www.mocky.io/v2/5d7113ef3300000e00779746
      4) updatedGames -> http://www.mocky.io/v2/5d7114b2330000112177974d
      5) updatedHeadlines -> http://www.mocky.io/v2/5d711461330000d135779748
  */
    @POST("/5d8e4bd9310000a2612b5448")
    suspend fun login(@Field("“userName”") userName: String, @Field("password") passwd: String): Response<Login>

    @GET("/5d7113513300000b2177973a")
    suspend fun getGames(@Header("Authorization") auth:String): Response<Game>

    @GET("/5d7113ef3300000e00779746")
    suspend fun getHeadlines(@Header("Authorization") auth:String): Response<Headline>

    @GET("/5d7114b2330000112177974d")
    suspend fun getUpdatedGames(@Header("Authorization") auth:String): Response<Game>

    @GET("/5d711461330000d135779748")
    suspend fun getUpdatedHeadlines(@Header("Authorization") auth:String): Response<Headline>

}