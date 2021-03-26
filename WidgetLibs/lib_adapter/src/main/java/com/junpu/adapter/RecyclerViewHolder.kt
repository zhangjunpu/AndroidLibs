package com.junpu.adapter

import android.content.Context
import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * RecyclerViewHolder
 * @author junpu
 * @date 2019-12-31
 */
abstract class RecyclerViewHolder(
    itemView: View,
    private val listener: OnItemClickListener?,
    private val childListener: OnItemChildClickListener?,
    private val longClickListener: OnItemLongClickListener?
) : ViewHolder(itemView) {

    protected val context: Context = itemView.context

    protected val click: (View?) -> Unit = { v ->
        when (v?.id) {
            itemView.id -> listener?.onItemClick(findParent(itemView.parent), v, adapterPosition)
            else -> childListener?.onItemChildClick(
                findParent(itemView.parent),
                itemView,
                v,
                adapterPosition
            )
        }
    }

    private val longClick: (View?) -> Boolean = { v ->
        longClickListener?.onItemLongClick(findParent(itemView.parent), v, adapterPosition)
        true
    }

    init {
        if (listener != null) itemView.setOnClickListener(click)
        if (longClickListener != null) itemView.setOnLongClickListener(longClick)
    }

    private fun findParent(parent: ViewParent): RecyclerView =
        if (parent is RecyclerView) parent else findParent(parent)

}