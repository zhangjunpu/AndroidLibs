package com.junpu.imagepicker

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Rational
import android.util.Size
import java.io.File

/**
 * 图片拍摄、裁剪、选择器
 */
open class ImagePicker(
    private var context: Context,
    private var isCrop: Boolean = false,
    private var cropRatio: Rational? = null,
    private var cropSize: Size? = null
) : IImagePicker {

    private var imageCachePath: String? = null // 图片缓存路径
    private var cameraUri: Uri? = null // 拍照路径
    private var cropUri: Uri? = null // 剪裁路径

    private var captureCallback: ((uri: Uri?, path: String?) -> Unit)? = null
    private var pickCallback: ((uri: Uri?, path: String?) -> Unit)? = null
    private var cropCallback: ((uri: Uri?, path: String?) -> Unit)? = null

    init {
        // 初始化图片缓存路径
        imageCachePath ?: context.externalCacheDir?.absolutePath?.also { imageCachePath = it }
    }

    open fun startActivityForResult(intent: Intent, requestCode: Int) {
        (context as? Activity)?.startActivityForResult(intent, requestCode)
    }

    /**
     * 相机拍照
     */
    override fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra("output", newCameraUri())
            .let { startActivityForResult(it, REQUEST_CODE_IMAGE_CAMERA) }
    }

    /**
     * 选择图片
     */
    override fun startPick() {
        val intent = Intent(Intent.ACTION_PICK)
            .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_IMAGE_PICK)
    }

    /**
     * 裁剪，默认1:1的正方形图片，大小300x300
     * @param uri    原始图片
     * @param ratio  裁剪比例 1：1
     * @param size   输出图片基础尺寸 300x300
     */
    override fun startCrop(uri: Uri, ratio: Rational?, size: Size?) {
        Intent("com.android.camera.action.CROP")
            .setDataAndType(uri, "image/*")
            .putExtra("output", newCropUri())
            .putExtra("crop", "true") // 可裁剪
            .putExtra("aspectX", ratio?.numerator ?: DEFAULT_RATIO) // 裁剪框比例
            .putExtra("aspectY", ratio?.denominator ?: DEFAULT_RATIO)
            .putExtra("outputX", size?.width ?: DEFAULT_CROP) // 输出图片大小
            .putExtra("outputY", size?.height ?: DEFAULT_CROP)
            .putExtra("scale", true) // 是否支持缩放
            .putExtra("scaleUpIfNeeded", true) // 去黑边
            .let { startActivityForResult(it, REQUEST_CODE_IMAGE_CROP) }
    }

    /**
     * 处理回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_CAMERA -> cameraUri?.let {
                    captureCallback?.invoke(it, it.getAbsolutePath(context))
                    if (isCrop) startCrop(it, cropRatio, cropSize)
                }
                REQUEST_CODE_IMAGE_PICK -> data?.data?.let {
                    pickCallback?.invoke(it, it.getAbsolutePath(context))
                    if (isCrop) startCrop(it, cropRatio, cropSize)
                }
                REQUEST_CODE_IMAGE_CROP -> cropUri?.let {
                    cropCallback?.invoke(it, it.path)
                }
            }
        }
    }

    /**
     * 拍照回调
     */
    override fun doOnCapture(callback: (uri: Uri?, path: String?) -> Unit) {
        this.captureCallback = callback
    }

    /**
     * 选择回调
     */
    override fun doOnPick(callback: (uri: Uri?, path: String?) -> Unit) {
        this.pickCallback = callback
    }

    /**
     * 裁剪回调
     */
    override fun doOnCrop(callback: (uri: Uri?, path: String?) -> Unit) {
        this.cropCallback = callback
    }

    /**
     * 拍照图像Uri
     */
    private fun newCameraUri(): Uri? {
        val dir = File(imageCachePath, DIR_NAME_CAMERA)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "camera_${System.currentTimeMillis()}.jpg")
        return context.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues(1).apply {
                put(MediaStore.Images.Media.DATA, file.absolutePath)
            }
        ).also { cameraUri = it }
    }

    /**
     * 裁剪图像Uri
     */
    private fun newCropUri(): Uri? {
        val dir = File(imageCachePath, DIR_NAME_CROP)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "crop_${System.currentTimeMillis()}.jpg")
        return Uri.fromFile(file).also { cropUri = it }
    }

    companion object {
        const val REQUEST_CODE_IMAGE_CAMERA = 100 // 拍照
        const val REQUEST_CODE_IMAGE_PICK = 101 // 选图
        const val REQUEST_CODE_IMAGE_CROP = 102 // 剪裁
        private const val DEFAULT_RATIO = 1
        private const val DEFAULT_CROP = 500 // 默认剪裁尺寸 500
        private const val DIR_NAME_CAMERA = "camera" // 拍照缓存文件夹名称
        private const val DIR_NAME_CROP = "crop" // 裁剪缓存文件夹名称
    }

}