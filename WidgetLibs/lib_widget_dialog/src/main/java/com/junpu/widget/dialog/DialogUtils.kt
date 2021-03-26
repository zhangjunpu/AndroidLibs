package com.junpu.widget.dialog

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Dialog Utils
 *
 * @author junpu
 * @date 2019-07-16
 */
internal object DialogUtils {

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        var width = 0
        if (context is Activity) width = context.window.decorView.measuredWidth
        if (width <= 0) width = getDisplayMetrics(context).widthPixels
        return width
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        var height = 0
        if (context is Activity) height = context.window.decorView.measuredHeight
        if (height <= 0) height = getDisplayMetrics(context).heightPixels
        return height
    }

    private fun getDisplayMetrics(context: Context) = context.resources.displayMetrics

    /**
     * 返回dp值
     */
    fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 返回dp值
     */
    fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun hideInputMethod(context: Context, view: View) {
        val imm =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}