package com.junpu.widget.emptyview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.junpu.widget.emptyview.databinding.EmptyViewBinding

/**
 * 空数据页面
 * @author zhangjunpu
 * @version 创建时间：2014/5/14 类说明；
 * 更新时间：2021/12/22；
 */
class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        // 默认文案
        const val DEFAULT_MSG_EMPTY = "暂无数据"
        const val DEFAULT_MSG_ERROR = "数据异常"
        const val DEFAULT_MSG_NO_NETWORK = "网络异常"
        const val DEFAULT_SUB_MSG_NO_NETWORK = "网络连接异常，请检查您的网络状态"

        fun isError(code: Int) = code == Status.ERROR.id || code == Status.NO_NETWORK.id
        fun isEmpty(code: Int) = code == Status.EMPTY.id
        fun isSuccess(code: Int) = code == Status.SUCCESS.id
        fun isLoading(code: Int) = code == Status.LOADING.id
        fun isReloading(code: Int) = code == Status.RELOADING.id

        /**
         * 获取默认文案
         */
        fun getDefaultMessage(status: Int) = when (status) {
            Status.EMPTY.id -> DEFAULT_MSG_EMPTY
            Status.ERROR.id -> DEFAULT_MSG_ERROR
            Status.NO_NETWORK.id -> DEFAULT_MSG_NO_NETWORK
            else -> null
        }

        /**
         * 获取默认子文案
         */
        fun getDefaultSubMessage(status: Int) = when (status) {
            Status.NO_NETWORK.id -> DEFAULT_SUB_MSG_NO_NETWORK
            else -> null
        }
    }

    private val binding = EmptyViewBinding.inflate(layoutInflater, this)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0).run {
            colorMode = getInt(R.styleable.EmptyView_colorMode, ColorMode.LIGHT.id)
            recycle()
        }
    }

    // 各状态下的重试按钮是否可用
    var isRetryEmptyEnable = false
    var isRetryErrorEnable = true
    var isRetryNoNetworkEnable = true

    // 各状态下的图片是否显示
    var isImageEmptyEnable = true
    var isImageErrorEnable = true
    var isImageNoNetworkEnable = true

    // 各状态下的图片状态
    var imageEmptyResource = 0
    var imageErrorResource = 0
    var imageNoNetworkResource = 0

    // 状态
    var status = Status.LOADING.id
        private set

    /**
     * 颜色模式
     */
    var colorMode: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.run {
                when (value) {
                    ColorMode.LIGHT.id -> {
                        textMessage.textColorResource = R.color.empty_text
                        textSubMessage.textColorResource = R.color.empty_text_sub
                        btnRetry.run {
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.nodata_retry_bg
                        }
                    }
                    ColorMode.DARK.id -> {
                        textMessage.textColorResource = R.color.empty_text_dark
                        textSubMessage.textColorResource = R.color.empty_text_sub_dark
                        btnRetry.run {
                            textColorResource = R.color.empty_text_sub_dark
                            backgroundResource = R.drawable.nodata_retry_bg_black
                        }
                    }
                }
            }
        }

    var messageColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(color) {
            binding.textMessage.textColor = color
        }

    var subMessageColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(color) {
            binding.textSubMessage.textColor = color
        }

    var messageColorResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            binding.textMessage.textColorResource = colorId
        }

    var subMessageColorResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            binding.textSubMessage.textColorResource = colorId
        }

    var retryText: CharSequence?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnRetry.text = value
        }

    var reLoginText: CharSequence?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnReLogin.text = value
        }

    var retryTextResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(stringId) {
            binding.btnRetry.textResource = stringId
        }

    var reLoginTextResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(stringId) {
            binding.btnReLogin.textResource = stringId
        }

    var retryTextColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnRetry.textColor = value
        }

    var reLoginTextColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnReLogin.textColor = value
        }

    var retryTextColorRecource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            binding.btnRetry.textColorResource = colorId
        }

    var reLoginTextColorRecource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            binding.btnReLogin.textColorResource = colorId
        }

    var retryTextColorStateList: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colors) {
            binding.btnRetry.setTextColor(colors)
        }

    var reLoginTextColorStateList: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colors) {
            binding.btnReLogin.setTextColor(colors)
        }

    var retryBackground: Drawable
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnRetry.background = value
        }

    var reLoginBackground: Drawable
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnReLogin.background = value
        }

    var retryBackgroundResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnRetry.backgroundResource = value
        }

    var reLoginBackgroundResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            binding.btnReLogin.backgroundResource = value
        }

    fun doOnRetry(retryListener: ((View) -> Unit)?) {
        binding.btnRetry.setOnClickListener(retryListener)
    }

    fun doOnReLogin(listener: ((View) -> Unit)?) {
        binding.btnReLogin.run {
            setVisibility(listener != null)
            setOnClickListener(listener)
        }
    }

    fun setMessage(msg: CharSequence?, defaultMsg: CharSequence? = null) {
        val message = msg ?: defaultMsg
        binding.textMessage.run {
            setVisibility(!message.isNullOrBlank())
            text = message
        }
    }

    fun setSubMessage(subMsg: CharSequence?, defaultSubMsg: CharSequence? = null) {
        val message = subMsg ?: defaultSubMsg
        binding.textSubMessage.run {
            setVisibility(!message.isNullOrBlank())
            this.text = message
        }
    }

    /**
     * [Status.LOADING], [Status.RELOADING]状态下的页面
     */
    private fun loading() {
        isClickable = false
        binding.progress.visible()
        binding.layoutMessage.gone()
    }

    /**
     * 设置[Status.EMPTY], [Status.ERROR], [Status.NO_NETWORK]三个状态下的页面展示
     */
    private fun setEmptyErrorStatus(
        status: Int,
        msg: CharSequence? = null,
        subMsg: CharSequence? = null
    ) {
        isClickable = true
        binding.progress.gone()
        binding.layoutMessage.visible()
        setMessage(msg, getDefaultMessage(status))
        setSubMessage(subMsg, getDefaultSubMessage(status))
        when (status) {
            Status.EMPTY.id -> {
                binding.btnRetry.setVisibility(isRetryEmptyEnable)
                binding.imageView.run {
                    imageResource =
                        if (imageEmptyResource > 0) imageEmptyResource else R.mipmap.ic_nodata_empty
                    setVisibility(isImageEmptyEnable)
                }
            }
            Status.ERROR.id -> {
                binding.btnRetry.setVisibility(isRetryErrorEnable)
                binding.imageView.run {
                    imageResource =
                        if (imageErrorResource > 0) imageErrorResource else R.mipmap.ic_nodata_error
                    setVisibility(isImageErrorEnable)
                }
            }
            Status.NO_NETWORK.id -> {
                binding.btnRetry.setVisibility(isRetryNoNetworkEnable)
                binding.imageView.run {
                    imageResource =
                        if (imageNoNetworkResource > 0) imageNoNetworkResource else R.mipmap.ic_nodata_network
                    setVisibility(isImageNoNetworkEnable)
                }
            }
        }
    }

    /**
     * 设置EmptyView各种状态
     */
    fun setStatus(status: Int, msg: String? = null, subMsg: String? = null) {
        this.status = status
        when (status) {
            Status.LOADING.id -> loading()
            Status.EMPTY.id, Status.ERROR.id, Status.NO_NETWORK.id -> {
                visible()
                setEmptyErrorStatus(status, msg, subMsg)
            }
            Status.SUCCESS.id -> gone()
            Status.RELOADING.id -> {
                visible()
                loading()
            }
        }
    }

    /**
     * 设置EmptyView各种状态
     */
    fun setStatus(status: Status, msg: String? = null, subMsg: String? = null) {
        this.status = status.id
        when (status) {
            Status.LOADING -> loading()
            Status.RELOADING -> {
                visible()
                loading()
            }
            Status.EMPTY, Status.ERROR, Status.NO_NETWORK -> {
                visible()
                setEmptyErrorStatus(status.id, msg, subMsg)
            }
            Status.SUCCESS -> gone()
        }
    }

    /**
     * 颜色模式
     */
    enum class ColorMode(val id: Int) {
        LIGHT(0), // 浅色模式
        DARK(1) // 深色模式
    }

    /**
     * EmptyView状态
     */
    enum class Status(val id: Int) {
        LOADING(0),
        EMPTY(1),
        ERROR(2),
        NO_NETWORK(3),
        SUCCESS(4),
        RELOADING(5)
    }
}