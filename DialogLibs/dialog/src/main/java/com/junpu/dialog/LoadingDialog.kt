package com.junpu.dialog

import android.content.Context
import com.junpu.dialog.databinding.DialogLoadingBinding

/**
 * Loading dialog.
 *
 * @author junpu
 * @date 2019-07-28
 */
class LoadingDialog(context: Context) : BaseDialog(context) {

    private val binding =
        DialogLoadingBinding.inflate(layoutInflater, contentView, contentView != null)

    /**
     * 设置message
     */
    fun setMessage(msg: String?) {
        msg?.let { binding.textMessage.text = it }
    }

    /**
     * 设置message
     */
    fun setMessage(resId: Int) {
        binding.textMessage.setText(resId)
    }

    class Builder(private val context: Context) {
        private var cancelable = false
        private var cancelOnTouchOutside = false
        private var message: String? = null

        fun setMessage(resId: Int) = apply {
            message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?) = apply {
            message = msg
        }

        fun setCancelable(flag: Boolean) = apply {
            cancelable = flag
        }

        fun setCanceledOnTouchOutside(cancel: Boolean) = apply {
            cancelOnTouchOutside = cancel
        }

        fun create() = LoadingDialog(context).apply {
            setCancelable(cancelable)
            setCanceledOnTouchOutside(cancelOnTouchOutside)
            setMessage(message)
        }

        fun show() = create().apply {
            show()
        }
    }
}