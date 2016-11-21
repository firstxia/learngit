package com.xialm.googleplayme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * activity的基类
 * 1.将相同的方法抽取出来
 * 2.统一管理所有的activity
 * Created by Xialm on 2016/10/22.
 * 解决线程安全问题:
 *     当界面打开,onCreate()方法还没有走完时,突然调用killAll()方法,杀死所有的activity;
 * 这时候onCreate()方法才走完,集合中就会有一个activity没有退出;如何解决呢?
 *      加同步关键字,在往集合中添加,移除和关闭activity时,加synchronized关键字,达到同步效果
 *
 */

public class BaseActivity extends AppCompatActivity {
    // 静态修饰集合,在每次打开一个新的activity时,不会重新创建该集合--共享资源
//    private static List<BaseActivity> activities = new ArrayList<>(); // 方式一
    private static List<BaseActivity> activities = new CopyOnWriteArrayList<>(); // 方式二
    public static BaseActivity bActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 每当打开一个新的activity时,把当前打开的activity添加到集合中
        synchronized (activities) {
            activities.add(this);
        }
        init();
        initViews();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        bActivity = null;
    }

    /**
     * 初始化所有的控件之前调用的初始化操作
     */
    protected void init() {
    }

    /**
     * 初始化所有的控件
     */
    protected void initViews() {
    }

    /**
     * 初始化ToolBar
     */
    protected void initToolBar() {
    }

    /**
     * 在界面退出时,从集合中删除该activity对象
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (activities) {
            activities.remove(this);
        }
    }

    /**
     * 一键退出时,关闭所有的activity,并杀死当前进程;
     * 我们在遍历的同时,是不允许对集合进行修改的;如何解决呢?有2中解决方式
     * 1.把集合拷贝一份,使用拷贝到集合做删除操作
     * 2.我们把集合使用CopyOnWriteArrayList :该集合允许遍历的同时,进行增删操作
     */
    public void killAll() {
        // 方式一
//        List<BaseActivity> copys;
//        synchronized (activities) {
//            copys = new ArrayList<>(activities);
//        }
//        for (BaseActivity activity: copys) {
//            activity.finish(); // 一旦调用finish(),就会调用onDestory()方法,就会对集合进行修改
//        }

        // 方式二
        for (BaseActivity activity : activities) {
            activity.finish();
        }

        // 自杀,操作,杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
