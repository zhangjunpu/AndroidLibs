package com.junpu.imagepicker.demo.easyphoto

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.huantansheng.easyphotos.engine.ImageEngine

/**
 * Glide4.x的加载图片引擎实现,单例模式
 * Glide4.x的缓存机制更加智能，已经达到无需配置的境界。如果使用Glide3.x，需要考虑缓存机制。
 * Created by huan on 2018/1/15.
 */
class GlideEngine private constructor() : ImageEngine {
    /**
     * 加载图片到ImageView
     */
    override fun loadPhoto(context: Context, uri: Uri, imageView: ImageView) {
        Glide.with(context).load(uri).transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
    }

    /**
     * 加载gif动图图片到ImageView，gif动图不动
     * 备注：不支持动图显示的情况下可以不写
     */
    override fun loadGifAsBitmap(context: Context, gifUri: Uri, imageView: ImageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView)
    }

    /**
     * 加载gif动图到ImageView，gif动图动
     * 备注：不支持动图显示的情况下可以不写
     */
    override fun loadGif(context: Context, gifUri: Uri, imageView: ImageView) {
        Glide.with(context).asGif().load(gifUri).transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
    }

    /**
     * 获取图片加载框架中的缓存Bitmap，不用拼图功能可以直接返回null
     * width, height 图片宽高；
     * 异常直接抛出，EasyPhotos内部处理
     */
    @Throws(Exception::class)
    override fun getCacheBitmap(context: Context, uri: Uri, width: Int, height: Int): Bitmap {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get()
    }

    companion object {
        @JvmStatic
        val instance by lazy { GlideEngine() }
    }
}