package com.junpu.fragment.tab.demo

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 * @author junpu
 * *
 * @time 2017/8/3 14:38
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        L.logEnable = true
        toastContext = this
        app = this
    }
}
