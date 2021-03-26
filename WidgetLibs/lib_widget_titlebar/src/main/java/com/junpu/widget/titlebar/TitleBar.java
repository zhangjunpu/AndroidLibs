package com.junpu.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 统一使用的标题栏，目前使用方式为ActionBar的CustomView
 *
 * @author liruiyu E-mail:allnet@live.cn
 * @version 创建时间：2014-7-23 类说明
 */
public class TitleBar extends FrameLayout implements OnClickListener {

    /**
     * 浅色模式（默认）
     */
    public static final int COLOR_MODE_LIGHT = 0;

    /**
     * 深色模式
     */
    public static final int COLOR_MODE_DARK = 1;

    private TextView mLeftText;
    private TextView mRightText;
    private TextView mTitleText;

    private int mColorMode = COLOR_MODE_LIGHT;

    public TitleBar(Context context) {
        this(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        int height = getResources().getDimensionPixelOffset(R.dimen.titlebar_height);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        setLayoutParams(params);

        LayoutInflater.from(getContext()).inflate(R.layout.titlebar_layout, this);
        mLeftText = findViewById(R.id.Back);
        mRightText = findViewById(R.id.Next);
        mTitleText = findViewById(R.id.Title);
        mLeftText.setOnClickListener(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyle, 0);
        mColorMode = a.getInt(R.styleable.TitleBar_colorMode, COLOR_MODE_LIGHT);
        CharSequence title = a.getText(R.styleable.TitleBar_textTitle);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        a.recycle();
        setMode();

    }

    private void setMode() {
        switch (mColorMode) {
            case COLOR_MODE_LIGHT:
                mLeftText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_menu_back_black, 0, 0, 0);
                mTitleText.setTextColor(getResources().getColor(R.color.titlebar_text_color_dark));
                mRightText.setTextColor(getResources().getColor(R.color.titlebar_text_color_dark));
                break;
            case COLOR_MODE_DARK:
                mLeftText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_menu_back_white, 0, 0, 0);
                mTitleText.setTextColor(getResources().getColor(R.color.titlebar_text_color_light));
                mRightText.setTextColor(getResources().getColor(R.color.titlebar_text_color_light));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Back) {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    /**
     * 设置标题
     */
    public void setTitle(int resid) {
        mTitleText.setText(resid);
    }

    public String getTitle() {
        return mTitleText.getText().toString();
    }

    /**
     * 设置指定Item的visibility
     */
    public void setItemVisibility(int whitchId, int visibility) {
        findViewById(whitchId).setVisibility(visibility);
    }

    public void setOnClickListener(int whitchId, OnClickListener listener) {
        findViewById(whitchId).setOnClickListener(listener);
    }

    /**
     * 设置指定Item的文本
     */
    public TextView setItemText(int whitchId, String text) {
        TextView item = (TextView) findViewById(whitchId);
        item.setText(text);
        item.setVisibility(VISIBLE);
        return item;
    }

    /**
     * 设置指定Item的文本
     */
    public TextView setItemText(int whitchId, int resid) {
        TextView item = (TextView) findViewById(whitchId);
        item.setText(resid);
        item.setVisibility(VISIBLE);
        return item;
    }

    /**
     * 设置Item的Drawable
     */
    public TextView setItemDrawable(int whitchId, int resid) {
        TextView item = (TextView) findViewById(whitchId);
        item.setVisibility(View.VISIBLE);
        if (whitchId == R.id.Back) {
            if (resid != 0)
                item.setCompoundDrawablesWithIntrinsicBounds(resid, 0, 0, 0);
            else
                item.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            if (resid != 0)
                item.setCompoundDrawablesWithIntrinsicBounds(0, 0, resid, 0);
            else
                item.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return item;
    }

    /**
     * 设置Item的Text点击效果
     */
    public TextView setItemTextColor(int whitchId, int color) {
        TextView item = (TextView) findViewById(whitchId);
        item.setVisibility(View.VISIBLE);
        item.setTextColor(color);
        return item;
    }

    /**
     * 设置Item的Text点击效果
     */
    public TextView setItemTextColor(int whitchId, ColorStateList colors) {
        TextView item = (TextView) findViewById(whitchId);
        item.setVisibility(View.VISIBLE);
        item.setTextColor(colors);
        return item;
    }

}
