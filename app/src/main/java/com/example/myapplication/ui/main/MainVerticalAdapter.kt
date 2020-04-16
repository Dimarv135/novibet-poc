package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.base.BaseAdapter
import com.example.myapplication.model.Game
import com.example.myapplication.model.GameLiveData
import com.example.myapplication.model.GameViewData
import com.example.myapplication.model.Headline
import kotlinx.android.synthetic.main.game_item.*
import kotlinx.android.synthetic.main.game_item.view.*
import kotlinx.android.synthetic.main.horizontal_recycler.view.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainVerticalAdapter(var games: List<GameViewData>, var headline: Headline, val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateHeadline(headline: Headline?) {
        headline?.let {
            this.headline = headline
        }
        notifyItemRangeChanged(0,itemCount)
    }

    fun updateGames(games: List<GameViewData>?) {
        games?.let {
            this.games = games
        }
        notifyItemRangeChanged(1,itemCount)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADLINE_TYPE else GAME_TYPE
    }

    companion object {
        private const val HEADLINE_TYPE = 0
        private const val GAME_TYPE = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == HEADLINE_TYPE) {
            HorizontalViewHolder(
                LayoutInflater.from(context).inflate(R.layout.horizontal_recycler, parent, false)
            )
        } else {
            GameViewHolder(LayoutInflater.from(context).inflate(R.layout.game_item, parent, false))
        }

    }

    override fun getItemCount(): Int {
     var count=1
        if (games.isNotEmpty()){
            count+=games.size
        }

     return   count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GameViewHolder) {
            holder.bind(games[position-1])
        } else if (holder is HorizontalViewHolder) {
            holder.bind(headline)
        }
    }

    inner class GameViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: GameViewData) {
            view.gameCompetitor1.text = item.competitor1

            view.gameCompetitor2.text = item.competitor2

            view.elapsed.text = item.elapsed.substring(0,8)
        }
    }

    inner class HorizontalViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Headline) {

            view.horizRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            view.horizRecycler.adapter = MainHorizontalAdapter(headline, context)
        }
    }
}

