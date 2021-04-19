package com.junpu.utils

/**
 * 正则校验
 * @author jamie
 * @date 2019-08-14
 */

/**
 * 密码校验
 */
fun passwordRegex(text: String): Boolean {
    val pattern = "^(?![0-9]+\$)(?![a-zA-Z]+\$)(?![._*]+\$)([0-9A-Za-z]|[._*]){6,16}\$"
    return Regex(pattern).matches(text)
}