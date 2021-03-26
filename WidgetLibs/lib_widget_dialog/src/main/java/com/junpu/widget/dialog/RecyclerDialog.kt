package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * RecyclerView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
class RecyclerDialog : BaseDialog {

    var recyclerView: RecyclerView? = null
    private var textTitle: TextView? = null
    private var textSubTitle: TextView? = null
    private var textMessage: TextView? = null
    private var progressBar: ProgressBar? = null
    private var textErrorMessage: TextView? = null

    private var adapter: DefaultRecyclerDialogAdapter? = null
    private var onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)? =
        null

    constructor(context: Context, themeResId: Int = R.style.Theme_Dialog_Recycler) : super(
        context,
        themeResId
    )

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_recycler_view

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        window.setGravity(Gravity.BOTTOM)
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun initView() {
        textTitle = findViewById(R.id.textTitle)
        textSubTitle = findViewById(R.id.textSubTitle)
        textMessage = findViewById(R.id.textMessage)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress)
        textErrorMessage = findViewById(R.id.errorMessage)
        loading()
    }

    /**
     * 初始化默认RecyclerView Adapter
     */
    fun initDefaultAdapter() {
        adapter = DefaultRecyclerDialogAdapter().apply {
            setOnItemClickListener(onItemClickListener)
        }
        recyclerView?.run {
            layoutManager =
                LinearLayoutManager(context)
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
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    /**
     * 设置ItemDecoration
     */
    fun addItemDecoration(decoration: ItemDecoration?) {
        recyclerView?.addItemDecoration(decoration!!)
    }

    fun setOnItemClickListener(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?) {
        onItemClickListener = listener
        adapter?.setOnItemClickListener(onItemClickListener)
    }

    fun setList(list: MutableList<String>?) {
        adapter?.update(list)?.run {
            success()
        }
    }

    fun setTitle(title: String?) {
        textTitle?.text = title
        textTitle?.visibility = if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
    }

    fun setSubTitle(subTitle: String?) {
        textSubTitle?.text = subTitle
        textSubTitle?.visibility = if (TextUtils.isEmpty(subTitle)) View.GONE else View.VISIBLE
    }

    fun setMessage(message: String?) {
        textMessage?.text = message
        textMessage?.visibility = if (TextUtils.isEmpty(message)) View.GONE else View.VISIBLE
    }

    /**
     * loading状态
     */
    override fun loading() {
        progressBar?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        textErrorMessage?.visibility = View.GONE
    }

    /**
     * 成功状态
     */
    override fun success() {
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
        textErrorMessage?.visibility = View.GONE
    }

    /**
     * 错误状态
     */
    override fun error(errorMsg: String?) {
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        textErrorMessage?.visibility = View.VISIBLE
        textErrorMessage?.text = errorMsg
    }

    /**
     * Default RecyclerView Adapter
     */
    private class DefaultRecyclerDialogAdapter :
        RecyclerView.Adapter<DefaultRecyclerDialogViewHolder>() {

        private var data = mutableListOf<String>()
        private var onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)? =
            null

        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            position: Int
        ): DefaultRecyclerDialogViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.dialog_list_item, viewGroup, false).apply {
                setBackgroundResource(R.drawable.dialog_item_selector)
            }
            return DefaultRecyclerDialogViewHolder(view, onItemClickListener)
        }

        override fun onBindViewHolder(
            recyclerDialogViewHolder: DefaultRecyclerDialogViewHolder,
            position: Int
        ) {
            recyclerDialogViewHolder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size

        fun setOnItemClickListener(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?) {
            onItemClickListener = listener
        }

        fun update(list: MutableList<String>?) {
            list?.let {
                data.clear()
                data.addAll(list)
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Default ViewHolder
     */
    private class DefaultRecyclerDialogViewHolder constructor(
        itemView: View,
        private val onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?
    ) : ViewHolder(itemView), View.OnClickListener {

        private var textView: TextView? = null

        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnClickListener(this)
        }

        fun bind(text: String?) {
            textView?.text = text
        }

        override fun onClick(v: View) {
            onItemClickListener?.invoke(itemView.parent as? RecyclerView, v, adapterPosition)
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
        private var onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)? =
            null
        private var list: MutableList<String>? = null

        fun setTitle(title: String?): Builder = apply {
            this.title = title
        }

        fun setSubTitle(subTitle: String?): Builder = apply {
            this.subTitle = subTitle
        }

        fun setMessage(resId: Int): Builder = apply {
            this.message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?): Builder = apply {
            this.message = msg
        }

        fun setWidth(width: Int): Builder = apply {
            this.width = width
        }

        fun setHeight(height: Int): Builder = apply {
            this.height = height
        }

        fun setAdapter(adapter: RecyclerView.Adapter<*>?): Builder = apply {
            this.adapter = adapter
        }

        fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?): Builder = apply {
            this.layoutManager = layoutManager
        }

        fun addItemDecoration(decoration: ItemDecoration?): Builder = apply {
            this.itemDecoration = decoration
        }

        fun setOnItemClickListener(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?): Builder =
            apply {
                this.onItemClickListener = listener
            }

        fun setList(items: Array<String>): Builder = apply {
            this.list = items.toMutableList()
        }

        fun setList(items: MutableList<String>?): Builder = apply {
            this.list = items
        }

        fun create() = RecyclerDialog(context).apply {
            setTitle(title)
            setSubTitle(subTitle)
            setMessage(message)
            if (width != -3) setWidth(width)
            if (height != -3) setHeight(height)
            this@Builder.adapter?.let {
                setCustomAdapter(
                    this@Builder.layoutManager ?: LinearLayoutManager(
                        context
                    ), it
                )
            } ?: initDefaultAdapter()
            itemDecoration?.let { addItemDecoration(it) }
            setOnItemClickListener(this@Builder.onItemClickListener)
            setList(list)
        }

        fun show(): RecyclerDialog = create().apply {
            show()
        }
    }

}