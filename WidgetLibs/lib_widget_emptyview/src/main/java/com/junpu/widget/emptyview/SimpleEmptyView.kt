package com.junpu.widget.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.simple_empty_view.view.*

/**
 * 简单的EmptyView
 * @author junpu
 * @date 2020/6/9
 */
class SimpleEmptyView : FrameLayout {

    companion object {
        const val DEFAULT_ERROR = "数据错误"
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        View.inflate(context, R.layout.simple_empty_view, this)
    }

    /**
     * 设置重试事件
     */
    fun doOnRetry(listener: ((View) -> Unit)?) {
        btnRetry?.setOnClickListener(listener)
    }

    /**
     * loading状态
     */
    fun loading() {
        isClickable = true
        visible()
        progress?.visible()
        layoutError?.gone()
    }

    /**
     * 成功状态
     */
    fun success() {
        gone()
    }

    /**
     * 错误状态
     */
    fun error(errorMsg: String? = DEFAULT_ERROR) {
        visible()
        progress?.gone()
        layoutError?.visible()
        errorMessage?.text = errorMsg
    }

    /**
     * 设置View状态
     */
    fun setStatus(status: Int, errorMsg: String? = DEFAULT_ERROR) {
        when (status) {
            Status.LOADING.id -> loading()
            Status.SUCCESS.id -> success()
            Status.ERROR.id -> error(errorMsg)
        }
    }

    /**
     * SimpleEmptyView状态
     */
    enum class Status(val id: Int) {
        LOADING(0),
        SUCCESS(1),
        ERROR(2),
    }
}