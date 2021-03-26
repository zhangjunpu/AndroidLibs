package com.junpu.gopermissions

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * 检查、请求权限Activity
 * @author junpu
 * @date 2020/7/13
 */
open class PermissionsActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST = 0x010
        private const val PERMISSION_REQUEST_AGAIN = 0x011
    }

    private var permissionListener: ((isSuccess: Boolean) -> Unit)? = null

    /**
     * 请求权限回调
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST, PERMISSION_REQUEST_AGAIN -> {
                val flag = grantResults.any { it == PackageManager.PERMISSION_DENIED }
                if (flag) {
                    Toast.makeText(this, getString(R.string.permission_tips), Toast.LENGTH_SHORT)
                        .show()
                    permissionListener?.invoke(false)
                    return
                }
                permissionListener?.invoke(true)
            }
        }
    }

    /**
     * 检查权限
     */
    protected fun checkPermissions(
        permissions: Array<String>,
        listener: ((isSuccess: Boolean) -> Unit)? = null
    ) {
        this.permissionListener = listener
        // 检查是否有的权限还没有获取
        val havePermissions = permissions.any {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        // 权限都已获取
        if (!havePermissions) {
            permissionListener?.invoke(true)
            return
        }
        // 检查是否有被拒绝并且勾选了不再提示的权限
        val shouldShow = permissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }
        // 首次申请
        if (!shouldShow) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST)
            return
        }
        AlertDialog.Builder(this)
            .setMessage(R.string.permission_tips)
            .setPositiveButton(R.string.agree) { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    PERMISSION_REQUEST_AGAIN
                )
            }
            .setNegativeButton(R.string.reject) { _, _ -> listener?.invoke(false) }
            .setCancelable(false)
            .create()
            .apply {
                show()
                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(this@PermissionsActivity, R.color.permissions_blue)
                )
                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(this@PermissionsActivity, R.color.permissions_orange)
                )
            }
    }
}
