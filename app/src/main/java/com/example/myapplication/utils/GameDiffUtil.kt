package com.example.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.model.GameViewData


class GameDiffUtil(oldList: List<GameViewData>, newList: List<GameViewData>) :
    DiffUtil.Callback() {
    private val oldGameList: List<GameViewData>
    private val newGameList: List<GameViewData>
    override fun getOldListSize(): Int {
        return oldGameList.size
    }

    override fun getNewListSize(): Int {
        return newGameList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGameList[oldItemPosition].id === newGameList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGame: GameViewData = oldGameList[oldItemPosition]
        val newGame: GameViewData = newGameList[newItemPosition]
        return oldGame.competitor1 == newGame.competitor1 && oldGame.competitor2 == newGame.competitor2 && oldGame.elapsed == newGame.elapsed
    }


    init {
        oldGameList = oldList
        newGameList = newList
    }
}