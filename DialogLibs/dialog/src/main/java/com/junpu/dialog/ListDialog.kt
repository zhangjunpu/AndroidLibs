package com.junpu.dialog

import android.content.Context
import android.view.*
import android.widget.*
import com.junpu.dialog.databinding.DialogListBinding

/**
 * 列表对话框
 * @author junpu
 * @date 2019-12-23
 */
class ListDialog(context: Context) : BaseDialog(context, R.style.Dialog_Theme_Bottom) {

    private val binding by lazy {
        DialogListBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    private var buttonClickCallback: ((ListDialog, View) -> Unit)? = null
    private var itemClickCallback: ((ListDialog, View, Int) -> Unit)? = null

    init {
        binding.btnCancel.setOnClickListener {
            buttonClickCallback?.invoke(this@ListDialog, it)
            cancel()
        }

        setGravityBottom()
        setLandScape(false)
    }

    fun setLandScape(isLand: Boolean) {
        window?.run {
            attributes.width =
                if (isLand) context.screenHeight else WindowManager.LayoutParams.MATCH_PARENT
        }
    }

    /**
     * 设置数据
     */
    fun setList(list: Array<String>?) {
        binding.listView.run {
            adapter = addAdapter(list)
            onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
                itemClickCallback?.invoke(this@ListDialog, view, position)
                cancel()
            }
        }
    }

    fun showActionButton(flag: Boolean) {
        binding.layoutAction.visibility = if (flag) View.VISIBLE else View.GONE
    }

    fun setButtonText(text: String?) {
        binding.btnCancel.text = text
    }

    fun doOnButtonClick(callback: ((dialog: ListDialog, view: View) -> Unit)?) {
        buttonClickCallback = callback
    }

    fun doOnItemClick(callback: ((dialog: ListDialog, view: View, position: Int) -> Unit)?) {
        itemClickCallback = callback
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
        binding.listView.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

    /**
     * 设置指定position的选中
     */
    fun setItemChecked(position: Int, value: Boolean) {
        binding.listView.setItemChecked(position, value)
    }

    /**
     * 自定义Adapter
     */
    fun setAdapter(adapter: ListAdapter?): ListDialog = apply {
        binding.listView.adapter = adapter
    }

    /**
     * 默认的Item效果的Adapter
     */
    fun addAdapter(list: List<String>?): ArrayAdapter<*> {
        val resourceId: Int = when (binding.listView.choiceMode) {
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
        var showActionButton = true
        var buttonText: String? = null
        var buttonClickCallback: ((ListDialog, View) -> Unit)? = null
        var itemClickCallback: ((ListDialog, View, Int) -> Unit)? = null

        fun setList(items: Array<String>) = apply {
            this.list = items
        }

        fun isLandScape(isLand: Boolean) = apply {
            this.isLand = isLand
        }

        fun showActionButton(isShow: Boolean) = apply {
            this.showActionButton = isShow
        }

        fun setButtonText(text: String?) = apply {
            this.buttonText = text
        }

        fun doOnButtonClick(callback: ((dialog: ListDialog, view: View) -> Unit)?) = apply {
            this.buttonClickCallback = callback
        }

        fun doOnItemClick(callback: ((dialog: ListDialog, view: View, position: Int) -> Unit)?) =
            apply {
                this.itemClickCallback = callback
            }

        fun create() = ListDialog(context).apply {
            setList(list)
            setLandScape(isLand)
            showActionButton(showActionButton)
            buttonText?.let { setButtonText(it) }
            doOnButtonClick(this@Builder.buttonClickCallback)
            doOnItemClick(this@Builder.itemClickCallback)
        }

        fun show() = create().apply {
            show()
        }
    }
}