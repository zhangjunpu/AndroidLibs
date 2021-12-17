package com.junpu.adapter.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.demo.databinding.AdapterItemBinding
import com.junpu.adapter.demo.databinding.AdapterItemHeaderBinding
import com.junpu.adapter.demo.model.AdapterItem
import com.junpu.adapter.demo.model.HeaderItem
import com.junpu.adapter.demo.utils.binding
import com.junpu.utils.gone

private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

/**
 * 带 Header 的 adapter
 * @author junpu
 * @date 2021/12/13
 */
class HeaderAdapter : BaseAdapter<AdapterItem, BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_HEADER -> HeaderHolder(parent.binding())
            else -> ItemHolder(parent.binding(), listener)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (position == 0) {
            (holder as HeaderHolder).bindData(null)
        } else {
            (holder as ItemHolder).bindData(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = if (position == 0) TYPE_HEADER else TYPE_ITEM
    override fun getItemCount() = super.getItemCount() + 1
    override fun getItem(position: Int) = if (position == 0) null else super.getItem(position - 1)
}

class HeaderHolder(
    private val binding: AdapterItemHeaderBinding
) : BaseViewHolder<HeaderItem>(binding.root)

class ItemHolder(
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