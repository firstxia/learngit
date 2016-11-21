package com.xialm.googleplayme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.utils.ViewUtils;
import com.xialm.googleplayme.view.LoadingPage;

import java.util.List;

/**
 * 所有Fragment的基类
 * 该类中只保留与业务相关的代码,与界面相关的代码抽取到LoadingPage中
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    private LoadingPage loadingPage;

    public BaseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 解决ViewPager的缓存功能,带来的bug,状态为成员变量,不会重新创建
        if (loadingPage == null) {
            // 控件中的上下文一般选用最长生命周期的上下文
            loadingPage = new LoadingPage(UIUtils.getContext()) {
                @Override
                protected int load() {
                    return BaseFragment.this.load();
                }

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };
//            init(); //将几种不同的界面添加到帧布局中
        }

//        showPage(); //根据状态,控制几种不同的界面的显示与隐藏
//        show(); //根据服务器返回的状态,切换为不同的界面 (在界面一打开时,就在onCreate的ViewPager的界面切换中请求数据了)
        return loadingPage;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 界面销毁时,移除子view(一个子View只能有一个爹)
        ViewUtils.removeViewFromParent(loadingPage);
    }

    /**
     * 联网请求服务器,返回一个状态
     */
    protected abstract int load();

    /**
     * 根据从服务器返回的数据,返回对应的状态
     *  数据校验:List<?> datas 中?表示只校验集合,不校验集合中数据类型
     *  数据校验:List<? extends Object> datas 中?表示校验所有泛型继承自Object的集合
     * @param datas
     * @return
     */
    protected int checkDatas(List<? extends Object> datas) {
        if (datas == null) {
            return LoadingPage.STATUS_ERROR; //失败
        } else {
            if (datas.size() == 0) {
                return LoadingPage.STATUS_EMPTY; //为null
            } else {
                return LoadingPage.STATUS_SUCCESS; //成功
            }
        }
    }

    /**
     * 创建成功界面(每个成功界面都长得不一样)
     */
    protected abstract View createSuccessView();

    /**
     * 开始请求服务器数据
     * 根据状态,控制几种不同的界面的显示与隐藏(界面相关)
     */
    public void show() {
        if (loadingPage != null) {
             loadingPage.show();
        }
    }

}
