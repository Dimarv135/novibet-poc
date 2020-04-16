package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.HeadlineViewData
import kotlinx.android.synthetic.main.headline_item.view.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MainHorizontalAdapter(
    private val context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var headline: List<HeadlineViewData> = listOf()
    private var recyclerView: RecyclerView? = null

    fun updateHeadlineItems(headline: List<HeadlineViewData>?) {
        headline?.let {
            this.headline = it
            notifyDataSetChanged()
        }
    }

    init {
        startUpdateTask()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        HorizontalViewHolder(
            LayoutInflater.from(context).inflate(R.layout.headline_item, parent, false)
        )

    override fun getItemCount(): Int {
        return if (headline.isNotEmpty()) headline.size else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (headline.isNotEmpty()) (holder as HorizontalViewHolder).bind(headline[position])
    }

    inner class HorizontalViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: HeadlineViewData) {
            view.competitor1.text = item.competitor1
            view.competitor2.text = item.competitor2
            view.startTime.text = item.time
        }
    }

    private fun startUpdateTask() {
        val scheduleTaskExecutor = Executors.newScheduledThreadPool(2)
        var newPosition = 1
        scheduleTaskExecutor.scheduleAtFixedRate({
            run {
                if (newPosition + 1 > headline.size) newPosition = 0

                recyclerView?.smoothScrollToPosition(newPosition)
                newPosition++
            }
        }, 25, 5, TimeUnit.SECONDS)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}