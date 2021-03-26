package com.junpu.tool.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.tool.sample.R
import com.junpu.tool.sample.launch
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @author Junpu
 * @time 2018/1/3 15:05
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkUpdate.setOnClickListener {
//            AutoUpdateManager.getInstance().init(applicationContext).toast(true).checkShowDialog()
        }
        imagePicker.setOnClickListener {
            launch(ImagePickerActivity::class.java)
        }
        wifi.setOnClickListener {
            launch(NetworkActivity::class.java)
        }
        fragmentTab?.setOnClickListener {
            launch(TabActivity::class.java)
        }
        L.d("111111111111111111111111111")
        L.addTag()
        L.d("222222222222222222222222222")
        log.setOnClickListener {
            L.v("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
            L.d("ddddddddddddddddddddddddddddddddddddddddddd")
            L.i("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii")
            L.w("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
            L.e("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
            L.a("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")

            L.addTag()
            L.vv("vv--vv--vv--vv--vv--vv--vv--vv--vv--vv--vv")
            L.d("dd--dd--dd--dd--dd--dd--dd--dd--dd--dd--dd")
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
        system.setOnClickListener {
            launch(DeviceInfoActivity::class.java)
        }
    }
}