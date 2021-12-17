package com.junpu.log.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.log.L
import com.junpu.log.demo.databinding.ActivityMainBinding
import com.junpu.log.logStackTrace

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLog.setOnClickListener {
            L.v("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
            L.d("ddddddddddddddddddddddddddddddddddddddddddd")
            L.i("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii")
            L.w("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
            L.e("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
            L.a("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")

            L.addTag()
            L.vv("vv--vv--vv--vv--vv--vv--vv--vv--vv--vv--vv")
            L.dd("dd--dd--dd--dd--dd--dd--dd--dd--dd--dd--dd")
            L.ii("ii--ii--ii--ii--ii--ii--ii--ii--ii--ii--ii")
            L.ww("ww--ww--ww--ww--ww--ww--ww--ww--ww--ww--ww")
            L.ee("ee--ee--ee--ee--ee--ee--ee--ee--ee--ee--ee")
            L.aa("aa--aa--aa--aa--aa--aa--aa--aa--aa--aa--aa")

            L.removeTag()
            L.vvv("vvvvv-vvvvv-vvvvv-vvvvv-vvvvv-vvvvv-vvvvv")
            L.ddd("ddddd-ddddd-ddddd-ddddd-ddddd-ddddd-ddddd")
            L.iii("iiiii-iiiii-iiiii-iiiii-iiiii-iiiii-iiiii")
            L.www("wwwww-wwwww-wwwww-wwwww-wwwww-wwwww-wwwww")
            L.eee("eeeee-eeeee-eeeee-eeeee-eeeee-eeeee-eeeee")
            L.aaa("aaaaa-aaaaa-aaaaa-aaaaa-aaaaa-aaaaa-aaaaa")

            L.addTag()
            L.d()
            L.d()
            L.ddd()
            val e = RuntimeException()
            e.logStackTrace()
        }
    }
}