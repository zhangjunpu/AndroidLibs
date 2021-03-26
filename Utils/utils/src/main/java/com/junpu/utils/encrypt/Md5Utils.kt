package com.junpu.utils.encrypt

import com.junpu.utils.logStackTrace
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5加密
 * @author junpu
 * @date 2019-08-03
 */
object Md5Utils {

    /**
     * 加密
     */
    fun encrypt(text: String): String? {
        return try {
            val digest = MessageDigest.getInstance("MD5")
            digest.reset()
            digest.update(text.toByteArray(StandardCharsets.UTF_8))
            val encryptBytes = digest.digest()
            val sb = StringBuilder()
            for (b in encryptBytes) {
                var hex = Integer.toHexString(b.toInt() and 0xFF)
                if (hex.length == 1) hex = "0$hex"
                sb.append(hex)
            }
            sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.logStackTrace()
            null
        }
    }
}
