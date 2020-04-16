package com.example.myapplication.ui.main

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.*
import com.example.myapplication.model.Game
import com.example.myapplication.model.Headline
import com.example.myapplication.model.Login
import com.example.myapplication.repository.MainRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.thread

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var games = MutableLiveData<Game?>()
    private var headLines = MutableLiveData<Headline?>()
    private var auth: String? = null
    private val user = "Dim"
    private val pass = "123"


    init {
        val login: LiveData<String> = liveData(Dispatchers.IO) {
            val response = repository.login(user,pass)
            auth = "${response.body().token_type} ${response.body().access_token}"
            fetchInitialList(auth)
        }
        startUpdateTask()
    }


    fun fetchInitialList(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                games.value = repository.getGames(auth).body()
                headLines.value = repository.getHeadlines(auth).body()
            }
        }
    }

    fun updateGames(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                games.postValue(repository.getUpdatedGames(auth).body())
            }
        }
    }

    fun updateHeadalines(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                headLines.postValue(repository.getUpdatedHeadlines(auth).body())
            }
        }
    }

    val scheduleTaskExecutor = Executors.newScheduledThreadPool(2)

    fun startUpdateTask() {
        scheduleTaskExecutor.scheduleAtFixedRate({
            run {
                updateGames(auth)
                updateHeadalines(auth)
            }
        }, 20, 2, TimeUnit.SECONDS)

    }

    companion object {
        val repository = MainRepository()
    }

}
