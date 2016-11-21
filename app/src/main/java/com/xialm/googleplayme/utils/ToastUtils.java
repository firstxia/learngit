package com.xialm.googleplayme.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类:静态吐司
 * Created by Xialm on 2016/10/22.
 */

public class ToastUtils {

    private static Toast toast;

    /**
     * 静态吐司
     * @param context
     * @param str 提示的内容
     */
    public static void showToast(Context context, String str) {
        // 第一次创建或者是吐司消失时,toast就等于null了
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

    public static void showToast(String text) {
        showToast(UIUtils.getContext(), text);
    }
}
