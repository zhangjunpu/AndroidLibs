package com.junpu.imagepreview.sample

import android.Manifest
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepreview.ImagePreviewActivity
import com.junpu.imagepreview.sample.databinding.ActivityMainBinding

class MainActivity : PermissionsActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var url = "file:///android_asset/1.jpg"
    private val urls = arrayOf(
        "file:///android_asset/1.jpg",
        "file:///android_asset/2.jpg",
        "file:///android_asset/3.jpg",
        "file:///android_asset/4.jpg",
        "file:///android_asset/5.jpg",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.run {
            btnSingle.setOnClickListener {
                ImagePreviewActivity.launch(this@MainActivity, url)
            }
            btnMultiple.setOnClickListener {
                ImagePreviewActivity.launch(this@MainActivity, urls, 1)
            }
        }
        checkPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }
}
