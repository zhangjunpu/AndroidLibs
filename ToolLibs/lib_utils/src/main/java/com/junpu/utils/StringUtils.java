package com.junpu.utils;

/**
 * @author zhangjunpu
 * @date 2017/3/13
 */

public class StringUtils {

    /**
     * 获取字符串的长度，中文字符为1位，英文和数字为半个
     *
     * @param value
     * @return
     */
    public static float getLength(String value) {
        return (float) chineseLength(value) / 2;
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 获取长度为
     *
     * @param value
     * @param maxLength
     * @return
     */
    public static String chineseSub(String value, int maxLength) {
        int valueLength = 0;
        String subStr = null;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                if (valueLength == maxLength - 1) {
                    subStr = value.substring(0, i);
                }
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
            if (valueLength == maxLength) {
                subStr = value.substring(0, i + 1);
            }
        }
        if (valueLength < maxLength) {
            return value;
        } else {
            return subStr;
        }
    }

}
