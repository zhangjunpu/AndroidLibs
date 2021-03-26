package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter
 * @author junpu
 * @date 2019-12-31
 */
abstract class BaseAdapter<T> : RecyclerViewAdapter<T>() {

    protected var listener: OnItemClickListener? = null
    protected var childListener: OnItemChildClickListener? = null
    protected var longClickListener: OnItemLongClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setOnItemChildClickListener(listener: OnItemChildClickListener?) {
        this.childListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    fun doOnItemClick(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?) {
        this.listener = object : OnItemClickListener {
            override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
                listener?.invoke(recyclerView, itemView, position)
            }
        }
    }

    fun doOnChildClick(listener: ((recyclerView: RecyclerView?, itemView: View?, childView: View?, position: Int) -> Unit)?) {
        this.childListener = object : OnItemChildClickListener {
            override fun onItemChildClick(
                recyclerView: RecyclerView?,
                itemView: View?,
                childView: View?,
                position: Int
            ) {
                listener?.invoke(recyclerView, itemView, childView, position)
            }
        }
    }

    fun doOnItemLongClick(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?) {
        this.longClickListener = object : OnItemLongClickListener {
            override fun onItemLongClick(
                recyclerView: RecyclerView?,
                itemView: View?,
                position: Int
            ) {
                listener?.invoke(recyclerView, itemView, position)
            }
        }
    }

    fun update(array: List<T>?) {
        data.clear()
        addAll(array)
    }

    fun updateAt(position: Int, t: T) {
        if (position in data.indices) data[position] = t
        notifyDataSetChanged()
    }

    fun add(t: T) {
        data.add(t)
        notifyDataSetChanged()
    }

    fun addAll(list: List<T>?) {
        list?.let { data.addAll(it) }
        notifyDataSetChanged()
    }

    fun addAt(position: Int, t: T) {
        if (position in data.indices) data.add(position, t)
        notifyDataSetChanged()
    }

    fun addFirst(t: T) {
        data.add(0, t)
        notifyDataSetChanged()
    }

    fun addAllFirst(list: List<T>?) {
        list?.let { data.addAll(0, it) }
        notifyDataSetChanged()
    }

    fun remove(t: T) {
        data.remove(t)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun contains(t: T) = data.contains(t)

}