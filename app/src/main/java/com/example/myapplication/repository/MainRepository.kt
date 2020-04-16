package com.example.myapplication.repository


import com.example.myapplication.service.MainService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainRepository {

    private val service by lazy {
        create()
    }

    suspend fun login(uName:String, passwd:String) = service.login(uName, passwd)

    suspend fun getHeadlines(auth:String) = service.getHeadlines(auth)

    suspend fun getGames(auth:String) = service.getGames(auth)

    suspend fun getUpdatedHeadlines(auth:String) = service.getUpdatedHeadlines(auth)

    suspend fun getUpdatedGames(auth:String) = service.getUpdatedGames(auth)




    companion object {
        private val BASE_URL = "http://www.mocky.io/"

        fun create(): MainService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(MainService::class.java)
        }
    }
}