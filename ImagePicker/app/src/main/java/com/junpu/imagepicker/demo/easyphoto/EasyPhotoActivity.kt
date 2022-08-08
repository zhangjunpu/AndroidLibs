package com.junpu.imagepicker.demo.easyphoto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.junpu.imagepicker.demo.databinding.ActivityEasyPhotoBinding
import com.junpu.utils.dip
import com.junpu.utils.screenWidth
import com.junpu.viewbinding.binding

/**
 * EasyPhoto 相册
 * Github:https://github.com/HuanTanSheng/EasyPhotos
 * @author junpu
 * @date 2022/8/7
 */
class EasyPhotoActivity : AppCompatActivity() {

    private val binding by binding<ActivityEasyPhotoBinding>()
    private val photoAdapter by lazy { ImagePhotoAdapter((screenWidth - 4.dip * 4) / 3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            recyclerView.run {
                layoutManager = GridLayoutManager(context, 3)
                adapter = photoAdapter
            }
            btnPick.setOnClickListener {
                EasyPhotos.createAlbum(this@EasyPhotoActivity, false, false, GlideEngine.instance) // 是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快）
//                    .setFileProviderAuthority("com.junpu.imagepicker.demo.fileprovider")
//                    .setCameraLocation(Setting.LIST_FIRST) // 设置相机按钮在相册第一张，默认在右下角
//                    .complexSelector(false, 2, 5) // 是否只能选择单类型，视频数，图片数
//                    .setOriginalMenu(false, true, null) // 是否默认选中，是否可用，不可用时用户点击将toast信息。
                    .setPuzzleMenu(false) // 是否显示拼图按钮
                    .setCleanMenu(false) // 是否显示清空按钮
                    .setCount(5) // 最大可选数，默认1
                    .start(object : SelectCallback() {
                        override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                            photos?.let { photoAdapter.addAll(it) }
                        }

                        override fun onCancel() {
                        }
                    })
            }
        }
    }

}