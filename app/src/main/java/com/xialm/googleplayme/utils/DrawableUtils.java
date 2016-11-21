package com.xialm.googleplayme.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Xialm on 2016/11/17.
 */

public class DrawableUtils {
    /**
     * 返回背景 shapeDrawable
     *
     * @param color
     * @return
     */
    public static GradientDrawable create(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(UIUtils.dip2px(5));  //使用dp适配好,也可以抽取到dimen中
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 创建状态选择器
     *
     * @param pressedDrawable 按压的
     * @param normalDrawable  正常的
     * @return
     */
    public static StateListDrawable createStateListDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        /*
        <item android:drawable="@drawable/btn_pressed" android:state_pressed="true" />
        <item android:drawable="@drawable/btn_normal" />
         */
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        drawable.addState(new int[]{}, normalDrawable);
        return drawable;
    }
}
