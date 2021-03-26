package com.junpu.utils

/**
 * 正则校验
 * @author jamie
 * @date 2019-08-14
 */
object RegularUtils {
    /**
     * 密码校验
     */
    fun passWordRegulation(text: String): Boolean {
        val regularPassWord =
            Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)(?![._*]+\$)([0-9A-Za-z]|[._*]){6,16}\$")
        return regularPassWord.matches(text)
    }
}