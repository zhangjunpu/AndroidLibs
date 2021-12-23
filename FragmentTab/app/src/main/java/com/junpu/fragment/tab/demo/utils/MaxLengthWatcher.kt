package com.junpu.fragment.tab.demo.utils

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.widget.EditText

/**
 * 中文1个字符、英文半个字符的EditText字数限制类
 *
 * @author zhangjunpu
 * @date 2017/3/19
 */
class MaxLengthWatcher(
    private var editText: EditText,
    private var maxLength: Float,
    vararg filters: InputFilter
) : TextWatcher {

    private var isNeedRefresh = false
    private var beforeText: String? = null
    private var result: String? = null
    private var selection = 0
    private val filters = mutableListOf<InputFilter>()
    private var textChangeCallback: ((Editable?) -> Unit)? = null

    init {
        this.filters.addAll(filters)
    }

    fun doOnTextChange(callback: ((Editable?) -> Unit)?) {
        textChangeCallback = callback
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        beforeText = s.toString()
        isNeedRefresh = false
    }

    /**
     * @param s      变化后的字符
     * @param start  变化开始的位置
     * @param before 被删除的字符长度
     * @param count  增加的字符长度
     */
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val content = s.toString()
        val length = content.chineseLengthHalf
        if (count - before >= 0 && length > maxLength) {
            val changeStr = content.substring(start, start + count)
            val deleteStr = beforeText!!.substring(start, start + before)
            result = changeString(beforeText!!, deleteStr, changeStr, maxLength, start)
            selection = start + result!!.length - beforeText!!.length + deleteStr.length
            isNeedRefresh = true
        }
    }

    override fun afterTextChanged(s: Editable) {
        val content = s.toString()
        val halfLength = content.chineseLengthHalf
        val length = s.length
        val inputFilter =
            if (halfLength > maxLength) LengthFilter(length) else LengthFilter(Int.MAX_VALUE)
        editText.filters = getInputFilters(inputFilter)
        textChangeCallback?.invoke(s)
        if (isNeedRefresh) {
            isNeedRefresh = false
            editText.setText(result)
            if (selection > editText.text.length) selection = editText.text.length
            editText.setSelection(selection)
        }
    }

    private fun getInputFilters(filter: InputFilter): Array<InputFilter> = Array(filters.size + 1) {
        if (it in filters.indices) filters[it] else filter
    }

    private fun changeString(
        beforeStr: String,
        deleteStr: String,
        changeStr: String,
        maxLength: Float,
        start: Int
    ): String {
        val beforeLength = beforeStr.chineseLengthHalf
        val deleteLength = deleteStr.chineseLengthHalf
        val changeLength = changeStr.chineseLengthHalf
        return if (beforeLength - deleteLength + changeLength <= maxLength) {
            StringBuilder(beforeStr).run {
                delete(start, start + deleteStr.length)
                insert(start, changeStr)
                toString()
            }
        } else {
            val str = changeStr.substring(0, changeStr.length - 1)
            changeString(beforeStr, deleteStr, str, maxLength, start)
        }
    }


    /**
     * 获取字符串的长度，中文字符为1位，其他为半个
     */
    val String.chineseLengthHalf: Float
        get() = chineseLength / 2f

    /**
     * 获取字符串的长度，每个中文字符长度为2，否则为1
     */
    val String.chineseLength: Int
        get() {
            var size = 0
            val regex = Regex("[\u0391-\uFFE5]")
            forEach {
                size += if (regex.matches(it.toString())) 2 else 1
            }
            return size
        }

    /**
     * 获取长度为，每个中文字符长度为2，否则为1
     */
    fun String.chineseSub(maxLength: Int): String? {
        var size = 0
        var subStr: String? = null
        val regex = Regex("[\u0391-\uFFE5]")
        for (i in this.indices) {
            if (regex.matches(get(i).toString())) {
                if (size == maxLength - 1) subStr = substring(0, i)
                size += 2
            } else {
                size += 1
            }
            if (size == maxLength) {
                subStr = substring(0, i + 1)
            }
        }
        return if (size < maxLength) this else subStr
    }
}