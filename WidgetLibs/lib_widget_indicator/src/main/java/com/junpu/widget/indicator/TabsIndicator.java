package com.junpu.widget.indicator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.junpu.widget.indicator.utils.IndicatorUtils;

import java.util.ArrayList;
import java.util.List;

public class TabsIndicator extends FrameLayout implements OnClickListener {

    public static final int ITEM_WIDTH = 75; //dp

    private ViewPager mViewPager;
    private LinearLayout radioGroup;
    private UnderlinePageIndicator indicator;

    private int mTextSize = 12; // sp
    private int mLineWidth = -1; // 底部游标宽长度
    private int mCount;

    private ColorStateList mTextColor;
    private boolean isCountMode = false; // 消息数量模式
    private int mCurPosition;
    private List<Tab> mTabs = new ArrayList<>();

    public TabsIndicator(Context context) {
        super(context);
        initView();
    }

    public TabsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabsIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        LayoutParams indicatorParams = new LayoutParams(LayoutParams.MATCH_PARENT, IndicatorUtils.dp2px(getContext(), 2));
        indicatorParams.gravity = Gravity.BOTTOM;
//        indicatorParams.bottomMargin = ToolUtils.dp2px(getContext(), 0);
        indicator = (UnderlinePageIndicator) LayoutInflater.from(getContext()).inflate(R.layout.underline_indicator, this, false);
        indicator.setFades(false);
        indicator.setLine(true);
        indicator.setSpace(IndicatorUtils.dp2px(getContext(), 20));
        setIndicatorColorRes(R.color.default_indicator);
        addView(indicator, indicatorParams);

        LayoutParams radiogroupParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        radioGroup = new RadioGroup(getContext());
        radioGroup = new LinearLayout(getContext());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setGravity(Gravity.CENTER);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int position = group.indexOfChild(group.findViewById(checkedId));
//                if (mViewPager != null) {
//                    mViewPager.setCurrentItem(position, true);
//                }
//            }
//        });
        addView(radioGroup, radiogroupParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (mCount == 0) return;
        int space = (width / mCount - mLineWidth) / 2;
        indicator.setSpace(space);
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, false);
    }

    public void setViewPager(ViewPager viewPager, boolean isFullWidth) {
        mViewPager = viewPager;
        indicator.setViewPager(viewPager);
        PagerAdapter adapter = viewPager.getAdapter();
        mCount = adapter.getCount();
        if (!isFullWidth) {
            getLayoutParams().width = mCount * IndicatorUtils.dp2px(getContext(), ITEM_WIDTH);
        }

        radioGroup.removeAllViews();
        int length = 0; // 最长的Item文字长度
        for (int i = 0; i < mCount; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title.length() > length) {
                length = title.length();
            }
            addTab(title);
        }

        if (mLineWidth == -1) {
            mLineWidth = IndicatorUtils.dp2px(getContext(), mTextSize) * length + IndicatorUtils.dp2px(getContext(), 2);
        }

        checked(0);
        // 不显示底部的滑动条
        if (mCount <= 1) {
            indicator.setVisibility(View.INVISIBLE);
        } else {
            indicator.setVisibility(View.VISIBLE);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                checked(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = radioGroup.indexOfChild(v);
        if (position == -1 || position == mCurPosition) {
            return;
        }
//        checked(position);
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, true);
        }
    }

    public void addTab(CharSequence text) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tabs_indicator_item, radioGroup, false);
        view.setOnClickListener(this);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setTextSize(mTextSize);
        name.setText(text);
        if (mTextColor != null) {
            name.setTextColor(mTextColor);
        }
        ImageView count = (ImageView) view.findViewById(R.id.count);
        count.setVisibility(GONE);

        radioGroup.addView(view);
        mTabs.add(new Tab(name, count));
    }

    /**
     * 选择Item
     */
    private void checked(int position) {
        if (mTabs == null || mTabs.isEmpty()) {
            return;
        }
        int size = mTabs.size();
        if (position < 0 || position >= size) {
            return;
        }
        mCurPosition = position;
        for (int i = 0; i < size; i++) {
            Tab tab = mTabs.get(i);
            tab.setChecked(i == position);
        }
    }

    /**
     * 设置是否为消息数量模式
     *
     * @param countMode
     */
    public void setCountMode(boolean countMode) {
        isCountMode = countMode;
    }

    /**
     * 设置消息数量
     *
     * @param position
     * @param count
     */
    public void setCount(int position, int count) {
        if (mTabs == null || mTabs.isEmpty() || position < 0 || position >= mTabs.size())
            return;
        Tab tab = mTabs.get(position);
        tab.setCount(count);
    }

    /**
     * 设置消息数量
     *
     * @param position
     * @param count
     */
    public void setCount(int position, String count) {
        if (mTabs == null || mTabs.isEmpty() || position < 0 || position >= mTabs.size())
            return;
        Tab tab = mTabs.get(position);
        tab.setCount(count);
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public void setTextColor(int color) {
        mTextColor = ColorStateList.valueOf(color);
    }

    public void setTextColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }
        mTextColor = colors;
    }

    public void setUnderLineSpace(int underLineSpace) {
        if (indicator != null) {
            indicator.setSpace(underLineSpace);
        }
    }

    public void setIndicatorVisibility(int visibility) {
        if (indicator != null) {
            indicator.setVisibility(visibility);
        }
    }

    public void setIndicatorColor(int color) {
        indicator.setSelectedColor(color);
    }

    public void setIndicatorColorRes(int res) {
        indicator.setSelectedColor(getResources().getColor(res));
    }

    public void setLineWidth(int width) {
        mLineWidth = width;
    }

    class Tab {
        public TextView name;
        public ImageView count;

        public Tab(TextView name, ImageView count) {
            this.name = name;
            this.count = count;
        }

        public void setChecked(boolean isChecked) {
            name.setTextColor(getResources().getColor(isChecked ? R.color.tab_text_on : R.color.tab_text_off));
        }

        public void setCount(int count) {
//            this.count.setText(String.valueOf(Math.max(0, count)));
            if (!isCountMode || count <= 0) {
                this.count.setVisibility(GONE);
            } else {
                this.count.setVisibility(VISIBLE);
            }
        }

        public void setCount(String count) {
//            this.count.setText(count);
            this.count.setVisibility(isCountMode ? VISIBLE : GONE);
        }
    }

}
