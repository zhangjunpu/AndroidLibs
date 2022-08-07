package com.junpu.imagepicker.demo.easyphoto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.huantansheng.easyphotos.EasyPhotos
import com.junpu.imagepicker.demo.databinding.ActivityEasyPhotoBinding
import com.junpu.viewbinding.binding

/**
 * EasyPhoto 相册
 * Github:https://github.com/HuanTanSheng/EasyPhotos
 * @author junpu
 * @date 2022/8/7
 */
class EasyPhotoActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_ALBUM = 100
    }

    private val binding by binding<ActivityEasyPhotoBinding>()
    private val photoAdapter by lazy { ImagePhotoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            recyclerView.run {
                layoutManager = GridLayoutManager(context, 3)
                adapter = photoAdapter
            }
            btnPick.setOnClickListener {
                // 参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），
                // [配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-配置ImageEngine，支持所有图片加载库)
                // setCount：最大可选数，默认1
                EasyPhotos.createAlbum(this@EasyPhotoActivity, false, false, GlideEngine.getInstance())
                    .setCount(9)
                    .start(REQUEST_ALBUM)
            }
        }
    }

}