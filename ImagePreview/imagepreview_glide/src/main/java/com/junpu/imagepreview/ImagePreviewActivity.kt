package com.junpu.imagepreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.junpu.imagepreview.databinding.ActivityImagePreviewBinding
import java.util.*

/**
 * 视频剧照
 *
 * @author zhangjunpu
 * @date 14/11/21
 */
class ImagePreviewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityImagePreviewBinding.inflate(layoutInflater) }

    private var size = 0
    private var curIndex = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener { finish() }
        initData(savedInstanceState)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CUR_INDEX, binding.viewPager.currentItem)
    }

    private fun initData(savedInstanceState: Bundle?) {
        val urls = intent.getStringArrayExtra(ARGS_ARRAY)
        size = urls?.size ?: 0
        curIndex = savedInstanceState?.getInt(CUR_INDEX) ?: intent.getIntExtra(ARGS_POSITION, 0)
        println("-------> $urls, ${size}, $curIndex")
        binding.viewPager.run {
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(i: Int) {
                    updateIndex(i)
                }
            })
            adapter = ImagePreviewAdapter(this@ImagePreviewActivity, urls)
            currentItem = curIndex
        }
        updateIndex(curIndex)
        if (size == 1) binding.textNum.visibility = View.INVISIBLE
    }

    private fun updateIndex(index: Int) {
        binding.textNum.text = getString(R.string.page_num, index + 1, size)
    }

    companion object {
        private const val CUR_INDEX = "cur_index"
        private const val ARGS_POSITION = "args_position"
        private const val ARGS_ARRAY = "args_array"

        fun launch(context: Context, vararg urls: String) {
            launch(context, urls, 0)
        }

        fun launch(context: Context, urls: List<String>, position: Int) {
            launch(context, urls.toTypedArray(), position)
        }

        fun launch(context: Context, arr: Array<out String>, position: Int) {
            val intent = Intent(context, ImagePreviewActivity::class.java).apply {
                putExtra(ARGS_ARRAY, arr)
                putExtra(ARGS_POSITION, position)
            }
            context.startActivity(intent)
        }
    }
}