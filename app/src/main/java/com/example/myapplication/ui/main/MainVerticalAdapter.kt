package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.GameViewData
import com.example.myapplication.model.Headline
import com.example.myapplication.model.HeadlineViewData
import kotlinx.android.synthetic.main.game_item.view.*
import kotlinx.android.synthetic.main.horizontal_recycler.view.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainVerticalAdapter(
    val context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var games: List<GameViewData> = listOf()
    var horizontalAdapter = MainHorizontalAdapter(context)
    var layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


    fun updateHeadline(headline: List<HeadlineViewData>?) {
        headline?.let {
            horizontalAdapter.updateHeadlineItems(headline)
        }
    }


    fun updateGames(games: List<GameViewData>?) {
        games?.let {
            this.games = games
            notifyDataSetChanged()
        }

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
        var count = 1
        if (games.isNotEmpty()) {
            count += games.size
        }

        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GameViewHolder) {
            holder.bind(games[position - 1])
        } else if (holder is HorizontalViewHolder) {
            holder.bind()
        }
    }

    inner class GameViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: GameViewData) {
            view.gameCompetitor1.text = item.competitor1

            view.gameCompetitor2.text = item.competitor2

            view.elapsed.text = item.elapsed.substring(0, 8)
        }
    }

    inner class HorizontalViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            view.horizRecycler.layoutManager = layoutManager
            view.horizRecycler.adapter = horizontalAdapter
        }
    }
}

