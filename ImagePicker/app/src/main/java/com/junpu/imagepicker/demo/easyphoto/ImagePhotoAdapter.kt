package com.junpu.imagepicker.demo.easyphoto

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.junpu.imagepicker.demo.databinding.ItemPhotoBinding
import com.junpu.viewbinding.binding

/**
 * 相册
 * @author junpu
 * @date 2022/8/7
 */
class ImagePhotoAdapter(private val itemViewSize: Int) : RecyclerView.Adapter<ImageHolder>() {

    private val data = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(parent.binding<ItemPhotoBinding>().apply {
            root.layoutParams.run {
                width = itemViewSize
                height = itemViewSize
            }
        })
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun addAll(list: List<Photo>) {
        val start = itemCount
        data.addAll(list)
        notifyItemRangeInserted(start, list.size)
    }
}

class ImageHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(t: Photo) {
        binding.imgPhoto.let {
            Glide.with(it).load(t.uri).into(it)
        }
    }
}