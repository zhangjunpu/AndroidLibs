package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView

/**
 * CustomView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
class CustomViewDialog : BaseDialog {

    private var textTitle: TextView? = null
    private var textSubTitle: TextView? = null
    private var textMessage: TextView? = null
    private var customLayout: FrameLayout? = null
    private var progressBar: ProgressBar? = null
    private var textErrorMessage: TextView? = null

    constructor(context: Context) : super(context, R.style.Theme_Dialog_Recycler)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_custom_view

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        window.setGravity(Gravity.BOTTOM)
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun initView() {
        textTitle = findViewById(R.id.textTitle)
        textSubTitle = findViewById(R.id.textSubTitle)
        textMessage = findViewById(R.id.textMessage)
        customLayout = findViewById(R.id.layoutContent)
        progressBar = findViewById(R.id.progress)
        textErrorMessage = findViewById(R.id.errorMessage)
        loading()
    }

    fun setCustomView(view: View?) {
        customLayout?.addView(view)
    }

    fun setCustomView(view: View?, params: FrameLayout.LayoutParams?) {
        customLayout?.addView(view, params)
    }

    fun removeCustomView() {
        customLayout?.removeAllViews()
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
        customLayout?.visibility = View.INVISIBLE
        textErrorMessage?.visibility = View.GONE
    }

    /**
     * 成功状态
     */
    override fun success() {
        progressBar?.visibility = View.GONE
        customLayout?.visibility = View.VISIBLE
        textErrorMessage?.visibility = View.GONE
    }

    /**
     * 错误状态
     */
    override fun error(errorMsg: String?) {
        progressBar?.visibility = View.GONE
        customLayout?.visibility = View.INVISIBLE
        textErrorMessage?.visibility = View.VISIBLE
        textErrorMessage?.text = errorMsg
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var subTitle: String? = null
        private var message: String? = null
        private var width = -3
        private var height = -3
        private var customView: View? = null
        private var layoutParams: FrameLayout.LayoutParams? = null
        private var isRemoveCustomView = false

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

        fun setCustomView(view: View?): Builder = apply {
            this.customView = view
        }

        fun setCustomView(view: View?, params: FrameLayout.LayoutParams?): Builder = apply {
            this.customView = view
            this.layoutParams = params
        }

        fun removeCustomView(): Builder = apply {
            this.isRemoveCustomView = true
        }

        fun create() = CustomViewDialog(context).apply {
            setTitle(title)
            setSubTitle(subTitle)
            setMessage(message)
            if (width != -3) setWidth(width)
            if (height != -3) setHeight(height)
            if (isRemoveCustomView) removeCustomView()
            customView?.let {
                layoutParams?.run {
                    setCustomView(it, this)
                } ?: setCustomView(it)
            }
        }

        fun show() = create().apply {
            show()
        }
    }

}