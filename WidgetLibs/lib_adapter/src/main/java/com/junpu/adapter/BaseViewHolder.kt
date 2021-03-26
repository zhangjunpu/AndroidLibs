package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base ViewHolder
 * @author junpu
 * @date 2019-12-31
 */
open class BaseViewHolder<T> : RecyclerViewHolder {

    constructor(
        itemView: View,
        listener: OnItemClickListener? = null,
        childListener: OnItemChildClickListener? = null,
        longClickListener: OnItemLongClickListener? = null
    ) : super(itemView, listener, childListener, longClickListener)

    constructor(
        itemView: View,
        listener: ((RecyclerView?, View?, Int) -> Unit?)?,
        childListener: ((RecyclerView?, View?, View?, Int) -> Unit?)? = null,
        longClickListener: ((RecyclerView?, View?, Int) -> Unit?)? = null
    ) : super(itemView,
        listener?.let {
            object : OnItemClickListener {
                override fun onItemClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    position: Int
                ) {
                    it.invoke(recyclerView, itemView, position)
                }
            }
        },
        childListener?.let {
            object : OnItemChildClickListener {
                override fun onItemChildClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    childView: View?,
                    position: Int
                ) {
                    it.invoke(recyclerView, itemView, childView, position)
                }
            }
        },
        longClickListener?.let {
            object : OnItemLongClickListener {
                override fun onItemLongClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    position: Int
                ) {
                    it.invoke(recyclerView, itemView, position)
                }
            }
        }
    )

    open fun bindData(t: T?) {}
}