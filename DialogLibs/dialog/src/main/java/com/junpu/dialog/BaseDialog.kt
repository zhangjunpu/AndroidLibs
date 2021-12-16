package com.junpu.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

/**
 * Base Dialog
 * 使用 setContentView(layoutResId: Int)，layout.xml 最外层的参数生效；
 * 使用 setContentView(view: View)，layout.xml 最外层的参数会失效；
 * 原因：setContentView(View) 会调用 setContentView(View, LayoutParams) 方法，而它的 LayoutParams 参数是新 new 的；
 * 解决方法：
 * 1. val contentView = window?.findViewById(Window.ID_ANDROID_CONTENT) 获取 ViewGroup；
 * 2. inflate(layoutInflate, contentView, contentView != null)
 *
 * @author junpu
 * @date 2019-07-16
 */
abstract class BaseDialog(
    context: Context,
    themeResId: Int = R.style.Dialog_Theme_Base,
) : Dialog(context, themeResId) {

    protected val contentView: ViewGroup?
        get() = window?.findViewById(Window.ID_ANDROID_CONTENT)

    fun setWidth(width: Int) {
        window?.attributes?.width = width
    }

    fun setHeight(height: Int) {
        window?.attributes?.height = height
    }

    fun setGravity(gravity: Int) {
        window?.setGravity(Gravity.BOTTOM)
    }

    fun setWidthMatchParent() {
        setWidth(WindowManager.LayoutParams.MATCH_PARENT)
    }

    fun setHeightMatchParent() {
        setHeight(WindowManager.LayoutParams.MATCH_PARENT)
    }

    fun setGravityBottom() {
        window?.setGravity(Gravity.BOTTOM)
    }

    /**
     * 黑色背景遮罩透明度，0 ~ 1；
     */
    fun setDim(dim: Float) {
        window?.attributes?.dimAmount = dim
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

    companion object {
        const val STATUS_LOADING = 0
        const val STATUS_SUCCESS = 1
        const val STATUS_ERROR = 2
        const val DEFAULT_ERROR = "数据错误"
    }

}