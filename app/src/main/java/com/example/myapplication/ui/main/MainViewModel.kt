package com.example.myapplication.ui.main

import androidx.lifecycle.*
import com.example.myapplication.model.BetView
import com.example.myapplication.model.GameViewData
import com.example.myapplication.model.Headline
import com.example.myapplication.model.HeadlineViewData
import com.example.myapplication.repository.MainRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {
    //var games = MutableLiveData<Game?>()

    var headLines = MutableLiveData<Headline?>()
    var headlineListLD = MutableLiveData<MutableList<HeadlineViewData>>()
    private var auth: String? = null
    private val user = "Dim"
    private val pass = "123"
    var gamesListLD = MutableLiveData<MutableList<GameViewData>>()
    var gamelist: MutableList<GameViewData> = mutableListOf()

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
                    val list: MutableList<GameViewData> = mutableListOf()
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
                    gamelist.addAll(list)
                    gamesListLD.postValue(list)
                    //games.postValue(req.body())

                }
                val headline = reqHeadline.body()
                headline?.let {
                    val list = mutableListOf<HeadlineViewData>()
                    for (betview in headline[0].betViews) {
                        list.add(
                            HeadlineViewData(
                                betview.competitor1Caption,
                                betview.competitor2Caption,
                                betview.startTime
                            )
                        )
                    }
                    headlineListLD.postValue(list)
                }
            }
        }
    }

    private fun updateGames(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedGames(auth)

                val game = req.body()
                game?.let {
                    val list: MutableList<GameViewData> = mutableListOf()
                    for (competition in game[0].betViews[0].competitions) {
                        for (event in competition.events) {

                            if (gamesListLD.value!!.any { item -> item.id == event.betContextId }) {

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

                    gamelist.addAll(list)

                    if (gamesListLD.value != gamelist) gamesListLD.postValue(gamelist)

                }
            }
        }
    }

    private fun updateHeadalines(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedHeadlines(auth)
                val headline = req.body()
                headline?.let {
                    val list = mutableListOf<HeadlineViewData>()
                    for (betview: BetView? in headline[0].betViews) {
                        betview?.competitor1Caption?.let {
                            list.add(
                                HeadlineViewData(
                                    betview.competitor1Caption,
                                    betview.competitor2Caption,
                                    betview.startTime
                                )
                            )
                        }
                    }
                    if (headlineListLD.value != list) headlineListLD.postValue(list)
                }
                // headLines.postValue(req.body())
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
