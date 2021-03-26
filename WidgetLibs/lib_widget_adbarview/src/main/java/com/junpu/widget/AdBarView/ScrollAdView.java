package com.junpu.widget.AdBarView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 广告轮播（跟AdView类似，但是AdView不太好用）
 * <p>
 * 使用方法，需要 scrollAd.post { scrollAd.initData(list) }
 * 并且ScrollAdView需要指定高度
 * <p>
 * Created by chengxiaobo on 2017/3/28.
 */

public class ScrollAdView extends FrameLayout {

    private int height;
    private int flag = 1;
    private List<String> stringList;
    private Context context;
    private LinearLayout linearLayoutContent;
    private ObjectAnimator animator;

    private int animationDelay = 3000;
    private int animationDuration = 1000;
    private int textSize = 14;
    private int textColor = 0xffffffff;

    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int TEXT_GRAVITY_LEFT = 0, TEXT_GRAVITY_CENTER = 1, TEXT_GRAVITY_RIGHT = 2;

    private static final int MESSAGE_WHAT = 100;

    public ScrollAdView(Context context) {
        super(context);
        init(context);
    }

    public ScrollAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initConfig(attrs, 0);
    }

    public ScrollAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initConfig(attrs, defStyleAttr);
    }

    private void initConfig(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        animationDelay = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, animationDelay);
        animationDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animationDuration);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = px2sp(textSize);
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT);
        switch (gravityType) {
            case TEXT_GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();
    }

    private void init(Context context) {
        this.context = context;
    }

    public void initData(List<String> adList) {

        if (animator != null) {
            return;
        }

        if (adList == null && adList.size() == 0) {
            return;
        }

        this.removeAllViews();

        height = this.getMeasuredHeight();
        linearLayoutContent = new LinearLayout(context);
        linearLayoutContent.setOrientation(LinearLayout.VERTICAL);
        stringList = adList;

        if (adList.size() == 1) {

            linearLayoutContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

            TextView tv = new TextView(context);
            tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
            tv.setText(stringList.get(0));
            tv.setGravity(gravity);

            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);

            //变成动态
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
            tv.setTextColor(textColor);

            linearLayoutContent.addView(tv);
            this.addView(linearLayoutContent);

        } else {
            linearLayoutContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height * 2));  //!!! 必须设置height ，否则为AT_MOST

            for (int i = 0; i < 2; i++) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
                tv.setText(stringList.get(i));
                tv.setGravity(gravity);

                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);

                //变成动态
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                tv.setTextColor(textColor);

                linearLayoutContent.addView(tv);
            }

            this.addView(linearLayoutContent);

            initAnimation();
        }

    }

    /**
     * 内容变化，更新list
     */
    public void updateContentList(List<String> adList) {
        stringList = adList;
        initData(adList);
    }

    /**
     * 获取当前的stringlist
     */
    public List<String> getStringList() {
        return stringList;
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {

        animator = ObjectAnimator.ofFloat(linearLayoutContent, "translationY", 0, -height);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.sendEmptyMessageDelayed(MESSAGE_WHAT, animationDelay);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });

        startAnimation();

    }

    public void startAnimation() {
        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    public void stopAnimation() {
        if (animator != null) {
            animator.cancel();
        }
        if (handler != null) {
            handler.removeMessages(MESSAGE_WHAT);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            TextView tv = (TextView) linearLayoutContent.getChildAt(0);
            linearLayoutContent.removeView(tv);
            linearLayoutContent.addView(tv);

            if (flag + 1 >= stringList.size()) {
                flag = 0;
            } else {
                flag = flag + 1;
            }
            tv.setText(stringList.get(flag));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            startAnimation();
        }
    };

    // 将px值转换为sp值，保证文字大小不变
    public int px2sp(float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
