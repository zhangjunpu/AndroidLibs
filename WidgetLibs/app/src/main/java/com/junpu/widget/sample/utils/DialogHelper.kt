package com.junpu.widget.sample.utils

import android.content.Context
import android.view.View
import com.junpu.widget.dialog.LoadingDialog
import com.junpu.widget.dialog.ToastDialog

/**
 * 快速生成对话框
 * @author zhangjunpu
 * @date 2017/2/10
 */
object DialogHelper {

    /**
     * 显示Loading对话框
     */
    fun showLoadingDialog(context: Context?, msg: String? = null): LoadingDialog? {
        context ?: return null
        return LoadingDialog.Builder(context)
            .setMessage(msg)
            .show()
    }

    /**
     * 显示提示对话框
     */
    fun showToastDialog(
        context: Context,
        message: String,
        cancelCallback: ((ToastDialog, View) -> Unit)? = null,
        submitCallback: ((ToastDialog, View) -> Unit)? = null,
    ): ToastDialog {
        return ToastDialog.Builder(context)
            .setMessage(message)
            .doOnSubmit(submitCallback)
            .doOnCancel(cancelCallback)
            .show()
    }
}