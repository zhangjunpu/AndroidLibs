package com.junpu.statistics

import android.content.Context
import com.bftv.fui.analytics.FAConstant

/**
 * 统计管理类
 *
 * @author chengxiaobo
 * @time 2018/4/10 18:00
 */
object StatisticsManager : StatisticsInterface {

    private val list = ArrayList<StatisticsInterface>()

    fun add(bean: StatisticsInterface) {

        list.add(bean)
    }

    override fun init(context: Context) {

        list.forEach {
            it.init(context)
        }
    }

    override fun updateUser(userType: FAConstant.UserType, userId: String) {
        list.forEach {
            it.updateUser(userType, userId)
        }
    }

    override fun faceTimeDail() {

        list.forEach {
            it.faceTimeDail()
        }
    }

    override fun faceTimeCancel() {

        list.forEach {
            it.faceTimeCancel()
        }
    }

    override fun faceTimeTimeOut20s() {

        list.forEach {
            it.faceTimeTimeOut20s()
        }
    }

    override fun faceTimeTimeOut60s() {

        list.forEach {
            it.faceTimeTimeOut60s()
        }
    }

    override fun faceTimeTimeBusy() {

        list.forEach {
            it.faceTimeTimeBusy()
        }
    }

    override fun faceTimeHangUpFrom() {

        list.forEach {
            it.faceTimeHangUpFrom()
        }
    }

    override fun faceTimeAccepted() {

        list.forEach {
            it.faceTimeAccepted()
        }
    }

    override fun faceTimeAnswerPage() {

        list.forEach {
            it.faceTimeAnswerPage()
        }
    }

    override fun faceTimeAnswerButton() {

        list.forEach {
            it.faceTimeAnswerButton()
        }
    }

    override fun faceTimeRejcet() {

        list.forEach {
            it.faceTimeRejcet()
        }
    }

    override fun faceTimehandUpTo() {

        list.forEach {
            it.faceTimehandUpTo()
        }
    }

    override fun faceTimeAddressBook() {
        list.forEach {
            it.faceTimeAddressBook()
        }
    }

    override fun faceTimeRecords() {
        list.forEach {
            it.faceTimeRecords()
        }
    }

    override fun faceTimeMutedVideo() {
        list.forEach {
            it.faceTimeMutedVideo()
        }
    }

    override fun faceTimeMutedAudio() {
        list.forEach {
            it.faceTimeMutedAudio()
        }
    }
}
