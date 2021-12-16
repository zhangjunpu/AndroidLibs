package com.junpu.dialog

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.junpu.dialog.databinding.DialogToastBinding

/**
 * 提示对话框，用于展示一段文字
 *
 * @author Junpu
 * @time 2017/2/24 13:37
 */
class ToastDialog(context: Context) : BaseDialog(context) {

    private val binding by lazy {
        DialogToastBinding.inflate(layoutInflater, contentView, contentView != null)
    }
    private var cancelCallback: ((ToastDialog, View) -> Unit)? = null
    private var submitCallback: ((ToastDialog, View) -> Unit)? = null

    init {
        setCanceledOnTouchOutside(false)
        binding.run {
            btnCancel.setOnClickListener {
                cancelCallback?.invoke(this@ToastDialog, it)
                cancel()
            }
            btnSubmit.setOnClickListener {
                submitCallback?.invoke(this@ToastDialog, it)
                cancel()
            }
        }
    }

    fun setMessage(msg: String?) {
        binding.textMessage.text = msg
    }

    fun setMessage(@StringRes resId: Int) {
        binding.textMessage.setText(resId)
    }

    fun setCancelText(text: String?) {
        binding.btnCancel.text = text
    }

    fun setCancelText(@StringRes resId: Int) {
        binding.btnCancel.setText(resId)
    }

    fun setSubmitText(text: String?) {
        binding.btnSubmit.text = text
    }

    fun setSubmitText(@StringRes resId: Int) {
        binding.btnSubmit.setText(resId)
    }

    fun hideCancel(flag: Boolean) {
        val visibility = if (flag) View.GONE else View.VISIBLE
        binding.btnCancel.visibility = visibility
        binding.lineV.visibility = visibility
    }

    fun hideSubmit(flag: Boolean) {
        val visibility = if (flag) View.GONE else View.VISIBLE
        binding.btnSubmit.visibility = visibility
        binding.lineV.visibility = visibility
    }

    /**
     * 设置取消按钮
     */
    fun doOnCancel(callback: ((dialog: ToastDialog, view: View) -> Unit)?) {
        cancelCallback = callback
    }

    /**
     * 设置确定按钮
     */
    fun doOnSubmit(callback: ((dialog: ToastDialog, view: View) -> Unit)?) {
        submitCallback = callback
    }

    class Builder(private val context: Context) {
        private var message: String? = null
        private var cancelText: String? = null
        private var submitText: String? = null
        private var isHideCancel = false
        private var isHideSubmit = false
        private var cancelCallback: ((dialog: ToastDialog, view: View) -> Unit)? = null
        private var submitCallback: ((dialog: ToastDialog, view: View) -> Unit)? = null

        fun setMessage(msg: String?): Builder = apply {
            this.message = msg
        }

        fun setMessage(@StringRes resId: Int) = apply {
            this.message = context.getString(resId)
        }

        fun setCancelText(text: String?) = apply {
            this.cancelText = text
        }

        fun setCancelText(@StringRes resId: Int) = apply {
            this.cancelText = context.getString(resId)
        }

        fun setSubmitText(text: String?) = apply {
            this.submitText = text
        }

        fun setSubmitText(@StringRes resId: Int) = apply {
            this.submitText = context.getString(resId)
        }

        fun hideCancel() = apply {
            this.isHideCancel = true
        }

        fun hideSubmit() = apply {
            this.isHideSubmit = true
        }

        fun doOnCancel(callback: ((dialog: ToastDialog, view: View) -> Unit)?) = apply {
            this.cancelCallback = callback
        }

        fun doOnSubmit(callback: ((dialog: ToastDialog, view: View) -> Unit)?) = apply {
            this.submitCallback = callback
        }

        fun create() = ToastDialog(context).apply {
            setMessage(message)
            cancelText?.let { setCancelText(it) }
            submitText?.let { setSubmitText(it) }
            if (isHideCancel) hideCancel(true)
            if (isHideSubmit) hideSubmit(true)
            doOnCancel(this@Builder.cancelCallback)
            doOnSubmit(this@Builder.submitCallback)
        }

        fun show() = create().apply {
            show()
        }
    }
}