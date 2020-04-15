package com.example.myapplication.ui.main

import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.base.BaseAdapter
import com.example.myapplication.model.Headline


class MainHorizontalAdapter : BaseAdapter<Headline>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = HeadlineViewHolder(inflate(R.layout.headline_item, parent))


    inner class HeadlineViewHolder(override val containerView: View) : BaseViewHolder(containerView) {
        override fun onBindData(item: Headline?) {

        }

    }

}