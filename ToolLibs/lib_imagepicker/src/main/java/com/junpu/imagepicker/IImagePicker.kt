package com.junpu.imagepicker

import android.content.Intent
import android.net.Uri
import android.util.Rational
import android.util.Size

/**
 *
 * @author junpu
 * @date 2020/9/11
 */
interface IImagePicker {
    /**
     * 拍照
     */
    fun startCamera()

    /**
     * 选择图片
     */
    fun startPick()

    /**
     * 剪裁
     */
    fun startCrop(uri: Uri, ratio: Rational? = null, size: Size? = null)

    /**
     * 回调
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * 拍照回调
     */
    fun doOnCapture(callback: (uri: Uri?, path: String?) -> Unit)

    /**
     * 选择回调
     */
    fun doOnPick(callback: (uri: Uri?, path: String?) -> Unit)

    /**
     * 裁剪回调
     */
    fun doOnCrop(callback: (uri: Uri?, path: String?) -> Unit)
}