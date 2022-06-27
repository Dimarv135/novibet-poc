package com.example.myapplication.ui.main

import androidx.lifecycle.*
import com.example.myapplication.model.BetView
import com.example.myapplication.model.GameViewData
import com.example.myapplication.model.HeadlineViewData
import com.example.myapplication.repository.MainRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {

    var headlineListLD = MutableLiveData<MutableList<HeadlineViewData>>()
    var auth: String? = null
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


     fun fetchInitialList(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val reqGames = repository.getGames(it)
                val reqHeadline = repository.getHeadlines(it)

                val game = reqGames.body()
                game?.let {
                    val list: MutableList<GameViewData> = mutableListOf()

                    it[0].betViews[0].competitions.flatMap { it.events}.map {event -> list.add(
                        GameViewData(
                            event.betContextId,
                            event.additionalCaptions.competitor1,
                            event.additionalCaptions.competitor2,
                            event.liveData.elapsed
                        )) }

                    gamelist.addAll(list)
                    gamesListLD.postValue(list)

                }
                val headline = reqHeadline.body()
                headline?.let {
                    val list = mutableListOf<HeadlineViewData>()
                     it[0].betViews.map {betview->
                         betview?.let {
                             list.add(
                                 HeadlineViewData(
                                     it.competitor1Caption,
                                     it.competitor2Caption,
                                     it.startTime
                                 )
                             )
                         }
                    }
                    headlineListLD.postValue(list)
                }
            }
        }
    }

     fun updateGames(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedGames(it)

                val game = req.body()
                game?.let {
                    val list: MutableList<GameViewData> = mutableListOf()
                    it[0].betViews[0].competitions.flatMap {competition-> competition.events }.map { event ->
                        gamesListLD.value?.any { item -> item.id != event.betContextId }.let {
                            list.add(
                                GameViewData(
                                    event.betContextId,
                                    event.additionalCaptions.competitor1,
                                    event.additionalCaptions.competitor2,
                                    event.liveData.elapsed
                                )
                            )
                        }
                    }.asSequence()

                    gamelist.addAll(list)

                    if (gamesListLD.value != gamelist) gamesListLD.postValue(gamelist)

                }
            }
        }
    }

    private fun updateHeadlines(auth: String?) {
        auth?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val req = repository.getUpdatedHeadlines(it)
                val headline = req.body()
                headline?.let {
                    val list = mutableListOf<HeadlineViewData>()
                    it[0].betViews.map {betview ->
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
            }
        }
    }


    private fun startUpdateTask() {
        val scheduleTaskExecutor = Executors.newScheduledThreadPool(2)
        scheduleTaskExecutor.scheduleAtFixedRate({
            run {
                updateGames(auth)
                updateHeadlines(auth)
            }
        }, 20, 2, TimeUnit.SECONDS)

    }

    companion object {
        val repository = MainRepository()
    }

}
