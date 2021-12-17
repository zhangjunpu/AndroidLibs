@file:JvmName("ToastHelper")

package com.junpu.toast

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Toast
 * @author junpu
 * @date 2019-08-06
 */

lateinit var toastContext: Application
private var toast: Toast? = null

fun toast(msg: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    msg?.let {
        toast?.cancel()
        toast = Toast(toastContext).apply {
            view = View.inflate(toastContext.applicationContext, R.layout.toast_view, null).apply {
                findViewById<TextView>(R.id.textToast).text = it
            }
            this.duration = duration
//            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}

fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (resId != 0) {
        try {
            toast(toastContext.getString(resId), duration)
        } catch (e: Exception) {
            Log.e("System.err", Log.getStackTraceString(e))
        }
    }
}
