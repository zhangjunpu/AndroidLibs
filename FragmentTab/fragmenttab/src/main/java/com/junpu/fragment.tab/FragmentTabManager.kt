package com.junpu.fragment.tab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

/**
 * FragmentTab切换
 * @author junpu
 * @date 2019-11-22
 */
class FragmentTabManager(
    private val content: Context,
    private var fragmentManager: androidx.fragment.app.FragmentManager,
    private val contentViewId: Int
) {

    private val tabs = arrayListOf<Tab>()
    private var isBackStack = false
    private var curFragment: Fragment? = null
    private var switchListener: ((from: Fragment?, to: Fragment?) -> Unit)? = null

    fun addTab(cls: Class<out Fragment>, tag: String = cls.simpleName, args: Bundle? = null) {
        tabs.add(Tab(tag, cls, args))
    }

    fun addTabs(list: List<Tab>?) {
        if (!list.isNullOrEmpty()) {
            tabs.addAll(list)
        }
    }

    fun getTabs(): List<Tab> = tabs

    fun getTab(position: Int): Tab? {
        if (position in tabs.indices) {
            return tabs[position]
        }
        return null
    }

    fun setBackStack(flag: Boolean) {
        isBackStack = flag
    }

    fun setSwitchListener(listener: ((from: Fragment?, to: Fragment?) -> Unit)? = null) {
        switchListener = listener
    }

    /**
     * 查找Fragment
     */
    fun findFragment(position: Int): Fragment? = getTab(position)?.let { findFragment(it) }

    /**
     * 查找Fragment
     */
    fun findFragment(tag: Tab): Fragment? = fragmentManager.findFragmentByTag(tag.tag)

    /**
     * 返回Fragment信息
     */
    fun instance(tab: Tab): Fragment {
        var fragment = fragmentManager.findFragmentByTag(tab.tag)
        if (fragment == null) {
            fragment = FragmentFactory().instantiate(content.classLoader, tab.cls.name).apply {
                arguments = tab.args
            }
            fragmentManager.beginTransaction().run {
                add(contentViewId, fragment, tab.tag)
                if (isBackStack) addToBackStack(tab.tag)
                commit()
            }
        }
        return fragment
    }

    /**
     * 切换Fragment
     */
    fun switchTo(position: Int) {
        getTab(position)?.let {
            switchTo(it)
        }
    }

    /**
     * 切换Fragment
     */
    fun switchTo(tab: Tab) {
        switchTo(instance(tab))
    }

    /**
     * 切换到Fragment
     */
    fun switchTo(fragment: Fragment) {
        if (fragment != curFragment) {
            val lastFragment = curFragment
            curFragment = fragment
            fragmentManager.beginTransaction().run {
                lastFragment?.let { hide(it) }
                curFragment?.let { show(it) }
                commit()
            }
            switchListener?.invoke(lastFragment, curFragment)
        }
    }

    /**
     * 返回上一个Fragment
     */
    fun backStack(): Boolean {
        val flag = fragmentManager.popBackStackImmediate()
        if (flag) {
            val fragments = fragmentManager.fragments
            val lastFragment = curFragment
            while (!fragments.isNullOrEmpty()) {
                curFragment = fragments.last()
                if (curFragment == null) {
                    fragments.removeAt(fragments.lastIndex)
                    continue
                }
                fragmentManager.beginTransaction().run {
                    lastFragment?.let { hide(lastFragment) }
                    show(curFragment!!)
                    commit()
                }
                switchListener?.invoke(lastFragment, curFragment)
                return true
            }
            return false
        }
        return flag
    }

    /**
     * Fragment保存信息
     */
    data class Tab(
        var tag: String,
        var cls: Class<out Fragment>,
        var args: Bundle?
    )
}