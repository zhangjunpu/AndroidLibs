package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView

/**
 * 编辑对话框
 *
 * @author Junpu
 * @time 2018/9/13 14:33
 */
class EditDialog : BaseDialog, View.OnClickListener {

    var editText: EditText? = null
    private var textTitle: TextView? = null

    // 取消按钮
    private var negativeButton: TextView? = null
    private var negativeListener: ((dialog: DialogInterface, which: Int) -> Unit)? = null

    // 确定按钮
    private var positiveButton: TextView? = null
    private var onSubmitListener: ((dialog: EditDialog, id: Int, message: String?) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_edit

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun initView() {
        setCanceledOnTouchOutside(false)
        textTitle = findViewById(R.id.title)
        editText = findViewById(R.id.editText)
        negativeButton = findViewById(R.id.cancel)
        positiveButton = findViewById(R.id.submit)
        negativeButton?.setOnClickListener(this)
        positiveButton?.setOnClickListener(this)
    }

    fun setText(content: String?) {
        editText?.setText(content)
        editText?.requestFocus()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cancel -> {
                DialogUtils.hideInputMethod(context, v)
                negativeListener?.invoke(this, v.id)
            }
            R.id.submit -> {
                val message = editText?.text.toString()
                onSubmitListener?.invoke(this, v.id, message)
            }
        }
        cancel()
    }

    /**
     * 设置标题
     */
    fun setTitle(text: String?) {
        textTitle?.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
        textTitle?.text = text
    }

    /**
     * 设置Message
     */
    fun setMessage(text: String?) {
        editText?.setText(text)
    }

    /**
     * 设置EditText提示语
     */
    fun setHint(text: String?) {
        editText?.hint = text
    }

    /**
     * 设置取消按钮
     */
    fun setNegativeButton(
        text: String? = null,
        listener: ((dialog: DialogInterface, which: Int) -> Unit)? = null
    ) {
        text?.let { negativeButton?.text = it }
        negativeListener = listener
    }

    /**
     * 设置确定按钮
     */
    fun setPositiveButton(
        text: String? = null,
        listener: ((dialog: EditDialog, id: Int, message: String?) -> Unit)? = null
    ) {
        text?.let { positiveButton?.text = it }
        onSubmitListener = listener
    }

    /**
     * 设置单行显示
     */
    fun setSingleLine(singleLine: Boolean) {
        editText?.setSingleLine(singleLine)
    }

    /**
     * 设置最大行数
     */
    fun setMaxLines(maxLines: Int) {
        editText?.maxLines = maxLines
    }

    /**
     * 设置最大字数
     */
    fun setMaxLength(maxLength: Int) {
        val inputFilter: InputFilter = LengthFilter(maxLength)
        editText?.filters = arrayOf(inputFilter)
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var message: String? = null
        private var hint: String? = null
        private var negativeListener: ((dialog: DialogInterface, which: Int) -> Unit)? = null
        private var onSubmitListener: ((dialog: EditDialog, id: Int, message: String?) -> Unit)? =
            null
        private var negativeText: String? = null
        private var positiveText: String? = null
        private var singleLine = false
        private var maxLines = 0
        private var maxLength = 0

        fun setTitle(title: String?): Builder = apply {
            this.title = title
        }

        fun setMessage(resId: Int): Builder = apply {
            this.message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?): Builder = apply {
            this.message = msg
        }

        fun setHint(hint: String?): Builder = apply {
            this.hint = hint
        }

        fun setNegativeButton(
            text: String? = null,
            listener: ((dialog: DialogInterface, which: Int) -> Unit)? = null
        ): Builder = apply {
            this.negativeText = text
            this.negativeListener = listener
        }

        fun setPositiveButton(
            text: String? = null,
            listener: ((dialog: EditDialog, id: Int, message: String?) -> Unit)? = null
        ): Builder = apply {
            this.positiveText = text
            this.onSubmitListener = listener
        }

        fun setSingleLine(singleLine: Boolean): Builder = apply {
            this.singleLine = singleLine
        }

        fun setMaxLines(maxLines: Int): Builder = apply {
            this.maxLines = maxLines
        }

        fun setMaxLength(maxLength: Int): Builder = apply {
            this.maxLength = maxLength
        }

        fun create() = EditDialog(context).apply {
            setTitle(title)
            setMessage(message)
            setHint(hint)
            setNegativeButton(negativeText, this@Builder.negativeListener)
            setPositiveButton(positiveText, this@Builder.onSubmitListener)
            setSingleLine(singleLine)
            if (maxLines > 0) setMaxLines(maxLines)
            if (maxLength > 0) setMaxLength(maxLength)
        }

        fun show() = create().apply {
            show()
        }
    }
}