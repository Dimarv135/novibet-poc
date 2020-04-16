package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.base.BaseAdapter
import com.example.myapplication.model.Headline
import kotlinx.android.synthetic.main.headline_item.view.*


class MainHorizontalAdapter(private val headline: Headline, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        HorizontalViewHolder(
            LayoutInflater.from(context).inflate(R.layout.headline_item, parent, false)
        )

    override fun getItemCount(): Int {

        return if (headline.size != 0) headline[0].betViews.size else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HorizontalViewHolder).bind(headline)
    }

    inner class HorizontalViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Headline) {
            view.competitor1.text = item[0].betViews[adapterPosition].competitor1Caption
            view.competitor2.text = item[0].betViews[adapterPosition].competitor2Caption
            view.startTime.text = item[0].betViews[adapterPosition].startTime
        }
    }

}