package com.junpu.dialog

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.junpu.dialog.databinding.DialogCustomViewBinding

/**
 * CustomView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
class CustomViewDialog(context: Context) : BaseDialog(context, R.style.Dialog_Theme_Bottom) {

    private val binding by lazy {
        DialogCustomViewBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    init {
        loading()
        setGravityBottom()
        setWidthMatchParent()
    }

    fun setCustomView(view: View?) {
        binding.layoutContent.addView(view)
    }

    fun setCustomView(view: View?, params: FrameLayout.LayoutParams?) {
        binding.layoutContent.addView(view, params)
    }

    fun removeCustomView() {
        binding.layoutContent.removeAllViews()
    }

    fun setTitle(title: String?) {
        binding.run {
            textTitle.text = title
            textTitle.visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    fun setSubTitle(subTitle: String?) {
        binding.run {
            textSubTitle.text = subTitle
            textSubTitle.visibility = if (subTitle.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    fun setMessage(message: String?) {
        binding.run {
            textMessage.text = message
            textMessage.visibility = if (message.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    /**
     * loading状态
     */
    override fun loading() {
        binding.run {
            progress.visibility = View.VISIBLE
            layoutContent.visibility = View.INVISIBLE
            textErrorMessage.visibility = View.GONE
        }
    }

    /**
     * 成功状态
     */
    override fun success() {
        binding.run {
            progress.visibility = View.GONE
            layoutContent.visibility = View.VISIBLE
            textErrorMessage.visibility = View.GONE
        }
    }

    /**
     * 错误状态
     */
    override fun error(errorMsg: String?) {
        binding.run {
            progress.visibility = View.GONE
            layoutContent.visibility = View.INVISIBLE
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
        private var customView: View? = null
        private var layoutParams: FrameLayout.LayoutParams? = null
        private var isRemoveCustomView = false

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

        fun setCustomView(view: View?) = apply {
            this.customView = view
        }

        fun setCustomView(view: View?, params: FrameLayout.LayoutParams?) = apply {
            this.customView = view
            this.layoutParams = params
        }

        fun removeCustomView() = apply {
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
                layoutParams?.run { setCustomView(it, this) } ?: setCustomView(it)
            }
        }

        fun show() = create().apply {
            show()
        }
    }

}