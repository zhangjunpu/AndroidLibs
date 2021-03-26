//package com.bftv.widget.sample.ui
//
//import android.content.Context
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import com.bftv.widget.slide.SlideView
//import kotlinx.android.synthetic.main.activity_slide.*
//import android.support.v7.widget.DividerItemDecoration
//import com.junpu.widget.sample.R
//
//
//class SlideActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_slide)
//
////        slideView.setListener(object :SlideView.ClickListener{
////            override fun clickAddNick() {
////                Toast.makeText(this@SlideActivity,"clickAddNick",Toast.LENGTH_SHORT).show()
////            }
////
////            override fun clickDelete() {
////                Toast.makeText( this@SlideActivity,"clickDelete",Toast.LENGTH_SHORT).show()
////            }
////        })
////        val view=ImageView(this)
////        view.setImageResource(R.mipmap-xxhdpi.ic_launcher)
////        slideView.addContainerView(view)
//
//        recyclerView.adapter = MyAdapter(this)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        recyclerView.setOnTouchListener { _, _ ->
//            recyclerView.adapter.notifyDataSetChanged()
//            false
//        }
//
//    }
//}
//
//class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
//
//    override fun getItemCount(): Int {
//        return 30
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
//        holder?.let {
//            holder.tvName?.text = "用户$position"
//            holder.slideView?.setListener(object : SlideView.ClickListener {
//                override fun clickAddNick() {
//                    Toast.makeText(context, "clickAddNick", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun clickDelete() {
//                    Toast.makeText(context, "clickDelete", Toast.LENGTH_SHORT).show()
//                }
//            })
//            holder.slideView?.shrink(true)
//            holder.slideView?.setOnClickListener {
//                notifyDataSetChanged()
//            }
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.view_slide_item, null)
//        val viewContainer = LayoutInflater.from(context).inflate(R.layout.view_slide_container, null)
//        view.findViewById<SlideView>(R.id.slideView).addContainerView(viewContainer)
//        return MyViewHolder(view)
//    }
//}
//
//class MyViewHolder : RecyclerView.ViewHolder {
//
//    var ivNick: ImageView? = null
//    var tvName: TextView? = null
//    var btnDelete: Button? = null
//    var btnNickName: Button? = null
//    var slideView: SlideView? = null
//
//    constructor(view: View) : super(view) {
//        ivNick = view.findViewById(R.id.ivNickName)
//        tvName = view.findViewById(R.id.tvName)
//        btnDelete = view.findViewById(R.id.btnDelete)
//        btnNickName = view.findViewById(R.id.btnNickName)
//        slideView = view.findViewById(R.id.slideView)
//    }
//
//}
//
