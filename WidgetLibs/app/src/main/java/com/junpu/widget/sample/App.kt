package com.junpu.widget.sample

import android.app.Application
import com.junpu.toast.toastContext

/**
 *
 * @author junpu
 * @date 2020/9/3
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        toastContext = this
    }
}