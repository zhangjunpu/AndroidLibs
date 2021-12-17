package com.junpu.widget.sample

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 *
 * @author junpu
 * @date 2020/9/3
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        toastContext = this
        L.logEnable = true
        app = this
    }
}