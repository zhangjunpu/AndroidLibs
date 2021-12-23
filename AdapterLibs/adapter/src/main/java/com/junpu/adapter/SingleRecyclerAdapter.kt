package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 单选列表
 * @author junpu
 * @date 2019-12-31
 */
abstract class SingleRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : BaseAdapter<T, VH>() {

    var checkedPosition = -1

    /**
     * 选择的item
     */
    val checkedItem: T?
        get() = if (checkedPosition in 0 until itemCount) getItem(checkedPosition) else null

    /**
     * 选择一个并刷新item
     */
    fun check(position: Int) {
        if (checkedPosition != position) {
            val old = checkedPosition
            checkedPosition = position
            notifyItemChanged(old)
            notifyItemChanged(checkedPosition)
        }
    }

    /**
     * 选择一个并刷新列表
     */
    fun checkNotifyAll(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

    /**
     * 判断是否存在
     */
    fun isChecked(position: Int) = checkedPosition == position

    protected val itemClick: (View?, Int) -> Unit = { view, i ->
        if (checkedPosition != i) {
            check(i)
            listener?.invoke(view, i)
        }
    }
}