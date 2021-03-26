package com.junpu.widget.indicator;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

/**
 * @author zhangjunpu
 * @date 2017/1/5
 */

public class TabPagerManager {

    private Context mComtext;
    private FragmentHandlerAdapter mTabsAdapter;
    private ViewPager mViewPager;
    private int mDefaultPosition;

    public TabPagerManager(Context ctx) {
        mComtext = ctx;
    }

    public TabPagerManager init(FragmentManager fm) {
        mTabsAdapter = new FragmentHandlerAdapter(fm, mComtext);
        return this;
    }

    public FragmentHandlerAdapter getTabsAdapter() {
        return mTabsAdapter;
    }

    public TabPagerManager add(FragmentHandlerAdapter.TabInfo tabInfo) {
        mTabsAdapter.addTab(tabInfo);
        return this;
    }

    public static FragmentHandlerAdapter.TabInfo build(String name, Class<? extends Fragment> fragment) {
        return new FragmentHandlerAdapter.TabInfo(fragment, name, null);
    }

    public static FragmentHandlerAdapter.TabInfo build(String name, Class<? extends Fragment> fragment, Bundle arguments) {
        return new FragmentHandlerAdapter.TabInfo(fragment, name, arguments);
    }

    public TabPagerManager setDefaultPosition(int index) {
        mDefaultPosition = index;
        return this;
    }

    public TabPagerManager setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return this;
        }
        mViewPager = viewPager;
        viewPager.setOffscreenPageLimit(mTabsAdapter.getCount());
        viewPager.setAdapter(mTabsAdapter);
        viewPager.setCurrentItem(mDefaultPosition);
        return this;
    }

    public TabPagerManager addOnPageChangeListener(OnPageChangeListener listener) {
        mViewPager.addOnPageChangeListener(listener);
        return this;
    }

    public TabPagerManager setIndicator(TabsIndicator tabs) {
        return setIndicator(tabs, true);
    }

    public TabPagerManager setIndicator(TabsIndicator tabs, boolean isFullWidth) {
        if (tabs == null) return this;
        tabs.setViewPager(mViewPager, isFullWidth);
        return this;
    }

    /**
     * 默认Position
     * @param position
     */
    public TabPagerManager defaultPosition(int position) {
        mDefaultPosition = position;
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
        return this;
    }

    public Object getFragment(int position) {
        return mTabsAdapter.getFragment(mViewPager, position);
    }

    public int getCount() {
        return mTabsAdapter.getCount();
    }
}
