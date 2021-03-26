package com.junpu.statistics

import android.content.Context
import com.bftv.fui.analytics.FAConstant
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


/**
 * 友盟统计
 *
 * @author chengxiaobo
 * @time 2018/4/10 16:10
 */
class UMStatistics : StatisticsInterface {

    companion object {

        //发起方上报
        const val UM_FACETIME_DAIL = "UM_FACETIME_DAIL" // 发起端_拨打电话界面
        const val UM_FACETIME_CANCEL = "UM_FACETIME_CANCEL" // 发起端_取消拨打按钮
        const val UM_FACETIME_TIME_OUT_20s = "UM_FACETIME_TIME_OUT_20s" //发起端_拨打电话界面20s超时
        const val UM_FACETIME_TIME_OUT_60s = "UM_FACETIME_TIME_OUT_60s"//发起端_拨打电话界面60s超时
        const val UM_FACETIME_BUSY = "UM_FACETIME_BUSY"//发起端_用户占线
        const val UM_FACETIME_HANG_UP_FROM = "UM_FACETIME_HANG_UP_FROM" //  发起端_挂断电话按钮
        const val UM_FACETIME_ACCEPTED = "UM_FACETIME_ACCEPTED"//发起端_被接听

        //接收端上报：
        const val UM_FACETIME_ANSWER_PAGE = "UM_FACETIME_ANSWER_PAGE" // 接收端_接听电话界面
        const val UM_FACETIME_ANSWER = "UM_FACETIME_ANSWER" // 接收端_接听电话按钮
        const val UM_FACETIME_REJECT = "UM_FACETIME_REJECT" //  接收端_拒接电话按钮
        const val UM_FACETIME_HANG_UP_TO = "UM_FACETIME_HANG_UP_TO" //  接收端_挂断电话按钮
    }

    private var context: Context? = null

    override fun init(context: Context) {

        this.context = context
        UMConfigure.init(context.applicationContext, UMConfigure.DEVICE_TYPE_PHONE, null)
        MobclickAgent.setScenarioType(context.applicationContext, MobclickAgent.EScenarioType.E_DUM_NORMAL)
    }

    override fun updateUser(userType: FAConstant.UserType, userId: String) {
    }

    override fun faceTimeDail() {

        MobclickAgent.onEvent(context, UM_FACETIME_DAIL)
    }

    override fun faceTimeCancel() {

        MobclickAgent.onEvent(context, UM_FACETIME_CANCEL)
    }

    override fun faceTimeTimeOut20s() {

        MobclickAgent.onEvent(context, UM_FACETIME_TIME_OUT_20s)
    }

    override fun faceTimeTimeOut60s() {

        MobclickAgent.onEvent(context, UM_FACETIME_TIME_OUT_60s)
    }

    override fun faceTimeTimeBusy() {

        MobclickAgent.onEvent(context, UM_FACETIME_BUSY)
    }

    override fun faceTimeHangUpFrom() {

        MobclickAgent.onEvent(context, UM_FACETIME_HANG_UP_FROM)
    }

    override fun faceTimeAccepted() {

        MobclickAgent.onEvent(context, UM_FACETIME_ACCEPTED)
    }

    override fun faceTimeAnswerPage() {

        MobclickAgent.onEvent(context, UM_FACETIME_ANSWER_PAGE)
    }

    override fun faceTimeAnswerButton() {

        MobclickAgent.onEvent(context, UM_FACETIME_ANSWER)
    }

    override fun faceTimeRejcet() {

        MobclickAgent.onEvent(context, UM_FACETIME_REJECT)
    }

    override fun faceTimehandUpTo() {

        MobclickAgent.onEvent(context, UM_FACETIME_HANG_UP_TO)
    }

    override fun faceTimeAddressBook() {
    }

    override fun faceTimeRecords() {
    }

    override fun faceTimeMutedVideo() {
    }

    override fun faceTimeMutedAudio() {
    }
}