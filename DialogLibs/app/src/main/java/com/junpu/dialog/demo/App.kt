package com.junpu.dialog.demo

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 *
 * @author junpu
 * @date 2021/12/13
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        L.logEnable = true
        toastContext = this
        app = this
    }
}