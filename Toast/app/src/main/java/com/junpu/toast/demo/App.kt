package com.junpu.toast.demo

import android.app.Application
import com.junpu.toast.toastContext

/**
 *
 * @author junpu
 * @date 2020/7/14
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        toastContext = this
    }
}