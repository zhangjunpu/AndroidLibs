@file:JvmName("Extends")

package com.junpu.tool.sample

import android.content.Context
import android.content.Intent
import android.widget.TextView

/**
 *
 * @author Junpu
 * @time 2018/1/30 16:24
 */

fun Context.launch(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}

fun androidx.fragment.app.Fragment.launch(cls: Class<*>) {
    activity?.launch(cls)
}

fun TextView.appendLine(s: String) {
    append(s)
    append("\n")
}