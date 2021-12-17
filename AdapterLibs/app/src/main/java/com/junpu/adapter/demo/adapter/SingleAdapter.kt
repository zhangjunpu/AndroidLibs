package com.junpu.adapter.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.SingleRecyclerAdapter
import com.junpu.adapter.demo.databinding.AdapterItemBinding
import com.junpu.adapter.demo.model.AdapterItem
import com.junpu.adapter.demo.utils.binding

/**
 * 单选的 adapter
 * @author junpu
 * @date 2021/12/13
 */
class SingleAdapter : SingleRecyclerAdapter<AdapterItem, SingleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleHolder {
        return SingleHolder(parent.binding(), itemClick)
    }

    override fun onBindViewHolder(holder: SingleHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.check(checkedPosition == position)
    }
}

class SingleHolder(
    private val binding: AdapterItemBinding,
    itemClick: ((View?, Int) -> Unit)?,
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