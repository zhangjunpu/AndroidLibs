@file:JvmName("BitmapHelper")

package com.junpu.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Size
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer


/**
 * Bitmap
 * @author junpu
 * @date 2019-07-31
 */


/**
 * Image转换成bitmap
 */
fun Image.toBitmap(): Bitmap? {
    val bytes = toByteArray() ?: return null
    return convertBitmap(bytes)
}

/**
 * Image转换为字节数组
 */
fun Image.toByteArray(): ByteArray? {
    if (planes?.isEmpty() == true) return null
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return bytes
}

/**
 * Bitmap转换为字节数组JPEG
 */
fun Bitmap.toByteArray(quality: Int = 100): ByteArray = ByteArrayOutputStream(byteCount).let {
    compress(Bitmap.CompressFormat.JPEG, quality, it)
    it.toByteArray()
}

/**
 * Bitmap转文件
 */
fun Bitmap.toFile(path: String, quality: Int = 100) = FileOutputStream(path).use {
    compress(Bitmap.CompressFormat.JPEG, quality, it)
    it.flush()
}

/**
 * 字节数组转换为Bitmap
 */
fun convertBitmap(bytes: ByteArray, width: Int, height: Int): Bitmap =
    Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
        copyPixelsFromBuffer(ByteBuffer.wrap(bytes))
    }

/**
 * 字节数组转换为Bitmap
 */
fun convertBitmap(bytes: ByteArray): Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

fun Bitmap.contentString() = "$width/$height"

/**
 * 等比缩放原始尺寸为目标尺寸
 * @param width 原始宽度
 * @param height 原始高度
 * @param maxWidth 最大限制宽度，默认1600
 * @param maxHeight 最大限制高度，默认1600
 * @param isResizeMini 如果原始尺寸小于目标尺寸，是否缩放的，默认false
 */
fun resizeImage(
    width: Int,
    height: Int,
    maxWidth: Int = 1600,
    maxHeight: Int = 1600,
    isResizeMini: Boolean = false
): Size {
    val srcRatio = width / height.toFloat()
    val destRatio = maxWidth / maxHeight.toFloat()
    var w = width
    var h = height
    if (srcRatio >= destRatio && (width > maxWidth || isResizeMini)) {
        w = maxWidth
        h = (maxWidth / srcRatio).toInt()
    } else if (srcRatio < destRatio && (height > maxHeight || isResizeMini)) {
        h = maxHeight
        w = (maxHeight * srcRatio).toInt()
    }
    return Size(w, h)
}