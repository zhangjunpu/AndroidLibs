package com.junpu.dialog

import android.content.Context
import android.text.TextUtils
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.junpu.dialog.databinding.DialogListItemBinding
import com.junpu.dialog.databinding.DialogRecyclerViewBinding

/**
 * RecyclerView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
class RecyclerDialog(context: Context) : BaseDialog(context, R.style.Dialog_Theme_Bottom) {

    private val binding by lazy {
        DialogRecyclerViewBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    val recyclerView: RecyclerView get() = binding.recyclerView

    private var adapter: DefaultRecyclerDialogAdapter? = null
    private var defaultAdapterItemClickCallback: ((View?, Int) -> Unit)? = null

    init {
        loading()

        setGravityBottom()
        setWidthMatchParent()
    }

    /**
     * 初始化默认RecyclerView Adapter
     */
    fun initDefaultAdapter() {
        adapter = DefaultRecyclerDialogAdapter().apply {
            defaultAdapterItemClickCallback?.let { doOnItemClick(it) }
        }
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RecyclerDialog.adapter
        }
    }

    /**
     * 自定义RecyclerView Adapter，LayoutManager
     */
    fun setCustomAdapter(
        layoutManager: RecyclerView.LayoutManager?,
        adapter: RecyclerView.Adapter<*>?
    ) {
        recyclerView.run {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }

    /**
     * 设置ItemDecoration
     */
    fun addItemDecoration(decoration: ItemDecoration) {
        recyclerView.addItemDecoration(decoration)
    }

    fun doOnDefaultAdapterItemClick(callback: ((View?, Int) -> Unit)?) {
        defaultAdapterItemClickCallback = callback
        adapter?.doOnItemClick(callback)
    }

    fun setList(list: MutableList<String>?) {
        adapter?.update(list)?.run {
            success()
        }
    }

    fun setTitle(title: String?) {
        binding.run {
            textTitle.text = title
            textTitle.visibility = if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
        }
    }

    fun setSubTitle(subTitle: String?) {
        binding.run {
            textSubTitle.text = subTitle
            textSubTitle.visibility = if (TextUtils.isEmpty(subTitle)) View.GONE else View.VISIBLE
        }
    }

    fun setMessage(message: String?) {
        binding.run {
            textMessage.text = message
            textMessage.visibility = if (TextUtils.isEmpty(message)) View.GONE else View.VISIBLE
        }
    }

    /**
     * loading状态
     */
    override fun loading() {
        binding.run {
            progress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            textErrorMessage.visibility = View.GONE
        }
    }

    /**
     * 成功状态
     */
    override fun success() {
        binding.run {
            progress.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            textErrorMessage.visibility = View.GONE
        }
    }

    /**
     * 错误状态
     */
    override fun error(errorMsg: String?) {
        binding.run {
            progress.visibility = View.GONE
            recyclerView.visibility = View.GONE
            textErrorMessage.visibility = View.VISIBLE
            textErrorMessage.text = errorMsg
        }
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var subTitle: String? = null
        private var message: String? = null
        private var width = -3
        private var height = -3
        private var adapter: RecyclerView.Adapter<*>? = null
        private var layoutManager: RecyclerView.LayoutManager? = null
        private var itemDecoration: ItemDecoration? = null
        private var defaultAdapterItemClickCallback: ((View?, Int) -> Unit)? = null
        private var list: MutableList<String>? = null

        fun setTitle(title: String?) = apply {
            this.title = title
        }

        fun setSubTitle(subTitle: String?) = apply {
            this.subTitle = subTitle
        }

        fun setMessage(resId: Int) = apply {
            this.message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?) = apply {
            this.message = msg
        }

        fun setWidth(width: Int) = apply {
            this.width = width
        }

        fun setHeight(height: Int) = apply {
            this.height = height
        }

        fun setAdapter(adapter: RecyclerView.Adapter<*>?) = apply {
            this.adapter = adapter
        }

        fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) = apply {
            this.layoutManager = layoutManager
        }

        fun addItemDecoration(decoration: ItemDecoration?) = apply {
            this.itemDecoration = decoration
        }

        fun doOnDefaultAdapterItemClick(callback: ((view: View?, position: Int) -> Unit)?) = apply {
            this.defaultAdapterItemClickCallback = callback
        }

        fun setList(items: Array<String>) = apply {
            this.list = items.toMutableList()
        }

        fun setList(items: MutableList<String>?) = apply {
            this.list = items
        }

        fun create() = RecyclerDialog(context).apply {
            setTitle(title)
            setSubTitle(subTitle)
            setMessage(message)
            if (width != -3) setWidth(width)
            if (height != -3) setHeight(height)
            this@Builder.adapter?.let {
                setCustomAdapter(this@Builder.layoutManager ?: LinearLayoutManager(context), it)
            } ?: initDefaultAdapter()
            itemDecoration?.let { addItemDecoration(it) }
            doOnDefaultAdapterItemClick(this@Builder.defaultAdapterItemClickCallback)
            setList(list)
        }

        fun show(): RecyclerDialog = create().apply {
            show()
        }
    }

}


/**
 * Default RecyclerView Adapter
 */
private class DefaultRecyclerDialogAdapter :
    RecyclerView.Adapter<DefaultRecyclerDialogViewHolder>() {

    private var data = mutableListOf<String>()
    private var itemClickCallback: ((View?, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): DefaultRecyclerDialogViewHolder {
        val binding = DialogListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).apply {
            root.background = parent.context.getAttr(android.R.attr.selectableItemBackground)
        }
        return DefaultRecyclerDialogViewHolder(binding, itemClickCallback)
    }

    override fun onBindViewHolder(
        recyclerDialogViewHolder: DefaultRecyclerDialogViewHolder,
        position: Int
    ) {
        recyclerDialogViewHolder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun doOnItemClick(callback: ((View?, Int) -> Unit)?) {
        itemClickCallback = callback
    }

    fun update(list: MutableList<String>?) {
        data.clear()
        list?.let { data.addAll(list) }
        notifyDataSetChanged()
    }
}

/**
 * Default ViewHolder
 */
private class DefaultRecyclerDialogViewHolder(
    private val binding: DialogListItemBinding,
    private val itemClickCallback: ((View?, Int) -> Unit)?
) : ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            itemClickCallback?.invoke(it, bindingAdapterPosition)
        }
    }

    fun bind(text: String?) {
        binding.text1.text = text
    }
}
