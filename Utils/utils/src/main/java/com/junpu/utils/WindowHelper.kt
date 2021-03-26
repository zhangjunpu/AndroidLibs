@file:JvmName("WindowHelper")

package com.junpu.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment

/**
 * Window工具类
 * @author junpu
 * @date 2019-07-30
 */

val Context.windowManager: WindowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

/**
 * 获取屏幕尺寸
 */
val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Fragment.screenWidth: Int
    get() = requireContext().screenWidth

val Fragment.screenHeight: Int
    get() = requireContext().screenHeight

val View.screenWidth: Int
    get() = context.screenWidth

val View.screenHeight: Int
    get() = context.screenHeight

val Window.screenWidth: Int
    get() = context.screenWidth

val Window.screenHeight: Int
    get() = context.screenHeight


val Context.realDisplayMetrics
    get() = DisplayMetrics().also { display?.getRealMetrics(it) }

val Fragment.realDisplayMetrics: DisplayMetrics
    get() = requireContext().realDisplayMetrics

val View.realDisplayMetrics: DisplayMetrics
    get() = context.realDisplayMetrics

val Window.realDisplayMetrics: DisplayMetrics
    get() = context.realDisplayMetrics


val Context.realScreenWidth: Int
    get() = realDisplayMetrics.widthPixels

val Context.realScreenHeight: Int
    get() = realDisplayMetrics.heightPixels

val Fragment.realScreenWidth: Int
    get() = realDisplayMetrics.widthPixels

val Fragment.realScreenHeight: Int
    get() = realDisplayMetrics.heightPixels

val View.realScreenWidth: Int
    get() = realDisplayMetrics.widthPixels

val View.realScreenHeight: Int
    get() = realDisplayMetrics.heightPixels

val Window.realScreenWidth: Int
    get() = realDisplayMetrics.widthPixels

val Window.realScreenHeight: Int
    get() = realDisplayMetrics.heightPixels
