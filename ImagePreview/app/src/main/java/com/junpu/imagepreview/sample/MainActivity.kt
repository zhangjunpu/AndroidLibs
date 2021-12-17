package com.junpu.imagepreview.sample

import android.Manifest
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepreview.ImagePreviewActivity
import com.junpu.imagepreview.sample.databinding.ActivityMainBinding

class MainActivity : PermissionsActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var url = "/storage/emulated/0/Download/错题图片/origin/小学数学/math_01.jpg"
    private val urls = arrayOf(
        "/storage/emulated/0/Download/错题图片/origin/小学数学/math_01.jpg",
        "/storage/emulated/0/Download/错题图片/origin/小学数学/math_02.jpg",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.run {
            single.setOnClickListener {
                ImagePreviewActivity.launch(this@MainActivity, url)
            }
            multiple.setOnClickListener {
                ImagePreviewActivity.launch(this@MainActivity, urls, 1)
            }
        }
        checkPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

}
