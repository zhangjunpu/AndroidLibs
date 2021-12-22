package com.junpu.widget.emptyview.demo

import android.app.Application
import com.junpu.toast.toastContext

/**
 *
 * @author junpu
 * @date 2021/12/22
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        toastContext = this
    }
}