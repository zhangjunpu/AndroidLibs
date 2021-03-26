package com.junpu.statistics

import android.content.Context
import com.bftv.fui.analytics.FAConstant

/**
 * 统计interface
 *
 * @author chengxiaobo
 * @time 2018/4/10 16:00
 */
interface StatisticsInterface {

    /**
     * 初始化
     */
    fun init(context: Context)

    /**
     * 更新用户信息
     */
    fun updateUser(userType: FAConstant.UserType,userId: String)

    /**
     * 发起端_拨打电话
     */
    fun faceTimeDail()

    /**
     * 发起端_取消拨打按钮
     */
    fun faceTimeCancel()

    /**
     * 发起端_拨打电话界面20s超时
     */
    fun faceTimeTimeOut20s()

    /**
     * 发起端_拨打电话界面60s超时
     */
    fun faceTimeTimeOut60s()

    /**
     * 发起端_用户占线
     */
    fun faceTimeTimeBusy()

    /**
     * 发起端_挂断电话按钮
     */
    fun faceTimeHangUpFrom()

    /**
     * 发起端_被接听(收到对方接听的IM消息)
     */
    fun faceTimeAccepted()


    /**
     * 接收端_接听电话界面
     */
    fun faceTimeAnswerPage()

    /**
     * 接收端_接听电话按钮
     */
    fun faceTimeAnswerButton()

    /**
     * 接收端_拒接电话按钮
     */
    fun faceTimeRejcet()

    /**
     * 接收端_挂断电话按钮
     */
    fun faceTimehandUpTo()

    /**
     * 通讯录
     */
    fun faceTimeAddressBook()

    /**
     * 通话记录
     */
    fun faceTimeRecords()

    /**
     * 关闭摄像头
     */
    fun faceTimeMutedVideo()

    /**
     * 关闭麦克风
     */
    fun faceTimeMutedAudio()
}
