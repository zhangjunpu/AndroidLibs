package com.junpu.imagepicker.demo.system

import android.Manifest
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepicker.demo.databinding.ActivitySystemAlbumBinding
import com.junpu.viewbinding.bindingOnly

/**
 * 系统相册
 * @author junpu
 * @date 2022/8/7
 */
class SystemAlbumActivity : PermissionsActivity() {

    private val binding by bindingOnly<ActivitySystemAlbumBinding>()

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
    }
}