package com.junpu.viewbinding

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Activity、Dialog ViewBinding 反射封装
 * @author junpu
 * @date 2021/12/9
 */
inline fun <reified VB : ViewBinding> bindingInflate(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> bindingInflate(
    layoutInflater: LayoutInflater,
    root: ViewGroup?,
    attachToRoot: Boolean
) = VB::class.java.getMethod(
    "inflate",
    LayoutInflater::class.java,
    ViewGroup::class.java,
    Boolean::class.java
).invoke(null, layoutInflater, root, attachToRoot) as VB

inline fun <reified VB : ViewBinding> Activity.binding() = lazy {
    bindingInflate<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Activity.bindingOnly() = lazy {
    bindingInflate<VB>(layoutInflater)
}

inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    val root = window?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
    bindingInflate<VB>(layoutInflater, root, root != null)
}

inline fun <reified VB : ViewBinding> ViewGroup.binding(attachToRoot: Boolean = false) =
    bindingInflate<VB>(LayoutInflater.from(context), this, attachToRoot)

/**
 * Fragment ViewBinding 反射封装
 * @author junpu
 * @date 2021/12/9
 */
inline fun <reified VB : ViewBinding> Fragment.binding() = FragmentBindingDelegate(VB::class.java)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val cls: Class<VB>
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding =
                cls.getMethod("bind", View::class.java).invoke(null, thisRef.requireView()) as VB
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                }
            })
        }
        return binding!!
    }
}