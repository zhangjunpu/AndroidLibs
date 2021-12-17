package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter
 * @author junpu
 * @date 2019-12-31
 */
abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    val data = arrayListOf<T>()
    val isEmpty = data.isEmpty()

    protected var listener: ((View?, Int) -> Unit)? = null
    protected var childListener: ((View?, Int) -> Unit)? = null
    protected var longClickListener: ((View?, Int) -> Unit)? = null

    override fun getItemCount() = data.size
    open fun getItem(position: Int) = if (position in data.indices) data[position] else null

    fun doOnItemClick(listener: (View?, Int) -> Unit) {
        this.listener = listener
    }

    fun doOnChildClick(listener: (View?, Int) -> Unit) {
        this.childListener = listener
    }

    fun doOnItemLongClick(listener: (View?, Int) -> Unit) {
        this.longClickListener = listener
    }

    fun update(list: List<T>?) {
        data.clear()
        list?.let { data.addAll(it) }
        notifyDataSetChanged()
    }

    fun addAll(list: List<T>?) {
        list?.let {
            val start = data.size
            data.addAll(it)
            notifyItemRangeInserted(start, it.size)
        }
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

}