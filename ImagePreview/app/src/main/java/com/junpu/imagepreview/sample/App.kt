package com.junpu.imagepreview.sample

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext

/**
 * @author Junpu
 * *
 * @time 2017/5/5 14:14
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        toastContext = this
        L.logEnable = true
    }
}
