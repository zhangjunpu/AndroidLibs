package com.junpu.adapter.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.demo.databinding.AdapterItemBinding
import com.junpu.adapter.demo.model.AdapterItem
import com.junpu.adapter.demo.utils.binding
import com.junpu.utils.gone

/**
 * 最普通的 adapter
 * @author junpu
 * @date 2021/12/13
 */
class TestAdapter : BaseAdapter<AdapterItem, TestHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder(parent.binding(), listener)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}

class TestHolder(
    private val binding: AdapterItemBinding,
    itemClick: ((View?, Int) -> Unit)?,
) : BaseViewHolder<AdapterItem>(binding.root, itemClick) {

    init {
        binding.checkBox.gone()
    }

    override fun bindData(t: AdapterItem?) {
        binding.run {
            textTitle.text = t?.title
            textDesc.text = t?.subTitle
        }
    }
}