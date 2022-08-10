package com.junpu.imagepreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.junpu.imagepreview.databinding.ActivityImagePreviewBinding

/**
 * 图片预览
 *
 * @author zhangjunpu
 * @date 14/11/21
 */
class ImagePreviewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityImagePreviewBinding.inflate(layoutInflater) }
    private var urls: Array<String>? = null
    private var size = 0
    private var curIndex = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        curIndex = savedInstanceState?.getInt(ARG_INDEX) ?: intent.getIntExtra(ARG_INDEX, 0)
        urls = intent.getStringArrayExtra(ARG_IMAGES)?.also {
            size = it.size
        }
        initView()
        updateIndex()
    }

    private fun initView() {
        binding.run {
            btnBack.setOnClickListener { finish() }
            viewPager.run {
                addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                    override fun onPageSelected(i: Int) {
                        curIndex = i
                        updateIndex()
                    }
                })
                adapter = ImagePreviewAdapter(this@ImagePreviewActivity, urls)
                currentItem = curIndex
            }
            if (size == 1) textNum.visibility = View.INVISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_INDEX, binding.viewPager.currentItem)
    }

    private fun updateIndex() {
        binding.textNum.text = getString(R.string.page_num, curIndex + 1, size)
    }

    companion object {
        private const val ARG_INDEX = "index"
        private const val ARG_IMAGES = "images"

        fun launch(context: Context, vararg urls: String) {
            launch(context, urls, 0)
        }

        fun launch(context: Context, urls: List<String>, position: Int) {
            launch(context, urls.toTypedArray(), position)
        }

        fun launch(context: Context, arr: Array<out String>, position: Int) {
            context.startActivity(Intent(context, ImagePreviewActivity::class.java).apply {
                putExtra(ARG_IMAGES, arr)
                putExtra(ARG_INDEX, position)
            })
        }
    }
}