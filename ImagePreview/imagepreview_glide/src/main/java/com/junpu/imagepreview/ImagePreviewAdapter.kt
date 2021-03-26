package com.junpu.imagepreview

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoView

/**
 * 视频预览Adapter
 *
 * @author zhangjunpu
 * @date 14/11/21
 */
class ImagePreviewAdapter(
        private val context: Context,
        private var data: Array<out String>? = null,
) : PagerAdapter() {


    private val inflater = LayoutInflater.from(context)
    private var onViewTapListener: ((view: View, x: Float, y: Float) -> Unit)? = null

    fun doOnViewTap(callback: (view: View, x: Float, y: Float) -> Unit) {
        onViewTapListener = callback
    }

    fun getItem(position: Int) = data?.get(position)

    override fun getCount(): Int = data?.size ?: 0

    override fun isViewFromObject(view: View, any: Any): Boolean = view === any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        (any as? View)?.let { container.removeView(it) }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = inflater.inflate(R.layout.image_pager_item, container, false)
        val progress = layout.findViewById<ProgressBar>(R.id.loading)
        val imageView = layout.findViewById<PhotoView>(R.id.image).apply {
            setOnViewTapListener { view, x, y -> onViewTapListener?.invoke(view, x, y) }
        }
        val item = getItem(position)
        loadImageUrl(item, imageView, progress)
        container.addView(layout, 0)
        return layout
    }

    private fun loadImageUrl(url: String?, imageView: ImageView, progress: ProgressBar) {
        Glide.with(context)
                .load(url)
                .error(R.mipmap.ic_default_h)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView)
    }

}