package com.junpu.dialog.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junpu.dialog.demo.databinding.DialogRecyclerItemBinding
import java.util.*

/**
 *
 * @author junpu
 * @date 2021/12/14
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var data: MutableList<String> = ArrayList()
    private var itemClickCallback: ((View?, Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val binding = DialogRecyclerItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return MyViewHolder(binding, itemClickCallback)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal fun doOnItemClick(callback: (view: View?, position: Int) -> Unit) {
        this.itemClickCallback = callback
    }

    internal fun update(list: MutableList<String>?) {
        data.clear()
        list?.let { data.addAll(it) }
        notifyDataSetChanged()
    }
}

class MyViewHolder(
    private val binding: DialogRecyclerItemBinding,
    private val itemClickCallback: ((View?, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            itemClickCallback?.invoke(it, bindingAdapterPosition)
        }
    }

    internal fun bind(text: String) {
        binding.textMessage.text = text
    }
}
