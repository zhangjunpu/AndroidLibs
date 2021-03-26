package com.junpu.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.Window
import android.view.WindowManager

/**
 * Base Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
abstract class BaseDialog : Dialog {

    companion object {
        const val STATUS_LOADING = 0
        const val STATUS_SUCCESS = 1
        const val STATUS_ERROR = 2
        const val DEFAULT_ERROR = "数据错误"
    }

    constructor(
        context: Context,
        themeResId: Int = R.style.Theme_Dialog_Base,
        layoutRedId: Int = 0
    ) : super(context, themeResId) {
        init(layoutRedId)
    }

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        init()
    }

    private fun init(layoutRedId: Int = 0) {
        val layoutId = if (layoutRedId > 0) layoutRedId else layout()
        if (layoutId > 0) setContentView(layoutId)
        window?.run {
            val params = attributes.apply {
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            initWindow(this, params)
            attributes = params
        }
        initView()
    }

    /**
     * 初始化layout
     */
    protected abstract fun layout(): Int

    /**
     * 初始化window
     */
    protected open fun initWindow(window: Window, params: WindowManager.LayoutParams) {}

    /**
     * 初始化view
     */
    protected open fun initView() {}

    /**
     * 设置Dialog宽度
     */
    fun setWidth(width: Int) {
        window?.attributes = window?.attributes?.also {
            it.width = width
        }
    }

    /**
     * 设置Dialog高度
     */
    fun setHeight(height: Int) {
        window?.attributes = window?.attributes?.also {
            it.height = height
        }
    }

    /**
     * 设置Gravity
     */
    fun setGravity(gravity: Int) {
        window?.setGravity(Gravity.BOTTOM)
    }

    /**
     * loading状态
     */
    open fun loading() {
    }

    /**
     * 成功状态
     */
    open fun success() {
    }

    /**
     * 错误状态
     */
    open fun error(errorMsg: String?) {
    }

    /**
     * 设置状态 STATUS_LOADING, STATUS_SUCCESS, STATUS_ERROR
     */
    open fun setStatus(status: Int, errorMsg: String? = DEFAULT_ERROR) {
        when (status) {
            STATUS_LOADING -> loading()
            STATUS_SUCCESS -> success()
            STATUS_ERROR -> error(errorMsg)
        }
    }

}