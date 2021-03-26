@file:JvmName("InputHelper")

package com.junpu.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.showInputMethod(view: View) = inputMethodManager.showSoftInput(view, 0)

fun Context.showForceInputMethod(view: View) =
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)

fun Context.hideInputMethod(view: View) =
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

fun Context.toggleInputMethod() =
    inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN)

fun View.showInputMethod() = context.inputMethodManager.showSoftInput(this, 0)

fun View.hideInputMethod() = context.inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
