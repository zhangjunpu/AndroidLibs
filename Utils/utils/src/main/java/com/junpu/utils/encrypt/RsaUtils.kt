package com.junpu.utils.encrypt

import android.util.Base64
import com.junpu.utils.logStackTrace
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * RSA加密工具类
 * @author junpu
 * @date 2019-08-14
 */
object RsaUtils {

    private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"

    /**
     * 公钥加密
     */
    fun encrypt(content: String, publicKey: String): String? {
        try {
            val encodedKey = Base64.decode(publicKey, Base64.DEFAULT)
            val keySpec = X509EncodedKeySpec(encodedKey)
            val key = KeyFactory.getInstance("RSA").generatePublic(keySpec)
            val cipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.ENCRYPT_MODE, key)
            }
            val output = cipher.doFinal(content.toByteArray())
            return Base64.encodeToString(output, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.logStackTrace()
        }
        return null
    }
}