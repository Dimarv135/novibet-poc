package com.example.myapplication.ui.main

import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.base.BaseAdapter
import com.example.myapplication.model.Game

class MainVerticalAdapter : BaseAdapter<Game>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = GameViewHolder(inflate(
        R.layout.headline_item, parent))


    inner class GameViewHolder(override val containerView: View) : BaseViewHolder(containerView) {
        override fun onBindData(item: Game?) {

        }

    }

}