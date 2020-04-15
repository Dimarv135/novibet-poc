package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.model.Game
import com.example.myapplication.model.Headline
import com.example.myapplication.model.Login
import com.example.myapplication.service.MainService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainRepository {

    val service by lazy {
        MainRepository.create()
    }

    fun login(): Login? {
        val req = service.login()
        Log.d(req.request().toString(), "tag")
        return req.execute().body()
    }

    fun getHeadlines(): Headline? {
        val req = service.getHeadlines()
        Log.d(req.request().toString(), "tag")
        return req.execute().body()
    }

    fun getGames(): Game? {
        val req = service.getGames()
        Log.d(req.request().toString(), "tag")
        return req.execute().body()
    }

    fun getUpdatedHeadlines(): Headline? {
        val req = service.getUpdatedHeadlines()
        Log.d(req.request().toString(), "tag")
        return req.execute().body()
    }

    fun getUpdatedGames(): Game? {
        val req = service.getUpdatedGames()
        Log.d(req.request().toString(), "tag")
        return req.execute().body()
    }

    companion object {
        private val BASE_URL = "http://www.mocky.io/v2"

        fun create(): MainService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(MainService::class.java)
        }
    }
}