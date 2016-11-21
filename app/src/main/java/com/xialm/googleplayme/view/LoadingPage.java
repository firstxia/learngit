package com.xialm.googleplayme.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xialm.googleplayme.R;
import com.xialm.googleplayme.utils.ThreadUtils;
import com.xialm.googleplayme.utils.UIUtils;

import static android.content.pm.PackageInstaller.STATUS_SUCCESS;
import static android.provider.ContactsContract.ProviderStatus.STATUS_EMPTY;

/**
 * Created by Xialm on 2016/10/29.
 * 用户拆分BaseFragment,把与界面相关的代码抽取到这里
 */

public abstract class LoadingPage extends FrameLayout {
    // 定义的几种界面
    private View loadingView; //加载中界面
    private View errorView; // 加载失败界面
    private View emptyView; // 加载数据为null界面
    private View successView; // 加载数据成功界面

    // 定义的几种状态
    public static final int STATUS_LOADING = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_SUCCESS = 3;
    public static final int STATUS_UNKNOWN = 4;
    // 当前的状态
    public int status = STATUS_LOADING;

    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 将几种不同的界面,添加到帧布局中
        init();
    }

    /**
     * 将几种不同的界面添加到帧布局中
     */
    private void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();
            // ViewGroup.LayoutParams.MATCH_PARENT==-1
            this.addView(loadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        }

        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        }
        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        }

        // 成功界面,请求服务器数据成功之后,在添加

        showPage(); //根据状态,控制几种不同的界面的显示与隐藏
    }

    /**
     * 创建一个加载中的界面
     *
     * @return loadingView
     */
    private View createLoadingView() {
//        View.inflate(UIUtils.getContext(), R.layout.page_loading, null);
        View loadingView = UIUtils.inflate(R.layout.page_loading);
        return loadingView;
    }

    /**
     * 创建一个请求失败的界面
     *
     * @return errorView
     */
    private View createErrorView() {
        View errorView = UIUtils.inflate(R.layout.page_error);
        errorView.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                show();// 重新请求数据
            }
        });
        return errorView;
    }

    /**
     * 创建一个请求成功,数据为null的界面
     *
     * @return emptyView
     */
    private View createEmptyView() {
        View emptyView = UIUtils.inflate(R.layout.page_empty);
        return emptyView;
    }

    /**
     * 根据状态,控制几种不同的界面的显示与隐藏
     */
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(status == STATUS_LOADING || status == STATUS_UNKNOWN ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(status == STATUS_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(status == STATUS_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        // 请求服务器成功, 创建成功界面,添加到FrameLayout中
        if (status == STATUS_SUCCESS) {
            // 请求成功
            if (successView == null) {
                successView = createSuccessView();
                this.addView(successView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
            }
        } else {
            // 请求失败
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 根据服务器返回的状态,切换为不同的界面
     * 服务器返回的状态:
     * STATUS_ERROR=1;
     * STATUS_EMPTY=2;
     * STATUS_SUCCESS=3;
     */
    public void show() {
        /*handler.post(new Runnable() {
            @Override
            public void run() {
                // 子线程请求数据
                status = load();
            }
        });
        // 主线程更新UI;XLN 不能这样做,有可能子线程还没有执行完,就去更新界面了
        showPage();*/

        if (status == STATUS_ERROR) { // 是从错误界面,重新请求服务器数据的
            status=STATUS_LOADING; // 更改状态为加载中
        }
        showPage(); // 状态重置后,切换为对应状态的界面

        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                //模拟网络请求
//                SystemClock.sleep(2000);
                // 子线程请求数据
                status = load();

                // 切换到主线程更新UI
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPage();
                    }
                });
            }
        });
    }

    /**
     * 联网请求服务器,返回一个状态
     */
    protected abstract int load();

    /**
     * 创建成功界面
     */
    protected abstract View createSuccessView();
}
