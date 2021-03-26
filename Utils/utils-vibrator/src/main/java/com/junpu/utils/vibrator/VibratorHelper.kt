package com.junpu.utils.vibrator

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * 震动帮助类
 * @author junpu
 * @date 2019-10-19
 */

/**
 * 震动：一次短暂震动
 */
fun Context.vibrateOneShot(duration: Long = 50) {
    val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(duration)
        }
    }
}