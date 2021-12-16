package com.junpu.dialog.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.junpu.dialog.*
import com.junpu.dialog.demo.databinding.ActivityMainBinding
import com.junpu.dialog.demo.databinding.CustomViewBinding
import com.junpu.dialog.demo.utils.DialogHelper
import com.junpu.toast.toast
import com.junpu.utils.dip
import com.junpu.utils.formatDateTime
import com.junpu.utils.postDelay
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initRecycler()
    }

    private fun initView() {
        binding.btnLoading.setOnClickListener {
            val dialog = DialogHelper.showLoadingDialog(this)
            dialog?.setCancelable(true)
        }
        binding.btnToast.setOnClickListener {
            ToastDialog.Builder(this)
                .setMessage("This is a toast dialog")
                .setSubmitText("提交")
                .hideCancel()
                .doOnSubmit { _, _ -> println("submit") }
                .doOnCancel { _, _ -> println("cancel") }
                .show()
        }
        binding.btnList.setOnClickListener {
            val list = arrayOf("1", "2")
            ListDialog.Builder(this)
                .setList(list)
                .doOnItemClick { _, _, position ->
                    println("position = $position")
                    toast("click item $position")
                }
                .doOnButtonClick { dialog, view ->
                    toast("click button")
                }
                .isLandScape(false)
                .show()
        }
        binding.btnEdit.setOnClickListener {
            EditDialog.Builder(this)
                .setTitle("备注")
//                .setMessage("备注填写")
                .setHint("请输入备注")
                .doOnSubmit { _, _, message ->
                    println("message = $message")
                    toast(message)
                }
                .show()
        }
        binding.btnCustomView.setOnClickListener {
            val binding = CustomViewBinding.inflate(layoutInflater)
            val params = FrameLayout.LayoutParams(-1, dip(300))
            val dialog = CustomViewDialog.Builder(this)
                .setTitle("Custom View Title")
                .setMessage("Custom View Dialog Message!")
                .setSubTitle("Custom View SubTtitle")
                .setCustomView(binding.root, params)
                .create()
            postDelay(2000) {
                dialog.success()
            }
            dialog.show()
        }
        binding.btnNumberPicker.setOnClickListener {
            NumberPickerDialog.Builder(this)
                .setMaxValue(10)
                .setMinValue(1)
                .doOnValueChanged { _, oldVal, newVal ->
                    println("onValueChanged = $oldVal -> $newVal")
                }
                .show()
        }
        binding.btnAge.setOnClickListener {
            AgeDialog.Builder(this)
                .doOnDateChanged { _, year, month, day ->
                    println("OnDateChanged = ${year}, ${month}, $day")
                }
                .show()
        }
        binding.btnDate.setOnClickListener {
            val endTimeMillis = System.currentTimeMillis()
            val startTimeMillis = Calendar.getInstance().apply {
                timeInMillis = endTimeMillis
                add(Calendar.DAY_OF_YEAR, -7)
            }.timeInMillis
            println("start.formatDateTime = ${startTimeMillis.formatDateTime}")
            println("end.formatDateTime = ${endTimeMillis.formatDateTime}")
            DateDialog.Builder(this)
                .initDate(startTimeMillis, endTimeMillis)
                .doOnSubmit { dialog, start, end ->
                    println("start = ${start}, $end")
                    println("start.formatDateTime = ${start.formatDateTime}")
                    println("end.formatDateTime = ${end.formatDateTime}")
                    dialog.dismiss()
                }
                .doOnDismiss {
                    println("DialogActivity.dismiss $it")
                }
                .show()

//            DatePickerDialog(this, { view, year, month, day ->
//                println("year = ${year}, $month, $day")
//            }, 2020, 1, 1).show()
        }

    }

    private fun initRecycler() {
        val list = mutableListOf<String>().apply {
            for (i in 0..15) {
                add("item $i")
            }
        }
        val adapter = MyAdapter().apply {
            doOnItemClick { _, position -> toast("click custom adapter $position") }
        }
        var dialog: RecyclerDialog? = null
        binding.btnRecycler.setOnClickListener {
            if (dialog == null) {
                dialog = RecyclerDialog.Builder(this)
                    .setTitle("添加每日推送")
                    .setSubTitle("选择您准备要生成每日推送信息的学生姓名")
                    .setMessage("工作日每晚10点，将自动推送所有学生的每日报告。请在此时间前，完成所有学生的学管师评语。\n\n已完成评语并推送学员的，不会重复推送。")
//                    .setWidth(dip(350))
//                    .setHeight(dip(500))
                    .setLayoutManager(GridLayoutManager(this, 4))
//                    .setAdapter(adapter)
                    .doOnDefaultAdapterItemClick { _, position ->
                        toast("click default adapter $position")
                    }
                    .create()
            }
            dialog?.loading()
//            dialog?.setList(list)
            Handler(Looper.getMainLooper()).postDelayed({
                println("list = $list")
                adapter.update(list)
                dialog?.success()
                dialog?.setList(list)
            }, 3000)
            dialog?.show()
        }
    }
}