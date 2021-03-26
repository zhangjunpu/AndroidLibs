package com.junpu.imagepreview.sample

import android.Manifest
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepreview.ImagePreviewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PermissionsActivity() {

    private var url = "/storage/emulated/0/Download/1.jpg"

    private val urls = arrayOf(
            "/storage/emulated/0/Download/1.jpg",
            "/storage/emulated/0/Download/2.jpg",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        single.setOnClickListener {
            ImagePreviewActivity.launch(this, url)
        }
        multiple.setOnClickListener {
            ImagePreviewActivity.launch(this, urls, 1)
        }

        checkPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

}
