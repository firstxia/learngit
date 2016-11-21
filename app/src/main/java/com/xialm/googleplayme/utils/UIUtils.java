package com.xialm.googleplayme.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.xialm.googleplayme.BaseActivity;
import com.xialm.googleplayme.BaseApp;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Xialm on 2016/10/23.
 */
public class UIUtils {
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 根据资源id,获取一个字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取资源
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public static Context getContext() {
        return BaseApp.getInstance();
    }

    /**
     * 将一个布局xml文件,转化成一个View对象,并返回
     *
     * @param resource 布局资源
     * @return 返回一个view
     */
    public static View inflate(int resource) {
        return View.inflate(UIUtils.getContext(), resource, null);
    }

    public static int getDimen(int dimenId) {
        return getResources().getDimensionPixelSize(dimenId);
    }

    /**
     * 开启一个新的activity
     *
     * @param intent
     */
    public static void startActivity(Intent intent) {
        if (BaseActivity.bActivity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseActivity.bActivity.startActivity(intent);
        } else {
            BaseActivity.bActivity.startActivity(intent);
        }
    }

    public static String getStringFromResId(int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 根据id获取一个drawable对象
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }
}
