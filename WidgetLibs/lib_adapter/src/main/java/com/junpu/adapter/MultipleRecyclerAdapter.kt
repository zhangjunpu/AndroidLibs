package com.junpu.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 多选列表
 * @author junpu
 * @date 2019-12-31
 */
abstract class MultipleRecyclerAdapter<T> : BaseAdapter<T>(), OnItemClickListener {

    var checkedList = arrayListOf<T>()

    /**
     * 获取已选择的数量
     */
    val checkedCount: Int
        get() = checkedList.size

    /**
     * 选择一个item
     */
    fun check(position: Int) = check(getItem(position))

    /**
     * 选择一个item
     */
    fun check(t: T?) {
        t?.let {
            if (isChecked(it)) checkedList.remove(it) else checkedList.add(it)
            notifyDataSetChanged()
        }
    }

    /**
     * 清空所有已选择的数据
     */
    fun clearChecked() {
        checkedList.clear()
    }

    /**
     * 选择或取消选择全部
     */
    fun checkedAll(flag: Boolean) {
        clearChecked()
        if (flag) checkedList.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 判断是否存在
     */
    fun isChecked(position: Int) = isChecked(getItem(position))

    /**
     * 判断是否存在
     */
    fun isChecked(t: T?) = checkedList.contains(t)

    override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
        check(position)
        listener?.onItemClick(recyclerView, itemView, position)
    }
}