@file:JvmName("Extends")

package com.junpu.imagepreview.sample

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * 扩展函数类
 * @author junpu
 * @time 2017/11/7 23:09
 */

fun Context.launch(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}

fun Fragment.launch(cls: Class<*>) {
    context?.launch(cls)
}
