package com.junpu.imagepicker.demo.system

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junpu.imagepicker.IImagePicker
import com.junpu.imagepicker.ImagePicker
import com.junpu.imagepicker.demo.R
import com.junpu.imagepicker.demo.databinding.FragmentImagepickerBinding
import com.junpu.log.L
import com.junpu.utils.appendLine
import com.junpu.viewbinding.binding

/**
 * 系统相册
 * @author junpu
 * @date 2021/12/22
 */
class ImagePickerFragment : Fragment() {

    private val binding by binding<FragmentImagepickerBinding>()
    private var imagePicker: IImagePicker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_imagepicker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnCamera.setOnClickListener {
                imagePicker?.startCamera()
            }
            btnPicker.setOnClickListener {
                imagePicker?.startPick()
            }
            btnCrop.setOnClickListener {
                L.out("onCreate: path ---> $imagePicker")
            }
            textInfo.appendLine("onCreate")
        }
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
                binding.textInfo.appendLine("onCamera: ${uri?.path}\npath: $path")
                binding.imageView.setImageURI(uri)
            }
            doOnPick { uri, path ->
                L.vv("onPick: uri ---> ${uri.toString()}")
                L.vv("onPick: path ---> $path")
                binding.textInfo.appendLine("onPick: ${uri?.path}\npath: $path")
                binding.imageView.setImageURI(uri)
            }
            doOnCrop { uri, path ->
                L.vv("onCrop: uri ---> ${uri.toString()}")
                L.vv("onCrop: path ---> $path")
                binding.textInfo.appendLine("onCrop: ${uri?.path}\npath: $path")
                binding.imageView.setImageURI(uri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker?.onActivityResult(requestCode, resultCode, data)
    }
}