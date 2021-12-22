package com.junpu.imagepicker.demo

import android.app.Application
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 *
 * @author junpu
 * @date 2021/12/22
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        toastContext = this
        app = this
    }
}