package com.junpu.tool.sample.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.imagepicker.IImagePicker
import com.junpu.imagepicker.ImagePicker
import com.junpu.log.L
import com.junpu.tool.sample.R
import com.junpu.tool.sample.appendLine
import kotlinx.android.synthetic.main.fragment_imagepicker.*

/**
 *
 * @author junpu
 * @date 2020/9/11
 */
class ImagePickerActivity : PermissionsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagepicker)
        // 请求权限
        checkPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        ) {
        }
    }
}

/**
 *
 * @author junpu
 * @date 2020/9/11
 */
class ImagePickerFragment : Fragment() {

    private var imagePicker: IImagePicker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_imagepicker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCamera?.setOnClickListener {
            imagePicker?.startCamera()
        }

        btnPicker?.setOnClickListener {
            imagePicker?.startPick()
        }

        btnCrop?.setOnClickListener {
            L.out("onCreate: path ---> $imagePicker")
        }

        textInfo?.appendLine("onCreate")
        initImagePicker()
    }

    private fun initImagePicker() {
        imagePicker = object : ImagePicker(requireContext(), true) {
            override fun startActivityForResult(intent: Intent, requestCode: Int) {
                this@ImagePickerFragment.startActivityForResult(intent, requestCode)
            }
        }.apply {
            doOnCapture { uri, path ->
                L.vv("onCamera: uri ---> ${uri.toString()}")
                L.vv("onCamera: path ---> $path")
                textInfo?.appendLine("onCamera: ${uri?.path}\npath: $path")
                imageView?.setImageURI(uri)
            }
            doOnPick { uri, path ->
                L.vv("onPick: uri ---> ${uri.toString()}")
                L.vv("onPick: path ---> $path")
                textInfo?.appendLine("onPick: ${uri?.path}\npath: $path")
                imageView?.setImageURI(uri)
            }
            doOnCrop { uri, path ->
                L.vv("onCrop: uri ---> ${uri.toString()}")
                L.vv("onCrop: path ---> $path")
                textInfo?.appendLine("onCrop: ${uri?.path}\npath: $path")
                imageView?.setImageURI(uri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker?.onActivityResult(requestCode, resultCode, data)
    }
}