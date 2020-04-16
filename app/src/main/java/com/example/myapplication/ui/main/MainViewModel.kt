package com.example.myapplication.ui.main

import androidx.lifecycle.*
import com.example.myapplication.model.Game
import com.example.myapplication.model.GameViewData
import com.example.myapplication.model.Headline
import com.example.myapplication.repository.MainRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {
    var games = MutableLiveData<Game?>()

    var headLines = MutableLiveData<Headline?>()
    private var auth: String? = null
    private val user = "Dim"
    private val pass = "123"
    var gamesList = MutableLiveData<MutableList<GameViewData>>()


    init {
        viewModelScope.launch {
            val response = repository.login(user, pass)
            auth = "${response.body()?.token_type} ${response.body()?.access_token}"
            fetchInitialList(auth)
        }
        startUpdateTask()
    }


    private fun fetchInitialList(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val reqGames = repository.getGames(auth)
                val reqHeadline = repository.getHeadlines(auth)

                val game = reqGames.body()
                game?.let {
                    var list: MutableList<GameViewData> = mutableListOf()
                    for (competition in game[0].betViews[0].competitions) {
                        for (event in competition.events) {
                            list.add(
                                GameViewData(
                                    event.betContextId,
                                    event.additionalCaptions.competitor1,
                                    event.additionalCaptions.competitor2,
                                    event.liveData.elapsed
                                )
                            )
                        }
                    }
                    gamesList.postValue(list)
                    //games.postValue(req.body())

                }
                //games.postValue(reqGames.body())
                headLines.postValue(reqHeadline.body())
            }
        }
    }

    private fun updateGames(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedGames(auth)
                //item[0].betViews[0].competitions[adapterPosition-1].events[0].additionalCaptions
                val game = req.body()
                game?.let {
                    var list: MutableList<GameViewData> = mutableListOf()
                    for (competition in game[0].betViews[0].competitions) {
                        for (event in competition.events) {
                            if (gamesList.value!!.any { item -> item.id == event.betContextId }) {

                            } else {
                                list.add(
                                    GameViewData(
                                        event.betContextId,
                                        event.additionalCaptions.competitor1,
                                        event.additionalCaptions.competitor2,
                                        event.liveData.elapsed
                                    )
                                )
                            }
                        }
                    }
                    withContext(Dispatchers.Main) {
                        gamesList.value?.addAll(list)
                    }

                    //games.postValue(req.body())

                }
            }
        }
    }

    private fun updateHeadalines(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedHeadlines(auth)
                headLines.postValue(req.body())
            }
        }
    }


    private fun startUpdateTask() {
        val scheduleTaskExecutor = Executors.newScheduledThreadPool(2)
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
