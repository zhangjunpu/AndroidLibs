@file:JvmName("Md5Helper")

package com.junpu.utils.encrypt

import com.junpu.utils.logStackTrace
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5 Hash
 * @author junpu
 * @date 2019-08-03
 */

/**
 * md5 hash
 */
fun String.toMd5(): String? {
    return try {
        val digestBytes = MessageDigest.getInstance("MD5").run {
            reset()
            update(this@toMd5.toByteArray(StandardCharsets.UTF_8))
            digest()
        }
        StringBuilder().run {
            digestBytes.forEach {
                var hex = Integer.toHexString(it.toInt() and 0xFF)
                if (hex.length == 1) hex = "0$hex"
                append(hex)
            }
            toString()
        }
    } catch (e: NoSuchAlgorithmException) {
        e.logStackTrace()
        null
    }
}