package com.junpu.adapter.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.demo.databinding.AdapterItemBinding
import com.junpu.adapter.demo.utils.binding

/**
 * @author junpu
 * @date 2021/12/13
 */
class TestAdapter : BaseAdapter<TestItem, TestHolder>() {
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
) : BaseViewHolder<TestItem>(binding.root, itemClick) {
    override fun bindData(t: TestItem?) {
        super.bindData(t)
        binding.textTitle.text = t?.title
        binding.textDesc.text = t?.subTitle
    }
}

data class TestItem(
    var title: String?,
    var subTitle: String?,
)