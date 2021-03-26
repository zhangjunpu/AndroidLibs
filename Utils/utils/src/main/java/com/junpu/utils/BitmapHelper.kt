@file:JvmName("BitmapHelper")

package com.junpu.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
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
fun Bitmap.toByteArray(quality: Int = 100): ByteArray = ByteArrayOutputStream(byteCount).use {
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
fun convertBitmap(bytes: ByteArray, width: Int, height: Int): Bitmap {
//    val option = BitmapFactory.Options()
//    option.inSampleSize = 1
//    option.outWidth = width
//    option.outHeight = height
//    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, option)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val buffer = ByteBuffer.wrap(bytes)
//    val buffer = ByteBuffer.allocate(bitmap.byteCount)

    val elements = buffer.remaining()
    val bufferBytes = elements.toLong() shl 0
    val bitmapBytes = bitmap.byteCount

    bitmap.copyPixelsFromBuffer(buffer)
    return bitmap
}

/**
 * 字节数组转换为Bitmap
 */
fun convertBitmap(bytes: ByteArray) = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
