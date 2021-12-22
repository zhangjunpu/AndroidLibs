package com.junpu.widget.emptyview.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.toast.toast
import com.junpu.widget.emptyview.SimpleEmptyView
import com.junpu.widget.emptyview.demo.databinding.ActivitySimpleEmptyViewBinding

/**
 *
 * @author junpu
 * @date 2021/12/22
 */
class SimpleEmptyViewActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySimpleEmptyViewBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            loading.setOnClickListener {
                emptyView.setStatus(SimpleEmptyView.Status.LOADING.id)
            }
            error.setOnClickListener {
                emptyView.setStatus(SimpleEmptyView.Status.ERROR.id, "发起聊天或视频通话后，显示在这里")
            }
            success.setOnClickListener {
                emptyView.setStatus(SimpleEmptyView.Status.SUCCESS.id)
            }

            emptyView.run {
                doOnRetry { toast("哈哈哈哈哈 retry") }
            }
        }
    }
}