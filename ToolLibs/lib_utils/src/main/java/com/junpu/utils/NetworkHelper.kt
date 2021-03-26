@file:JvmName("NetworkHelper")

package com.junpu.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException


/**
 * 网络工具类
 * @author junpu
 * @date 2019-12-06
 */

inline val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

inline val Context.wifiManager: WifiManager
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

/**
 * 检查当前网络是否可用
 */
val Context.isNetworkConnect
    get() = try {
        connectivityManager.activeNetworkInfo?.isConnected ?: false
    } catch (e: Exception) {
        e.logStackTrace()
        false
    }

/**
 * 检查wifi网络是否可用
 */
val Context.isWifiEnabled
    get() = connectivityManager.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                ?: false
        } else {
            activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
    }

/**
 * 检查移动网络是否可用
 */
val Context.isMobileEnabled
    get() = connectivityManager.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                ?: false
        } else {
            activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE
        }
    }

/**
 * 检查以太网络是否可用
 */
val Context.isEthernetEnabled
    get() = connectivityManager.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                ?: false
        } else {
            activeNetworkInfo?.type == ConnectivityManager.TYPE_ETHERNET
        }
    }

/**
 * 获取当前wifi名称
 * android9.0以上需要申请定位权限
 * android10.0需要申请新添加的隐私权限ACCESS_FINE_LOCATION
 * 如果还需要后台获取或者使用wifi api则还需要申请后台使用定位权限ACCESS_BACKGROUND_LOCATION
 */
val Context.wifiName
    get() = if (isWifiEnabled) wifiManager.connectionInfo?.ssid?.let {
        if (it.startsWith("\"") && it.endsWith("\"")) it.substring(1, it.lastIndex) else it
    } else null

/**
 * 获取网络的ip地址
 */
val localIpAddress: String?
    get() {
        try {
            NetworkInterface.getNetworkInterfaces().iterator().run {
                while (hasNext()) {
                    next().inetAddresses.iterator().let {
                        while (it.hasNext()) {
                            val inetAddress = it.next()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.logStackTrace()
        }
        return null
    }

/**
 * 获取WIFI的IP地址
 */
val Context.wifiIpAddress: String?
    get() = wifiManager.run {
        if (isWifiEnabled) {
            connectionInfo?.let {
                val ipAddress = it.ipAddress
                val ip1 = ipAddress and 0xFF
                val ip2 = ipAddress shr 8 and 0xFF
                val ip3 = ipAddress shr 16 and 0xFF
                val ip4 = ipAddress shr 24 and 0xFF
                "$ip1.$ip2.$ip3.$ip4"
            }
        } else null
    }

/**
 * 获取本机的mac地址
 */
val Context.macAddress
    get() = wifiManager.connectionInfo?.bssid

/**
 * 打印堆栈错误信息
 */
private fun Throwable.logStackTrace() = Log.e("System.err", Log.getStackTraceString(this))