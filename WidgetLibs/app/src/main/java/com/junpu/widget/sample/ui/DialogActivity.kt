package com.junpu.widget.sample.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.junpu.toast.toast
import com.junpu.widget.dialog.*
import com.junpu.widget.sample.R
import com.junpu.widget.sample.utils.DialogHelper
import com.junpu.widget.sample.utils.formatDateTime
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.dialog_recycler_item.view.*
import java.util.*

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:02
 */
class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        initView()
        initRecycler()
    }

    private fun initView() {
        btnList.setOnClickListener {
            val list = arrayOf("1", "2")
            ListDialog.Builder(this)
                .setList(list)
                .setOnItemClickListener { dialog, position ->
                    println("position = $position")
                }
                .setIsLandScape(false)
                .show()
        }
        btnEdit.setOnClickListener {
            EditDialog.Builder(this)
                .setTitle("备注")
                .setMessage("备注填写")
                .setHint("请输入备注")
                .setPositiveButton { dialog, id, message ->
                    println("message = $message")
                }
                .show()
        }
        btnLoading.setOnClickListener {
            val dialog = DialogHelper.showLoadingDialog(this)
            dialog?.setCancelable(true)
        }
        btnToast?.setOnClickListener {
//            DialogHelper.showToastDialog(this, "This is a toast dialog") { dialog, view ->
//                println("dialog = $dialog , $view")
//            }
            ToastDialog.Builder(this)
                .setMessage("This is a toast dialog")
                .setSubmitText("提交")
                .hideCancel()
                .doOnSubmit { dialog, view -> println("submit") }
                .doOnCancel { dialog, view -> println("cancel") }
                .show()
        }
        btnNumberPicker?.setOnClickListener {
            NumberPickerDialog.Builder(this)
                .setMaxValue(10)
                .setMinValue(1)
                .setOnValueChangedListener { picker, oldVal, newVal ->
                    println("onValueChanged = ${oldVal} -> ${newVal}")
                }
                .show()
        }
        btnAge?.setOnClickListener {
            AgeDialog.Builder(this)
                .setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                    println("OnDateChanged = ${year}, ${monthOfYear}, ${dayOfMonth}")
                }
                .show()
        }
        btnDate?.setOnClickListener {
            val end = System.currentTimeMillis()
            val start = Calendar.getInstance().apply {
                timeInMillis = end
                add(Calendar.DAY_OF_YEAR, -7)
            }.timeInMillis
            println("start.formatDateTime = ${start.formatDateTime}")
            println("end.formatDateTime = ${end.formatDateTime}")
            DateDialog.Builder(this)
                .initDate(start, end)
                .setOnSubmitListener { dialog, start, end ->
                    println("start = ${start}, ${end}")
                    println("start.formatDateTime = ${start.formatDateTime}")
                    println("end.formatDateTime = ${end.formatDateTime}")
                    dialog.dismiss()
                }
                .setOnDismissListener {
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
        val layoutManager =
            GridLayoutManager(this, 4)
        val adapter = MyAdapter().apply {
            setOnItemClickListener { _, _, position -> toast("click item $position") }
        }
        var dialog: RecyclerDialog? = null
        btnRecycler.setOnClickListener {
            if (dialog == null) {
                dialog = RecyclerDialog.Builder(this)
                    .setTitle("添加每日推送")
                    .setSubTitle("选择您准备要生成每日推送信息的学生姓名")
                    .setMessage("工作日每晚10点，将自动推送所有学生的每日报告。请在此时间前，完成所有学生的学管师评语。\n\n已完成评语并推送学员的，不会重复推送。")
//                        .setWidth(dip(350))
//                    .setHeight(dip(500))
                    .setLayoutManager(layoutManager)
                    .setAdapter(adapter)
                    .setOnItemClickListener { _, _, position -> toast("$position") }
//                        .setList(arrayOf("1", "2", "3", "4", "5"))
                    .create()
            }
            dialog?.loading()
//            dialog?.setList(list)
            Handler().postDelayed({
                println("list = $list")
                adapter.update(list)
                dialog?.success()
            }, 3000)
            dialog?.show()
        }
    }

}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var data: MutableList<String> = ArrayList()
    private var onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)? =
        null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.dialog_recycler_item, viewGroup, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val item = data[position]
        viewHolder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal fun setOnItemClickListener(listener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?) {
        this.onItemClickListener = listener
    }

    internal fun update(list: MutableList<String>?) {
        if (list == null) return
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}

class MyViewHolder(
    itemView: View,
    private val onItemClickListener: ((recyclerView: RecyclerView?, itemView: View?, position: Int) -> Unit)?
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    internal fun bind(text: String) {
        itemView.textMessage.text = text
    }

    override fun onClick(v: View) {
        onItemClickListener?.invoke(itemView.parent as RecyclerView, v, adapterPosition)
    }
}
