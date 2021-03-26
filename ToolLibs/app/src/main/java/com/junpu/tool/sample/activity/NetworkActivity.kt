package com.junpu.tool.sample.activity

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.log.L
import com.junpu.tool.sample.R
import com.junpu.tool.sample.appendLine
import com.junpu.utils.*
import kotlinx.android.synthetic.main.activity_network.*

/**
 * NetworkActivity
 * @author junpu
 * @date 2020-01-04
 */
class NetworkActivity : PermissionsActivity() {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        btnNetwork?.setOnClickListener {
            val info = connectivityManager.activeNetworkInfo
            L.e("type = ${info?.type} / ${info?.typeName}")
            L.e("subtype = ${info?.subtype} / ${info?.subtypeName}")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        ?.apply {
                            println("transportCellular = ${hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)}")
                            println("transportWifi = ${hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}")
                            println("transportBluetooth = ${hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)}")
                            println("transportEthernet = ${hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)}")
                            println("transportVpn = ${hasTransport(NetworkCapabilities.TRANSPORT_VPN)}")
                            println("transportWifiAware = ${hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)}")
                            println("transportLowpan = ${hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)}")
                        }
                L.e("linkDownstreamBandwidthKbps = ${capabilities?.linkDownstreamBandwidthKbps}")
                L.e("linkUpstreamBandwidthKbps = ${capabilities?.linkUpstreamBandwidthKbps}")
            }

            println("this.activeNetworkInfo.toString() = ${connectivityManager.activeNetworkInfo}")
        }
        btnWifi?.setOnClickListener {
            val info = wifiManager.connectionInfo
            println("WifiInfo = $info")

            val wifiName = connectivityManager.activeNetworkInfo?.extraInfo.run {
                if (!isNullOrBlank() && startsWith("\"") && endsWith("\"")) {
                    val startIndex = indexOfFirst { it == '"' } + 1
                    val lastIndex = lastIndexOf("\"")
                    subSequence(startIndex, lastIndex)
                }
            }
            println("wifiName = $wifiName")
        }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            initNetworkInfo()
        }
    }

    private fun initNetworkInfo() {
        L.e("isNetworkConnect = $isNetworkConnect")
        L.e("isWifiEnabled = $isWifiEnabled")
        L.e("isEthernetEnabled = $isEthernetEnabled")
        L.e("isMobileEnabled = $isMobileEnabled")
        L.e("wifiName = $wifiName")
        L.e("macAddress = $macAddress")
        L.e("localIpAddress = $localIpAddress")
        L.e("wifiIpAddress = $wifiIpAddress")

        textInfo.appendLine("isNetworkConnect = $isNetworkConnect")
        textInfo.appendLine("isWifiEnabled = $isWifiEnabled")
        textInfo.appendLine("isEthernetEnabled = $isEthernetEnabled")
        textInfo.appendLine("isMobileEnabled = $isMobileEnabled")
        textInfo.append("\n")
        textInfo.appendLine("wifiName = $wifiName")
        textInfo.appendLine("macAddress = $macAddress")
        textInfo.appendLine("localIpAddress = $localIpAddress")
        textInfo.appendLine("wifiIpAddress = $wifiIpAddress")
    }
}