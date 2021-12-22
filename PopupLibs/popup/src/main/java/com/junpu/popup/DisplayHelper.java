package com.junpu.popup;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayHelper {

    /**
     * 获取 DisplayMetrics
     */
    static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 获取屏幕宽度
     */
    static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }
}
