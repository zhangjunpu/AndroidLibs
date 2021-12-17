package com.junpu.adapter.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.MultipleRecyclerAdapter
import com.junpu.adapter.demo.databinding.AdapterItemBinding
import com.junpu.adapter.demo.model.AdapterItem
import com.junpu.adapter.demo.utils.binding

/**
 * 多选的 adapter
 * @author junpu
 * @date 2021/12/13
 */
class MultipleAdapter : MultipleRecyclerAdapter<AdapterItem, MultipleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleHolder {
        return MultipleHolder(parent.binding(), itemClick)
    }

    override fun onBindViewHolder(holder: MultipleHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
        holder.check(checkedList.contains(item))
    }
}

class MultipleHolder(
    private val binding: AdapterItemBinding,
    itemClick: (View?, Int) -> Unit,
) : BaseViewHolder<AdapterItem>(binding.root, itemClick) {
    override fun bindData(t: AdapterItem?) {
        super.bindData(t)
        binding.textTitle.text = t?.title
        binding.textDesc.text = t?.subTitle
    }

    fun check(flag: Boolean) {
        binding.checkBox.isChecked = flag
    }
}