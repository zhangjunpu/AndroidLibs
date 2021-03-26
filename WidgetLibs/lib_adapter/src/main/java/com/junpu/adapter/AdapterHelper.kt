@file:JvmName("AdapterHelper")

package com.junpu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Item点击事件
 * @author junpu
 * @date 2019-12-31
 */
interface OnItemClickListener {
    fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int)
}

/**
 * Item内子View点击事件
 * @author junpu
 * @date 2019-12-31
 */
interface OnItemChildClickListener {
    fun onItemChildClick(
        recyclerView: RecyclerView?,
        itemView: View?,
        childView: View?,
        position: Int
    )
}

/**
 * Item长按事件
 * @author junpu
 * @date 2019-12-31
 */
interface OnItemLongClickListener {
    fun onItemLongClick(recyclerView: RecyclerView?, itemView: View?, position: Int)
}

/**
 * 更新ArrayList Item
 */
fun <T> ArrayList<T>.update(list: List<T>?) {
    clear()
    list?.let { addAll(it) }
}

fun ViewGroup.inflate(resourceId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resourceId, this, attachToRoot)