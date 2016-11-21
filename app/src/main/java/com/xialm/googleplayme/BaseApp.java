package com.xialm.googleplayme;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.utils.FileUtils;
import com.xialm.googleplayme.utils.UIUtils;

/**
 * Created by Xialm on 2016/10/23.
 */

public class BaseApp extends Application {
    public static Context instance; // 不能在这里做初始化,要在onCreate()方法中初始化,界面打开后

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        // 修改Picasso默认缓存图片目录
        Picasso picasso=new Picasso.Builder(UIUtils.getContext())
                .debugging(true)
                .downloader(new OkHttpDownloader(FileUtils.getIcon()))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * 对外暴露的上下文的方法
     * @return 上下文
     */
    public static Context getInstance() {
        return instance;
    }

}
