package com.junpu.widget.emptyview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.empty_view.view.*

/**
 * 空数据页面
 * @author zhangjunpu
 * @version 创建时间：2014-5-14 类说明
 */
class EmptyView : FrameLayout {

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

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        View.inflate(context, R.layout.empty_view, this)
        context.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, defStyleRes).run {
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
            when (value) {
                ColorMode.LIGHT.id -> {
                    textMessage?.textColorResource = R.color.empty_text
                    textSubMessage?.textColorResource = R.color.empty_text_sub
                    btnRetry?.run {
                        textColor = Color.WHITE
                        backgroundResource = R.drawable.nodata_retry_bg
                    }
                }
                ColorMode.DARK.id -> {
                    textMessage?.textColorResource = R.color.empty_text_dark
                    textSubMessage?.textColorResource = R.color.empty_text_sub_dark
                    btnRetry?.run {
                        textColorResource = R.color.empty_text_sub_dark
                        backgroundResource = R.drawable.nodata_retry_bg_black
                    }
                }
            }
        }

    var messageColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(color) {
            textMessage?.textColor = color
        }

    var subMessageColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(color) {
            textSubMessage?.textColor = color
        }

    var messageColorResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            textMessage?.textColorResource = colorId
        }

    var subMessageColorResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            textSubMessage?.textColorResource = colorId
        }

    var retryText: CharSequence?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnRetry?.text = value
        }

    var reLoginText: CharSequence?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnReLogin?.text = value
        }

    var retryTextResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(stringId) {
            btnRetry?.textResource = stringId
        }

    var reLoginTextResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(stringId) {
            btnReLogin?.textResource = stringId
        }

    var retryTextColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnRetry?.textColor = value
        }

    var reLoginTextColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnReLogin?.textColor = value
        }

    var retryTextColorRecource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            btnRetry?.textColorResource = colorId
        }

    var reLoginTextColorRecource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colorId) {
            btnReLogin?.textColorResource = colorId
        }

    var retryTextColorStateList: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colors) {
            btnRetry?.setTextColor(colors)
        }

    var reLoginTextColorStateList: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(colors) {
            btnReLogin?.setTextColor(colors)
        }

    var retryBaskground: Drawable
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnRetry?.background = value
        }

    var reLoginBaskground: Drawable
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnReLogin?.background = value
        }

    var retryBaskgroundResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnRetry?.backgroundResource = value
        }

    var reLoginBaskgroundResource: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            btnReLogin?.backgroundResource = value
        }

    fun doOnRetry(retryListener: ((View) -> Unit)?) {
        btnRetry?.setOnClickListener(retryListener)
    }

    fun doOnReLogin(listener: ((View) -> Unit)?) {
        btnReLogin?.run {
            setVisibility(listener != null)
            setOnClickListener(listener)
        }
    }

    fun setMessage(msg: CharSequence?, defaultMsg: CharSequence? = null) {
        val message = msg ?: defaultMsg
        textMessage?.run {
            setVisibility(!message.isNullOrBlank())
            text = message
        }
    }

    fun setSubMessage(subMsg: CharSequence?, defaultSubMsg: CharSequence? = null) {
        val message = subMsg ?: defaultSubMsg
        textSubMessage?.run {
            setVisibility(!message.isNullOrBlank())
            this.text = message
        }
    }

    /**
     * [Status.LOADING], [Status.RELOADING]状态下的页面
     */
    private fun loading() {
        isClickable = false
        progress?.visible()
        layoutMessage?.gone()
    }

    /**
     * 设置[Status.EMPTY], [Status.ERROR], [Status.NO_NETWORK]三个状态下的页面展示
     */
    private fun setEmptyErrorStatus(status: Int, msg: CharSequence? = null, subMsg: CharSequence? = null) {
        isClickable = true
        progress?.gone()
        layoutMessage?.visible()
        setMessage(msg, getDefaultMessage(status))
        setSubMessage(subMsg, getDefaultSubMessage(status))
        when (status) {
            Status.EMPTY.id -> {
                btnRetry?.setVisibility(isRetryEmptyEnable)
                imageView?.run {
                    imageResource = if (imageEmptyResource > 0) imageEmptyResource else R.mipmap.ic_nodata_empty
                    setVisibility(isImageEmptyEnable)
                }
            }
            Status.ERROR.id -> {
                btnRetry?.setVisibility(isRetryErrorEnable)
                imageView?.run {
                    imageResource = if (imageErrorResource > 0) imageErrorResource else R.mipmap.ic_nodata_error
                    setVisibility(isImageErrorEnable)
                }
            }
            Status.NO_NETWORK.id -> {
                btnRetry?.setVisibility(isRetryNoNetworkEnable)
                imageView?.run {
                    imageResource = if (imageNoNetworkResource > 0) imageNoNetworkResource else R.mipmap.ic_nodata_network
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