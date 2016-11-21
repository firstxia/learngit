package com.xialm.googleplayme.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.xialm.googleplayme.DetailsActivity;
import com.xialm.googleplayme.adapter.DefaultListAdapter;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.holder.HomePictureHolder;
import com.xialm.googleplayme.protocol.HomeProtocol;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.BaseListView;

import java.util.List;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 * fragment生命周期中:onCreateView()-->onActivityCreated()
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfos> appInfoses;
    private HomeProtocol homeProtocol;
    private List<String> pictures;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();// 手动调用;解决首页第一次打开不能自动请求网络问题
    }

    @Override
    protected int load() {
        // 1.请求服务器数据  2.将请求回来的数据缓存到本地   3.复用缓存  4.解析
//        HomeProtocol homeProtocol=new HomeProtocol();
        homeProtocol = new HomeProtocol();
        appInfoses = homeProtocol.load(0);
//        return LoadingPage.STATUS_SUCCESS;
        pictures = homeProtocol.getPictures();
        return checkDatas(appInfoses);
    }

    @Override
    protected View createSuccessView() {
        // 万能刷新
        SwipeRefreshLayout refreshLayout = new SwipeRefreshLayout(UIUtils.getContext());
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        ListView listView = new BaseListView(UIUtils.getContext());
        refreshLayout.addView(listView);

        // 取消万能刷新
//        refreshLayout.setRefreshing(false);


        // 给ListView添加轮播图头布局
        HomePictureHolder homePictureHolder = new HomePictureHolder(); //一调用构造,就会自动调用initView()
        homePictureHolder.setData(pictures); // 一调用setData()方法,就会自动调用refreshView()方法
        listView.addHeaderView(homePictureHolder.getContentView());

        listView.setAdapter(new DefaultListAdapter(appInfoses, listView) {
//            @Override
//            protected void onInnerItemClick() {
////                Intent intent = new Intent(UIUtils.getContext(), DetailsActivity.class);
////                startActivity(intent);
//            }

            @Override
            protected List<AppInfos> loadMore() {
                homeProtocol = new HomeProtocol();
                List<AppInfos> newDatas = homeProtocol.load(datas.size());
                return newDatas;
            }
        });
//        return listView;
        return refreshLayout;
    }
}

//    private class MyAdapter extends DefaultBaseAdapter<AppInfos> {
//
//        public MyAdapter(List<AppInfos> datas) {
//            super(datas);
//        }
//
//        @Override
//        protected BaseViewHolder getHolder() {
//            return new HomeViewHolder();
//        }
//    }

//    public class HomeViewHolder extends BaseViewHolder<AppInfos> {
//            public View contentView;
//            public ImageView item_icon;
//            public FrameLayout action_progress;
//            public TextView action_txt;
//            public TextView item_title;
//            public RatingBar item_rating;
//            public TextView item_size;
//            public TextView item_bottom;
//            private AppInfos appInfos;
//
//            @Override
//            protected View initView() {
//                View contentView = UIUtils.inflate(R.layout.item_appinfo);
//                this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
//                this.action_progress = (FrameLayout) contentView.findViewById(R.id.action_progress);
//                this.action_txt = (TextView) contentView.findViewById(R.id.action_txt);
//                this.item_title = (TextView) contentView.findViewById(R.id.item_title);
//                this.item_rating = (RatingBar) contentView.findViewById(R.id.item_rating);
//                this.item_size = (TextView) contentView.findViewById(R.id.item_size);
//                this.item_bottom = (TextView) contentView.findViewById(R.id.item_bottom);
//                return contentView;
//            }
//
//            @Override
//            protected void refreshView(AppInfos appInfos) {
//                this.item_title.setText(appInfos.name);
//                this.item_rating.setRating((float) appInfos.stars);
//                this.item_size.setText(Formatter.formatFileSize(UIUtils.getContext(), appInfos.size));
//                this.item_bottom.setText(appInfos.des);
//
//                // 使用Picasso显示图片
//                Picasso.with(UIUtils.getContext())
//                        .load(HttpHelper.BASEURL + "/image?name=" + appInfos.iconUrl)
//                        .into(this.item_icon);
//            }
//        }
//    }

