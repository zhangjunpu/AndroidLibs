package com.junpu.dialog

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.view.View
import com.junpu.dialog.databinding.DialogEditBinding

/**
 * 编辑对话框
 *
 * @author Junpu
 * @time 2018/9/13 14:33
 */
class EditDialog(context: Context) : BaseDialog(context, R.style.Dialog_Theme_Base) {

    private val binding by lazy {
        DialogEditBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    private var cancelCallback: ((EditDialog, View) -> Unit)? = null // 取消按钮
    private var submitCallback: ((EditDialog, View, String?) -> Unit)? = null // 确定按钮

    init {
        setCanceledOnTouchOutside(false)
        binding.run {
            btnCancel.setOnClickListener {
                context.hideInputMethod(it)
                cancelCallback?.invoke(this@EditDialog, it)
                cancel()
            }
            btnSubmit.setOnClickListener {
                val message = editText.text.toString()
                submitCallback?.invoke(this@EditDialog, it, message)
                cancel()
            }
        }

        setWidthMatchParent()
    }

    fun setText(content: String?) {
        binding.editText.run {
            setText(content)
            requestFocus()
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(text: String?) {
        binding.textTitle.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
        binding.textTitle.text = text
    }

    /**
     * 设置Message
     */
    fun setMessage(text: String?) {
        binding.editText.setText(text)
    }

    /**
     * 设置EditText提示语
     */
    fun setHint(text: String?) {
        binding.editText.hint = text
    }

    /**
     * 设置取消按钮
     */
    fun setCancelText(text: String) {
        binding.btnCancel.text = text
    }

    fun doOnCancel(callback: ((EditDialog, View) -> Unit)?) {
        cancelCallback = callback
    }

    /**
     * 设置确定按钮
     */
    fun setSubmitText(text: String) {
        binding.btnSubmit.text = text
    }

    fun doOnSubmit(callback: ((EditDialog, View, String?) -> Unit)?) {
        submitCallback = callback
    }

    /**
     * 设置单行显示
     */
    fun setSingleLine(singleLine: Boolean) {
        binding.editText.isSingleLine = singleLine
    }

    /**
     * 设置最大行数
     */
    fun setMaxLines(maxLines: Int) {
        binding.editText.maxLines = maxLines
    }

    /**
     * 设置最大字数
     */
    fun setMaxLength(maxLength: Int) {
        binding.editText.filters = arrayOf(LengthFilter(maxLength))
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var message: String? = null
        private var hint: String? = null
        private var cancelCallback: ((EditDialog, View) -> Unit)? = null
        private var submitCallback: ((EditDialog, View, String?) -> Unit)? = null
        private var cancelText: String? = null
        private var submitText: String? = null
        private var singleLine = false
        private var maxLines = 0
        private var maxLength = 0

        fun setTitle(title: String?) = apply {
            this.title = title
        }

        fun setMessage(resId: Int) = apply {
            this.message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?) = apply {
            this.message = msg
        }

        fun setHint(hint: String?) = apply {
            this.hint = hint
        }

        fun setCancelText(text: String) = apply {
            this.cancelText = text
        }

        fun doOnCancel(callback: ((dialog: EditDialog, view: View) -> Unit)?) = apply {
            this.cancelCallback = callback
        }

        fun setSubmitText(text: String) = apply {
            this.submitText = text
        }

        fun doOnSubmit(callback: ((dialog: EditDialog, view: View, message: String?) -> Unit)?) =
            apply {
                this.submitCallback = callback
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
            cancelText?.let { setCancelText(it) }
            doOnCancel(this@Builder.cancelCallback)
            submitText?.let { setSubmitText(it) }
            doOnSubmit(this@Builder.submitCallback)
            setSingleLine(singleLine)
            if (maxLines > 0) setMaxLines(maxLines)
            if (maxLength > 0) setMaxLength(maxLength)
        }

        fun show() = create().apply {
            show()
        }
    }
}