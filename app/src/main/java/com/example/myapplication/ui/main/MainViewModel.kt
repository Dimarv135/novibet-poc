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
                val reqGames = repository.getGames(it)
                val reqHeadline = repository.getHeadlines(it)

                val game = reqGames.body()
                game?.let {
                    val list: MutableList<GameViewData> = mutableListOf()
                    for (competition in it[0].betViews[0].competitions) {
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
                    for (betview in it[0].betViews) {
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
                val req = repository.getUpdatedGames(it)

                val game = req.body()
                game?.let {
                    val list: MutableList<GameViewData> = mutableListOf()
                    for (competition in it[0].betViews[0].competitions) {
                        for (event in competition.events) {

                            gamesListLD.value!!.any { item -> item.id != event.betContextId }.let {
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
                val req = repository.getUpdatedHeadlines(it)
                val headline = req.body()
                headline?.let {
                    val list = mutableListOf<HeadlineViewData>()
                    for (betview: BetView? in it[0].betViews) {
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
