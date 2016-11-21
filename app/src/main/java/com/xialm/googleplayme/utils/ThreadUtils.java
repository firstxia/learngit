package com.xialm.googleplayme.utils;

import android.os.Handler;

import com.xialm.googleplayme.manager.ThreadPoolManager;

/**
 * 线程工具类:
 *  1.在子线程请求网络
 *  2.切换到主线程执行
 * Created by Xialm on 2016/10/29.
 */

public class ThreadUtils {
    private static Handler handler = new Handler();
    /**
     * 在子线程执行(请求网络等操作)
     * @param runnable
     */
    public static void runOnBackThread(Runnable runnable){
//        new Thread(runnable).start(); // 界面每次切换都要创建一个线程,很浪费;使用线程池代替
        ThreadPoolManager.getInstance().createThreadPool().execute(runnable); // 使用线程池代替
    }

    /**
     * 切换到主线程执行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
