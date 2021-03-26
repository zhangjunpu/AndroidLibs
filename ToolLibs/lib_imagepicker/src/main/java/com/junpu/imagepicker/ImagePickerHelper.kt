package com.junpu.imagepicker

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

/**
 *
 * @author junpu
 * @date 2020/9/11
 */

/**
 * 通过uri获取文件的绝对路径
 */
fun Uri.getAbsolutePath(context: Context?): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    context?.contentResolver?.query(this, projection, null, null, null)?.use {
        if (it.moveToFirst()) return it.getString(it.getColumnIndexOrThrow(projection[0]))
    }
    return null
}
