package com.junpu.utils.encrypt

import android.util.Base64
import com.junpu.utils.logStackTrace
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES加密解密
 * @author junpu
 * @date 2019-09-05
 */
object AesUtils {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val IV_PARAMETER = "2017030920181231"

    var defaultSecretKey = "homeowrkdone201903300900"

    /**
     * AES加密
     * [text] 要加密的字符串
     * [secretKey] key，24位字符串
     */
    fun encrypt(text: String?, secretKey: String = defaultSecretKey): String? {
        try {
            val cipher = getCipher(true, secretKey)
            val result = cipher.doFinal(text?.toByteArray())
            return Base64.encodeToString(result, Base64.DEFAULT)
        } catch (e: Exception) {
            e.logStackTrace()
        }
        return null
    }

    /**
     * AES解密
     * [text] 要解密的字符串
     * [secretKey] key，24位字符串
     */
    fun decrypt(text: String?, secretKey: String = defaultSecretKey): String? {
        try {
            val content = Base64.decode(text, Base64.DEFAULT)
            val cipher = getCipher(false, secretKey)
            val result = cipher.doFinal(content)
            return String(result, Charsets.UTF_8)
        } catch (e: Exception) {
            e.logStackTrace()
        }
        return null
    }

    /**
     * 获取密钥
     */
    private fun getCipher(isEncrypt: Boolean, secretKey: String): Cipher {
        val mode = if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE // 模式
        val key = SecretKeySpec(secretKey.toByteArray(), ALGORITHM) // 密钥
        val iv = IvParameterSpec(IV_PARAMETER.toByteArray()) // 偏移量
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(mode, key, iv)
        }
    }

}