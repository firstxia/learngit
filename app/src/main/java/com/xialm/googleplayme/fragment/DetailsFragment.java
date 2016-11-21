package com.xialm.googleplayme.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.holder.DetailBottomHolder;
import com.xialm.googleplayme.holder.DetailDesHolder;
import com.xialm.googleplayme.holder.DetailInfoHolder;
import com.xialm.googleplayme.holder.DetailSafeHolder;
import com.xialm.googleplayme.holder.DetailScreenHolder;
import com.xialm.googleplayme.protocol.DetailProtocol;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.LoadingPage;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Xialm on 2016/11/9.
 */

public class DetailsFragment extends BaseFragment {
    @InjectView(R.id.fl_bottom_layout)
    FrameLayout flBottomLayout;
    @InjectView(R.id.fl_detail_info)
    FrameLayout flDetailInfo;
    @InjectView(R.id.fl_detail_safe)
    FrameLayout flDetailSafe;
    @InjectView(R.id.hsv_detail_screen)
    HorizontalScrollView hsvDetailScreen;
    @InjectView(R.id.fl_detail_des)
    FrameLayout flDetailDes;
    private AppInfos appInfos;
    private String packageName;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show(); //手动调用,从服务器请求网络
        setHasOptionsMenu(true);  // 在Fragment中默认是不允许修改actionbar的,必须加上该方法才会显示
        Bundle bundle = getArguments();
        packageName = bundle.getString("packageName");
    }

    @Override
    protected int load() {
        SystemClock.sleep(2000);
        DetailProtocol detailProtocol = new DetailProtocol(packageName);
        appInfos = detailProtocol.load(0);
        if (appInfos == null) {
            return LoadingPage.STATUS_ERROR;
        } else {
            return LoadingPage.STATUS_SUCCESS;

        }
    }

    @Override
    protected View createSuccessView() {
        View view = UIUtils.inflate(R.layout.fragment_detail);
        // 注册
        ButterKnife.inject(this, view);

        //详情信息
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();  //一旦调用构造就会自动调用initView()方法
        detailInfoHolder.setData(appInfos); //会自动调用refreshView()方法
        flDetailInfo.addView(detailInfoHolder.getContentView());

        // 截图
        DetailScreenHolder detailScreenHolder = new DetailScreenHolder(); //自动调用initView()
        detailScreenHolder.setData(appInfos); //自动调用refreshView()
        hsvDetailScreen.addView(detailScreenHolder.getContentView());

        //安全扫描
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();  //initView()
        detailSafeHolder.setData(appInfos); //refreshView()
        flDetailSafe.addView(detailSafeHolder.getContentView());

        //简介
        DetailDesHolder detailDesHolder = new DetailDesHolder(); //调用initView()
        detailDesHolder.setData(appInfos);
        flDetailDes.addView(detailDesHolder.getContentView());

        //下载
        DetailBottomHolder detailBottomHolder = new DetailBottomHolder();
        detailBottomHolder.setData(appInfos);
        flBottomLayout.addView(detailBottomHolder.getContentView());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

}
