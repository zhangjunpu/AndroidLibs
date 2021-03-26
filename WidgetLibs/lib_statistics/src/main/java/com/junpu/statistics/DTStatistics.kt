package com.junpu.statistics

import android.content.Context
import com.bftv.fui.analytics.FAConstant
import com.bftv.fui.analytics.actions.FAnalytics
import com.bftv.fui.analytics.actions.FClickAction
import com.bftv.fui.analytics.actions.FPageAction


/**
 * DT统计
 *
 * @author chengxiaobo
 * @time 2018/4/10 16:10
 */
class DTStatistics(private val userType: FAConstant.UserType, private val appName: String, private val sn: String = "", private val userId: String = "", private val debug: Boolean = false) : StatisticsInterface {

    override fun init(context: Context) {

        FAnalytics.init(
                context, // Context
                sn, // 设备SN
                userId, // 用户ID
                userType, // 用户类型
                appName, // App名字
                true, // 是否上传埋点
                debug          // 是否输出日志
        )
    }

    override fun updateUser(userType: FAConstant.UserType, userId: String) {
        FAnalytics.setUserId(userId)
        FAnalytics.setUserType(userType)
    }

    override fun faceTimeDail() {
        page("facetime_call_out")
    }

    override fun faceTimeCancel() {
        click("facetime_cancel")
    }

    override fun faceTimeTimeOut20s() {

    }

    override fun faceTimeTimeOut60s() {

    }

    override fun faceTimeTimeBusy() {
        page("facetime_call_connected")
    }

    override fun faceTimeHangUpFrom() {
        click("facetime_stop")
    }

    override fun faceTimeAccepted() {

    }

    override fun faceTimeAnswerPage() {
        page("facetime_call_in")
    }

    override fun faceTimeAnswerButton() {
        click("facetime_accept")
    }

    override fun faceTimeRejcet() {
        click("facetime_reject")
    }

    override fun faceTimehandUpTo() {
        click("facetime_stop")
    }

    override fun faceTimeAddressBook() {
        page("facetime_addressbook")
    }

    override fun faceTimeRecords() {
        page("facetime_records")
    }

    override fun faceTimeMutedVideo() {
        click("facetime_muted_video")
    }

    override fun faceTimeMutedAudio() {
        click("facetime_muted_audio")
    }

    /**
     * 点击按钮
     */
    private fun click(action: String) {
        FClickAction.createFunctionClick(
                action,
                "",
                FAConstant.ClickMode.OPEN,
                "center",
                "expandField"
        ).upload()
    }

    /**
     * 打开什么页面
     */
    private fun page(toPage: String) {

        FPageAction
                .createNormal(
                        "",
                        toPage,
                        ""
                ).upload()
    }
}