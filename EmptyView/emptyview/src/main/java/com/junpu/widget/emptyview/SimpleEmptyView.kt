package com.junpu.widget.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.junpu.widget.emptyview.databinding.SimpleEmptyViewBinding

/**
 * 简单的EmptyView
 * @author junpu
 * @date 2020/6/9
 */
class SimpleEmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_ERROR = "数据错误"
    }

    private val binding = SimpleEmptyViewBinding.inflate(layoutInflater, this)

    /**
     * 设置重试事件
     */
    fun doOnRetry(listener: ((View) -> Unit)?) {
        binding.btnRetry.setOnClickListener(listener)
    }

    /**
     * loading状态
     */
    fun loading() {
        isClickable = true
        visible()
        binding.progress.visible()
        binding.layoutError.gone()
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
        binding.progress.gone()
        binding.layoutError.visible()
        binding.errorMessage.text = errorMsg
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