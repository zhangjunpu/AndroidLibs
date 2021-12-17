package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.toast.toast
import com.junpu.widget.emptyview.EmptyView
import com.junpu.widget.sample.R
import com.junpu.widget.sample.databinding.ActivityEmptyViewBinding
import com.junpu.widget.sample.utils.binding

/**
 * 空数据页面
 * @author junpu
 * @date 2019-12-26
 */
class EmptyViewActivity : AppCompatActivity() {

    private val binding by binding<ActivityEmptyViewBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
