package com.junpu.utils.exifinterface

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.IOException


/**
 * 图片工具类
 * @author junpu
 * @date 2020/5/29
 */

/**
 * 获取Exif
 */
fun getExif(path: String?): ExifInterface? {
    path ?: return null
    var exif: ExifInterface? = null
    try {
        exif = ExifInterface(path)
    } catch (e: Exception) {
        Log.e("System.err", e.stackTraceToString())
    }
    return exif
}

/**
 * 获取图片的exif的旋转角度
 */
fun ExifInterface.getOrientation(): Int {
    var orientation = 0
    try {
        when (getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> orientation = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> orientation = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> orientation = 270
        }
    } catch (e: IOException) {
        Log.e("System.err", e.stackTraceToString())
    }
    return orientation
}

/**
 * 获取Uri的真实地址（兼容android 7.0）
 */
fun Uri.getRealFilePath(context: Context): String? {
    var result: String? = null
    when (scheme) {
        ContentResolver.SCHEME_CONTENT -> {
            val labels = arrayOf(MediaStore.Images.ImageColumns.DATA)
            context.contentResolver.query(this, labels, null, null, null)?.run {
                if (moveToFirst()) {
                    val pathIndex = getColumnIndex(labels[0])
                    if (pathIndex > -1) result = getString(pathIndex)
                }
                close()
            }
        }
        else -> path
    }
    return result
}