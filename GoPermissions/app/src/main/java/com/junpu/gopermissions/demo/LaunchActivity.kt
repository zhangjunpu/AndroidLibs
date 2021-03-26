package com.junpu.gopermissions.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.log.L

/**
 *
 * @author junpu
 * @date 2020/7/13
 */
class LaunchActivity : PermissionsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        checkPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            if (it) startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}