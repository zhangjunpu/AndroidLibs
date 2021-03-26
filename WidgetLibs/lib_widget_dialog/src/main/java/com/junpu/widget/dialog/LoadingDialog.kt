package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.Window
import android.view.WindowManager
import android.widget.TextView

/**
 * Loading dialog.
 *
 * @author junpu
 * @date 2019-07-28
 */
class LoadingDialog : BaseDialog {

    private var messageView: TextView? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_loading

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
//        params?.dimAmount = 0f
    }

    override fun initView() {
        messageView = findViewById(R.id.textMessage)
    }

    /**
     * 设置message
     */
    fun setMessage(msg: String?) {
        msg?.let { messageView?.text = it }
    }

    /**
     * 设置message
     */
    fun setMessage(resId: Int) {
        messageView?.setText(resId)
    }

    class Builder(private val context: Context) {
        private var cancelable = false
        private var cancelOnTouchOutside = false
        private var message: String? = null

        fun setMessage(resId: Int): Builder = apply {
            message = context.resources.getString(resId)
        }

        fun setMessage(msg: String?): Builder = apply {
            message = msg
        }

        fun setCancelable(flag: Boolean): Builder = apply {
            cancelable = flag
        }

        fun setCanceledOnTouchOutside(cancel: Boolean): Builder = apply {
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