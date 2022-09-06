package com.junpu.upgrade.demo

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azhon.appupdate.listener.OnButtonClickListener
import com.azhon.appupdate.listener.OnDownloadListenerAdapter
import com.azhon.appupdate.manager.DownloadManager
import com.junpu.log.L
import com.junpu.upgrade.demo.databinding.ActivityMainBinding
import com.junpu.viewbinding.binding

/**
 * 版本更新
 * @author Junpu
 * @date 2022/9/6
 */
class MainActivity : AppCompatActivity() {

    private val bind by binding<ActivityMainBinding>()

    private var manager: DownloadManager? = null
    private val url = "https://down.qq.com/qqweb/QQ_1/android_apk/Android_8.7.0.5295_537068059.apk"
    private val desc = "1. AppUpdate 库测试；\n2. 使用测试；\n3. 版本更新测试；\n4. fix bugs；"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        bind.run {
            // 普通更新
            btnUpdate.setOnClickListener { update(false) }
            // 强制更新
            btnUpdateForce.setOnClickListener { update(true) }
            // 自定义弹窗
            btnUpdateCustom.setOnClickListener {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("版本更新")
                    .setMessage(desc)
                    .setNegativeButton("取消") { dialog, _ -> dialog.cancel() }
                    .setPositiveButton("更新") { dialog, _ -> updateMute() }
                    .show()
            }
            // 静默更新
            btnUpdateMute.setOnClickListener {
                updateMute()
            }
            // 取消更新
            btnUpdateCancel.setOnClickListener { manager?.cancel() }
        }
    }

    private fun update(isForce: Boolean) {
        manager = DownloadManager.Builder(this)
            .apkUrl(url)
            .apkName("athena.apk") // 必须以.apk结尾
            .smallIcon(R.mipmap.ic_launcher)
            .apkVersionCode(2) // 设置了此参数，那么内部会自动判断是否需要显示更新对话框，否则需要自己判断是否需要更新
            .apkVersionName("v1.2.2")
            .apkSize("7.7MB")
            .apkDescription(desc) // 更新说明
            .forcedUpgrade(isForce) // 是否强制更新
            .enableLog(true) // debug log
            .jumpInstallPage(true) // 下载完成自动跳转安装页
            .showNotification(true) // 通知栏下载进度
            .showBgdToast(true) // 下载提示吐司
            .showNewerToast(true) // 提示当前已是最新版
            .onDownloadListener(object : OnDownloadListenerAdapter() {
                override fun downloading(max: Int, progress: Int) {
                    bind.progress.run {
                        this.max = max
                        this.progress = progress
                    }
                }
            })
            .onButtonClickListener(object : OnButtonClickListener {
                override fun onButtonClick(id: Int) {
                    when (id) {
                        OnButtonClickListener.UPDATE -> L.vv("点击 UPDATE")
                        OnButtonClickListener.CANCEL -> L.vv("点击 CANCEL")
                    }
                }
            })
            .build()
        manager?.download()
    }

    /**
     * 静默安装
     */
    private fun updateMute() {
        manager = DownloadManager.Builder(this)
            .apkUrl(url)
            .apkName("athena.apk")
            .smallIcon(R.mipmap.ic_launcher)
            .build()
        manager?.download()
    }
}