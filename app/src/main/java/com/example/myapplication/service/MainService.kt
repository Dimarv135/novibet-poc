package com.example.myapplication.service

import com.example.myapplication.model.Game
import com.example.myapplication.model.Headline
import com.example.myapplication.model.Login
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface MainService {

    /*  1) token -> http://www.mocky.io/v2/5d8e4bd9310000a2612b5448
      2) games -> http://www.mocky.io/v2/5d7113513300000b2177973a
      3) headlines -> http://www.mocky.io/v2/5d7113ef3300000e00779746
      4) updatedGames -> http://www.mocky.io/v2/5d7114b2330000112177974d
      5) updatedHeadlines -> http://www.mocky.io/v2/5d711461330000d135779748
  */
    @POST("/5d8e4bd9310000a2612b5448")
    fun login(): Call<Login>

    @GET("/5d7113513300000b2177973a")
    fun getGames(): Call<Game>

    @GET("/5d7113ef3300000e00779746")
    fun getHeadlines(): Call<Headline>

    @GET("/5d7114b2330000112177974d")
    fun getUpdatedGames(): Call<Game>

    @GET("/5d711461330000d135779748")
    fun getUpdatedHeadlines(): Call<Headline>

}