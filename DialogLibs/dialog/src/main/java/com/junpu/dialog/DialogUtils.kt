package com.junpu.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Dialog Utils
 *
 * @author junpu
 * @date 2019-07-16
 */

/**
 * 获取屏幕宽度
 */
internal val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
internal val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

/**
 * 返回dp值
 */
internal fun Context.dip(dp: Int) = dip(dp.toFloat())

internal fun Context.dip(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

internal fun Context.hideInputMethod(view: View) {
    val imm =
        applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

internal fun Context.getAttr(attrId: Int): Drawable? {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    val intArr = intArrayOf(attrId)
    val a = theme.obtainStyledAttributes(typedValue.resourceId, intArr)
    val drawable = a.getDrawable(0)
    a.recycle()
    return drawable
}