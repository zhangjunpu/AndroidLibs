package com.junpu.widget.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;

import com.junpu.widget.indicator.utils.IndicatorUtils;

public class PointIndicator extends RadioGroup {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    private int mPointDrawable;
    private int mPointWidth; // 默认4dp
    private int mPointHeight; // 默认4dp
    private int mPointMargin; // 默认2dp

    public PointIndicator(Context context) {
        super(context);
        init();
    }

    public PointIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPointWidth = IndicatorUtils.dp2px(getContext(), 4);
        mPointHeight = IndicatorUtils.dp2px(getContext(), 4);
        mPointDrawable = R.drawable.point_indicator_default;
        mPointMargin = IndicatorUtils.dp2px(getContext(), 2);
    }

    public void setViewPager(ViewPager viewPager, int pointDrawableRes) {
        setPointDrawable(pointDrawableRes);
        setViewPager(viewPager);
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) return;
        mViewPager = viewPager;
        mAdapter = mViewPager.getAdapter();
        int count = mAdapter.getCount();
        if (count <= 0) return;
        addRadioButton(count);
        setChecked(0);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 图片样式
     */
    public PointIndicator setPointDrawable(int resid) {
        this.mPointDrawable = resid;
        return this;
    }

    /**
     * 图片尺寸
     */
    public PointIndicator setPointSize(int width, int height) {
        this.mPointWidth = width;
        this.mPointHeight = height;
        return this;
    }

    /**
     * 图片间距
     */
    public PointIndicator setPointMargin(int mPointMargin) {
        this.mPointMargin = mPointMargin;
        return this;
    }

    private void addRadioButton(int count) {
        removeAllViews();
        for (int i = 0; i < count; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.point_indicator_radio_default, this, false);
            radioButton.setClickable(false);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(mPointDrawable, 0, 0, 0);
            LayoutParams params = new LayoutParams(mPointWidth, mPointHeight);
            params.setMargins(mPointMargin, mPointMargin, mPointMargin, mPointMargin);
            addView(radioButton, params);
        }
    }

    private SimpleOnPageChangeListener onPageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            setChecked(position);
        }
    };

    public void buildItem(int count) {
        if (count <= 0) return;
        addRadioButton(count);
        setChecked(0);
    }

    public void setChecked(int position) {
        if (position >= 0 && position < getChildCount()) {
            check(getChildAt(position).getId());
        }
    }

}
