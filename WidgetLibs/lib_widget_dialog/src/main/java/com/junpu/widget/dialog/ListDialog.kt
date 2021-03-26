package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.*
import android.widget.*

/**
 * 列表对话框
 * @author junpu
 * @date 2019-12-23
 */
class ListDialog : BaseDialog {

    var listView: ListView? = null

    private var actionMenu: View? = null
    private var btnCancel: TextView? = null
    private var onButtonClickListener: ((dialog: ListDialog, id: Int) -> Unit)? = null
    private var onListItemClickListener: ((dialog: ListDialog, position: Int) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_list

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        window.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        window.setWindowAnimations(R.style.dialog_anim_bottom)
        setLandScape(false)
    }

    override fun initView() {
        listView = findViewById(R.id.listView)
        actionMenu = findViewById(R.id.actionMenu)
        btnCancel = findViewById<TextView>(R.id.cancel).apply {
            setOnClickListener {
                onButtonClickListener?.invoke(this@ListDialog, it.id)
                cancel()
            }
        }
    }

    fun setLandScape(isLand: Boolean) {
        window?.run {
            decorView.setPadding(0, 0, 0, 0)
            attributes = attributes.also {
                if (isLand) {
                    it.width = DialogUtils.getScreenHeight(context) - DialogUtils.dp2px(context, 20)
                } else {
                    it.width = WindowManager.LayoutParams.MATCH_PARENT
                }
                it.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
        }
    }

    /**
     * 设置数据
     */
    fun setList(list: Array<String>?) {
        listView?.run {
            adapter = addAdapter(list)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                onListItemClickListener?.invoke(this@ListDialog, position)
                cancel()
            }
        }
    }

    /**
     * 设置点击事件
     */
    fun setOnItemClickListener(listener: ((dialog: ListDialog, position: Int) -> Unit)?) {
        this.onListItemClickListener = listener
    }

    /**
     * 设置是否显示底部功能栏
     */
    fun setShowActionMenu(flag: Boolean) {
        actionMenu?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    /**
     * 设置底部功能栏文字、事件
     */
    fun setButtonText(text: String?, listener: ((dialog: ListDialog, id: Int) -> Unit)?) {
        text?.let { btnCancel?.text = it }
        onButtonClickListener = listener
    }

    /**
     * Defines the choice behavior for the List. By default, Lists do not have
     * any choice behavior ([ListView.CHOICE_MODE_NONE]). By setting the
     * choiceMode to [ListView.CHOICE_MODE_SINGLE], the List allows up to one item
     * to be in a chosen state. By setting the choiceMode to
     * [ListView.CHOICE_MODE_MULTIPLE], the list allows any number of items to be
     * chosen.
     *
     * @param choiceMode One of [ListView.CHOICE_MODE_NONE], [ListView.CHOICE_MODE_SINGLE],
     * or [ListView.CHOICE_MODE_MULTIPLE]
     */
    fun setChoiceMode(choiceMode: Int) {
        listView?.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

    /**
     * 设置指定position的选中
     */
    fun setItemChecked(position: Int, value: Boolean) {
        listView?.setItemChecked(position, value)
    }

    /**
     * 自定义Adapter
     */
    fun setAdapter(adapter: ListAdapter?): ListDialog = apply {
        listView?.adapter = adapter
    }

    /**
     * 默认的Item效果的Adapter
     */
    fun addAdapter(list: List<String>?): ArrayAdapter<*> {
        val resourceId: Int = when (listView?.choiceMode) {
            else -> R.layout.dialog_list_item
        }
        return object : ArrayAdapter<String>(context, resourceId, list ?: emptyList()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(resourceId, parent, false)
                }
                val bean = getItem(position)
                (view as? TextView)?.text = bean
                return view!!
            }
        }
    }

    fun addAdapter(objects: Array<String>?): ArrayAdapter<*> {
        return addAdapter(objects?.toList())
    }

    class Builder(private val context: Context) {
        var list: Array<String>? = null
        var isLand = false
        var setShowActionMenu = true
        var buttonText: String? = null
        var onButtonClickListener: ((dialog: ListDialog, id: Int) -> Unit)? = null
        var onItemClickListener: ((dialog: ListDialog, position: Int) -> Unit)? = null

        fun setList(items: Array<String>): Builder = apply {
            this.list = items
        }

        fun setIsLandScape(isLand: Boolean): Builder = apply {
            this.isLand = isLand
        }

        fun setShowCancelButton(isShow: Boolean): Builder = apply {
            this.setShowActionMenu = isShow
        }

        fun setButtonText(
            text: String?,
            listener: ((dialog: ListDialog, id: Int) -> Unit)?
        ): Builder = apply {
            this.buttonText = text
            this.onButtonClickListener = listener
        }

        fun setOnItemClickListener(listener: ((dialog: ListDialog, position: Int) -> Unit)?): Builder =
            apply {
                this.onItemClickListener = listener
            }

        fun create() = ListDialog(context).apply {
            setList(list)
            setLandScape(isLand)
            setShowActionMenu(setShowActionMenu)
            setButtonText(buttonText, this@Builder.onButtonClickListener)
            setOnItemClickListener(this@Builder.onItemClickListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}