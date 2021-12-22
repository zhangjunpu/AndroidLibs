package com.junpu.widget.emptyview.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.toast.toast
import com.junpu.widget.emptyview.EmptyView
import com.junpu.widget.emptyview.demo.R
import com.junpu.widget.emptyview.demo.databinding.ActivityEmptyViewBinding

/**
 *
 * @author junpu
 * @date 2021/12/22
 */
class EmptyViewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEmptyViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            loading.setOnClickListener {
                emptyView.setStatus(EmptyView.Status.LOADING.id)
            }
            empty.setOnClickListener {
                emptyView.setStatus(EmptyView.Status.EMPTY.id, "", "发起聊天或视频通话后，显示在这里")
            }
            error.setOnClickListener {
                emptyView.setStatus(EmptyView.Status.ERROR.id, "", "发起聊天或视频通话后，显示在这里")
            }
            failure.setOnClickListener {
                emptyView.setStatus(EmptyView.Status.NO_NETWORK.id)
            }
            success.setOnClickListener {
                emptyView.setStatus(EmptyView.Status.SUCCESS.id)
            }

            emptyView.run {
                imageEmptyResource = R.mipmap.ic_nodata_friend
                isRetryEmptyEnable = true
                doOnRetry { toast("哈哈哈哈哈 retry") }
                doOnReLogin { toast("reLogin") }
                reLoginText = "退出，重新登录"
            }
        }
    }
}