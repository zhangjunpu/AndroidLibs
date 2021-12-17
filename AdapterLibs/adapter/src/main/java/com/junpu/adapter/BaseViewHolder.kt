package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base ViewHolder
 * @author junpu
 * @date 2019-12-31
 */
open class BaseViewHolder<T>(
    itemView: View,
    listener: ((View?, Int) -> Unit)? = null,
    childListener: ((View?, Int) -> Unit)? = null,
    longClickListener: ((View?, Int) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {

    protected val context = itemView.context

    protected val click: (View?) -> Unit = { v ->
        when (v?.id) {
            itemView.id -> listener?.invoke(v, bindingAdapterPosition)
            else -> childListener?.invoke(v, bindingAdapterPosition)
        }
    }

    private val longClick: (View?) -> Boolean = { v ->
        longClickListener?.invoke(v, bindingAdapterPosition)
        true
    }

    init {
        if (listener != null) itemView.setOnClickListener(click)
        if (longClickListener != null) itemView.setOnLongClickListener(longClick)
    }

    open fun bindData(t: T?) {}
}