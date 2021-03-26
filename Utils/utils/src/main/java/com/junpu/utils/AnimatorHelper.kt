@file:JvmName("AnimatorHelper")

package com.junpu.utils

import android.view.View

/**
 * Animator动画包装类
 * @author junpu
 * @date 2019-08-14
 */
abstract class ViewAnimatorWrapper<T>(protected val view: View) {
    abstract fun getAnimatorValue(): T
    abstract fun setAnimatorValue(t: T)
}