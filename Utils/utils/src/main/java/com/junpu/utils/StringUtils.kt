package com.junpu.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.Charset
import kotlin.math.ceil

/**
 * String 工具类
 * @author junpu
 * @date 2019-08-10
 */
object StringUtils {

    @Throws(UnsupportedEncodingException::class)
    fun toURLEncoded(str: String?, charsetName: String?): String {
        if (str.isNullOrBlank()) return ""
        val charset = charsetName?.let { Charset.forName(charsetName) } ?: Charset.defaultCharset()
        val encodeStr = String(str.toByteArray(), charset)
        return URLEncoder.encode(encodeStr, charsetName)
    }

    /**
     * 获取字符串的长度，中文字符占1位，英文和数字占半个
     */
    fun chineseLengthHalf(value: String) = chineseLength(value).toFloat() / 2

    /**
     * 获取字符串的长度，中文字符占2位，英文字符和数字占1位
     */
    fun chineseLength(value: String): Int {
        var length = 0
        val chinese = "[\u0391-\uFFE5]"
        value.forEach { length += if (it.toString().matches(chinese.toRegex())) 2 else 1 }
        return length
    }


    /**
     * 将一串数字每三位增加一个","
     */
    fun getSectionStr(count: Int) = String.format("%,d", count)

    /**
     * 获取小数的字符串形态，向上取整
     * 1. 如果为整数，则返回int形态的整数（11.0 -> 11）
     * 2. 如果为小数，则整体向上取整（11.2 -> 12）
     */
    fun getCeilString(count: Float): String {
        // 判断是否为整数
        return if (count * 10 % 10 != 0f) {
            ceil(count.toDouble()).toInt().toString() // 凑整，整体向上取整
        } else {
            count.toInt().toString() // 防止出现.0情况
        }
    }

    /**
     * 首行缩进
     */
    fun getSpace(): String = "\u3000\u3000"

    /**
     * 全角转化半角
     */
    fun toDBC(input: String): String {
        val charArray = input.map {
            when {
                it.toInt() == 12288 -> 32.toChar()
                it.toInt() in 65281..65374 -> (it.toInt() - 65248).toChar()
                else -> it
            }
        }.toCharArray()
        return String(charArray)
    }

    /**
     * 半角转全角
     */
    fun toSBC(input: String): String {
        val charArray = input.map {
            when (it.toInt()) {
                32 -> 12288.toChar()
                in 33..126 -> (it.toInt() + 65248).toChar()
                else -> it
            }
        }.toCharArray()
        return String(charArray)
    }
}