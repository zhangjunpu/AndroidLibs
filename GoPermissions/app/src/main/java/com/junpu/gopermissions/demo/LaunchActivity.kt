package com.junpu.gopermissions.demo

import android.Manifest
import android.content.Intent
import com.junpu.gopermissions.PermissionsActivity

/**
 *
 * @author junpu
 * @date 2020/7/13
 */
class LaunchActivity : PermissionsActivity() {

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