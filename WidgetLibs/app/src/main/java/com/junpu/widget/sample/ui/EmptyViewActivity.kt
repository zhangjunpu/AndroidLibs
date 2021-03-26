package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.junpu.widget.emptyview.EmptyView
import com.junpu.widget.sample.R
import kotlinx.android.synthetic.main.activity_empty_view.*
import com.junpu.toast.toast

/**
 * 空数据页面
 * @author junpu
 * @date 2019-12-26
 */
class EmptyViewActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_view)
        initView()
    }

    private fun initView() {
        loading.setOnClickListener(this)
        empty.setOnClickListener(this)
        error.setOnClickListener(this)
        failure.setOnClickListener(this)
        success.setOnClickListener(this)

        emptyView?.run {
            imageEmptyResource = R.mipmap.ic_nodata_friend
            isRetryEmptyEnable = true
            doOnRetry { toast("哈哈哈哈哈 retry") }
            doOnReLogin { toast("reLogin") }
            reLoginText = "退出，重新登录"
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            loading -> emptyView.setStatus(EmptyView.Status.LOADING.id)
            empty -> emptyView.setStatus(EmptyView.Status.EMPTY.id, "", "发起聊天或视频通话后，显示在这里")
            error -> emptyView.setStatus(EmptyView.Status.ERROR.id, "", "发起聊天或视频通话后，显示在这里")
            failure -> emptyView.setStatus(EmptyView.Status.NO_NETWORK.id)
            success -> emptyView.setStatus(EmptyView.Status.SUCCESS.id)
        }
    }
}
