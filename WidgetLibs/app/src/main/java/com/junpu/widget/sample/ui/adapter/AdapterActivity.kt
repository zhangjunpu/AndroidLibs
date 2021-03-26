package com.junpu.widget.sample.ui.adapter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.junpu.adapter.*
import com.junpu.toast.toast
import com.junpu.widget.sample.R
import com.junpu.widget.sample.utils.backgroundResource
import kotlinx.android.synthetic.main.activity_adapter_item.view.*
import kotlinx.android.synthetic.main.activity_slide.*

/**
 * @author junpu
 * @date 2019-12-31
 */
class AdapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)

        val adapter = MyAdapter().apply {
            doOnItemClick { recyclerView, itemView, position ->
                println("item.position = ${position}")
                toast("item.position = ${position}")
            }
            doOnChildClick { recyclerView, itemView, childView, position ->
                println("child.position = ${position}")
                toast("child.position = ${position}")
            }
            doOnItemLongClick { recyclerView, itemView, position ->
                println("longClick.position = ${position}")
                toast("longClick.position = ${position}")
            }
            update(data)
        }
        recyclerView?.run {
            this.layoutManager =
                LinearLayoutManager(context)
            this.adapter = adapter
        }
        adapter.update(data)
    }

    private val data: List<Item>
        get() {
            val list = arrayListOf<Item>()
            for (i in 0..20) {
                val item = Item(i, "name $i")
                list.add(item)
            }
            return list
        }

    private class MyAdapter : BaseAdapter<Item>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
            return MyViewHolder(
                parent.inflate(R.layout.activity_adapter_item),
                listener,
                childListener,
                null
            )
//            return MyCusViewHolder(view, doItemClick, doItemLongClick)
        }

        override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {
            holder.bindData(getItem(position))
        }

        private val doItemClick = { recyclerView: RecyclerView?, itemView: View?, position: Int ->
            println("doItemClick.position = ${position}")
            listener?.onItemClick(recyclerView, itemView, position)
        }

        private val doItemLongClick =
            { recyclerView: RecyclerView?, itemView: View?, position: Int ->
                println("doItemClick.position = ${position}")
                longClickListener?.onItemLongClick(recyclerView, itemView, position)
            }
    }

    private class MyViewHolder(
        view: View,
        listener: OnItemClickListener?,
        childListener: OnItemChildClickListener?,
        longClickListener: OnItemLongClickListener?
    ) :
        BaseViewHolder<Item>(view, listener, childListener, longClickListener) {
        init {
            itemView.textName.setOnClickListener(click)
        }

        override fun bindData(t: Item?) {
            itemView.textName?.text = t?.name
        }

        fun check(isCheck: Boolean) {
            itemView.backgroundResource =
                if (isCheck) R.attr.selectableItemBackground else R.attr.selectableItemBackgroundBorderless
        }
    }

    private class MyCusViewHolder(
        view: View,
        listener: ((RecyclerView?, View?, Int) -> Unit?),
        longClickListener: ((RecyclerView?, View?, Int) -> Unit?)
    ) :
        BaseViewHolder<Item>(view, listener, longClickListener = longClickListener) {
        init {
            itemView.textName.setOnClickListener(click)
        }

        override fun bindData(t: Item?) {
            itemView.textName?.text = t?.name
        }
    }

    private data class Item(
        val id: Int,
        val name: String
    )
}