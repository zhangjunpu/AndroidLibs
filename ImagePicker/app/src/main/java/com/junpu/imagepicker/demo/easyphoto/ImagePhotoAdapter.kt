package com.junpu.imagepicker.demo.easyphoto

import android.graphics.Bitmap
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junpu.imagepicker.demo.databinding.ItemPhotoBinding
import com.junpu.viewbinding.binding

/**
 * 相册
 * @author junpu
 * @date 2022/8/7
 */
class ImagePhotoAdapter : RecyclerView.Adapter<ImageHolder>() {
    private val data = mutableListOf<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(parent.binding())
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class ImageHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(t: Bitmap) {
        binding.imgPhoto.setImageBitmap(t)
    }
}