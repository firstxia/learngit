package com.xialm.googleplayme.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by Xialm on 2016/10/29.
 */

public class ViewUtils {
    /**
     * 从父View中移除子view(父View与子View断绝关系)
     * @param view 待移除的子view
     */
    public static void removeViewFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();  // 获取父view(ViewParent没有移除子view的功能,ViewGroup有)
            if (parent != null && parent instanceof ViewGroup) { // instanceof 类属于
                ViewGroup viewGroup= (ViewGroup) parent;
                viewGroup.removeView(view);
            }
        }

    }
}
