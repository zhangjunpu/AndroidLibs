package com.junpu.tool.sample.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.log.L
import com.junpu.tool.sample.databinding.ActivityDeviceInfoBinding
import com.junpu.utils.appendLine
import java.util.*

/**
 * 设备信息
 * @author junpu
 * @date 2020/4/13
 */
class DeviceInfoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDeviceInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "设备信息"

        val text = "系统语言：${Locale.getDefault().language}\n" +
                "系统版本：${Build.VERSION.RELEASE}\n" +
                "系统型号：${Build.MODEL}\n" +
                "设备品牌：${Build.BRAND}\n" +
                "CPU架构信息：${Build.CPU_ABI}\n" +
                "硬件信息：${Build.HARDWARE}"
        L.d(text)
        binding.textInfo.text = text
        binding.textInfo.appendLine("")
        binding.textInfo.appendLine("")
        binding.textInfo.appendLine("density: ${resources.displayMetrics.density}")
        binding.textInfo.appendLine("scaledDensity: ${resources.displayMetrics.scaledDensity}")
        binding.textInfo.appendLine("")
        binding.textInfo.appendLine("")

        val info = "主板 BOARD：${Build.BOARD}\n" +
                "设备引导程序版本号 BOOTLOADER：${Build.BOOTLOADER}\n" +
                "设备品牌 BRAND：${Build.BRAND}\n" +
                "CPU指令集 CPU_ABI：${Build.CPU_ABI}\n" +
                "CPU指令集2 CPU_ABI2：${Build.CPU_ABI2}\n" +
                "设备参数 DEVICE：${Build.DEVICE}\n" +
                "显示屏参数 DISPLAY：${Build.DISPLAY}\n" +
                "唯一编号 FINGERPRINT：${Build.FINGERPRINT}\n" +
                "硬件信息 HARDWARE：${Build.HARDWARE}\n" +
                "Host值 HOST：${Build.HOST}\n" +
                "修订版本列表 ID：${Build.ID}\n" +
                "硬件序列号 SERIAL：${Build.SERIAL}\n" +
                "硬件制造商 MANUFACTURER：${Build.MANUFACTURER}\n" +
                "系统型号 MODEL：${Build.MODEL}\n" +
                "手机产品名 PRODUCT：${Build.PRODUCT}\n" +
                "RADIO：${Build.RADIO}\n" +
                "描述Build的标签 TAGS：${Build.TAGS}\n" +
                "编译时间 TIME：${Build.TIME}\n" +
                "Builder类型 TYPE：${Build.TYPE}\n" +
                "UNKNOWN：${Build.UNKNOWN}\n" +
                "User名 USER：${Build.USER}\n" +
                "当前开发代码 VERSION.CODENAME：${Build.VERSION.CODENAME}\n" +
                "源码控制版本号 VERSION.INCREMENTAL：${Build.VERSION.INCREMENTAL}\n" +
                "版本字符串 VERSION.RELEASE：${Build.VERSION.RELEASE}\n" +
                "版本号 VERSION.SDK：${Build.VERSION.SDK}\n" +
                "版本号 VERSION.SDK_INT：${Build.VERSION.SDK_INT}\n"

        L.d(info)
        binding.textInfo.appendLine(info)
    }
}