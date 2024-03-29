package com.junpu.imagepicker.demo

import android.Manifest
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepicker.demo.databinding.ActivityMainBinding
import com.junpu.imagepicker.demo.easyphoto.EasyPhotoActivity
import com.junpu.imagepicker.demo.system.SystemAlbumActivity
import com.junpu.utils.launch
import com.junpu.viewbinding.bindingOnly

/**
 * 主界面
 * @author junpu
 * @date 2022/8/7
 */
class MainActivity : PermissionsActivity() {

    private val binding by bindingOnly<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 请求权限
        checkPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )

        binding.run {
            btnSystemAlbum.setOnClickListener { launch<SystemAlbumActivity>() }
            btnEasyPhoto.setOnClickListener { launch<EasyPhotoActivity>() }
        }
    }
}