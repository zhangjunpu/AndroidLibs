package com.junpu.fragment.tab.demo.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junpu.fragment.tab.demo.databinding.FragmentDeviceBinding
import com.junpu.log.L
import com.junpu.utils.appendLine
import java.util.*

/**
 * 设备信息
 * @author junpu
 * @date 2020/4/13
 */
class DeviceInfoFragment : Fragment() {

    private var binding: FragmentDeviceBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDeviceBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "系统语言：${Locale.getDefault().language}\n" +
                "系统版本：${Build.VERSION.RELEASE}\n" +
                "系统型号：${Build.MODEL}\n" +
                "设备品牌：${Build.BRAND}\n" +
                "CPU架构信息：${Build.CPU_ABI}\n" +
                "硬件信息：${Build.HARDWARE}"
        L.d(text)
        binding?.textInfo?.text = text
        binding?.textInfo?.appendLine("")
        binding?.textInfo?.appendLine("")
        binding?.textInfo?.appendLine("density: ${resources.displayMetrics.density}")
        binding?.textInfo?.appendLine("scaledDensity: ${resources.displayMetrics.scaledDensity}")
        binding?.textInfo?.appendLine("")
        binding?.textInfo?.appendLine("")

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
        binding?.textInfo?.appendLine(info)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}