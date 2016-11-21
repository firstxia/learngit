package com.xialm.googleplayme.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义的,ListView的基类:已经消除分割线和默认的背景色了
 * Created by Xialm on 2016/11/3.
 */

public class BaseListView extends ListView{
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        <ListView
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:cacheColorHint="@android:color/transparent"
//        android:divider="@android:color/transparent"
//        android:listSelector="@android:color/transparent"></ListView>
        // 取消背景
        setCacheColorHint(0x00000000);
        // 取消状态选择器(选择一个条目,整体变黑)
//        setSelector(R.drawable.nothing);
        setSelector(new ColorDrawable(0x00000000)); // 把一个颜色设置为一个drawable对象
        // 取消分割线
        setDivider(new ColorDrawable(0x00000000));
    }
}
