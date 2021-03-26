package com.junpu.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerViewAdapter
 * @author junpu
 * @date 2019-12-31
 */
abstract class RecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val data = arrayListOf<T>()

    val isEmpty = data.isEmpty()

    override fun getItemCount() = data.size

    open fun getItem(position: Int) = if (position in data.indices) data[position] else null
}
