package com.example.myapplication.base


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer



abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder> {

    protected var data: MutableList<T?>
    protected lateinit var context: Context

    constructor() {
        this.data = mutableListOf()
    }

    constructor(data: MutableList<T?>) {
        this.data = data
    }

    abstract fun createItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        abstract fun onBindData(item: T?)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindData(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return createItemViewHolder(parent, viewType)
    }

    protected fun inflate(@LayoutRes id: Int, container: ViewGroup): View {
        context = container.context
        return LayoutInflater.from(context).inflate(id, container, false)
    }

    fun set(data: MutableList<T?>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun set(item: T?) {
        val index = data.indexOf(item)
        data[index] = item
        notifyItemChanged(index)
    }

    fun set(index: Int, item: T?) {
        data[index] = item
        notifyItemChanged(index)
    }

    fun get(): List<T?> {
        return data
    }

    fun get(index: Int): T? {
        return data[index]
    }

    fun appendData(data: List<T?>) {
        val size = itemCount
        this.data.addAll(data)
        notifyItemRangeInserted(size, itemCount)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: T?) {
        val size = itemCount
        data.add(item)
        notifyItemRangeInserted(size, itemCount)
    }

    fun removeItem(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeItem(item: T?) {
        val position = data.indexOf(item)
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun notifyItemChanged(item: T?) {
        notifyItemChanged(data.indexOf(item))
    }
}