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


    /* fun login(auth:Muta): Login? {

         CoroutineScope(Dispatchers.IO).launch {
             val req = service.login()

             Log.d(req.toString(), "tag")
             return req.body()
         }
     }*/

    /* fun getHeadlines(): Headline? {
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
     }*/

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