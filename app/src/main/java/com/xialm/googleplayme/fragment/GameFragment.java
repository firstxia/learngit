package com.xialm.googleplayme.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.xialm.googleplayme.adapter.DefaultListAdapter;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.protocol.GameProtocol;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.BaseListView;

import java.util.List;

/**
 * 游戏
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends BaseFragment {

    private List<AppInfos> datas;

    @Override
    protected int load() {
        GameProtocol gameProtocol = new GameProtocol();
        datas = gameProtocol.load(0);
        return checkDatas(datas); //LoadingPage.STATUS_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new DefaultListAdapter(datas, listView) {
            @Override
            protected List<AppInfos> loadMore() {
                GameProtocol gameProtocol = new GameProtocol();
                List<AppInfos> newDatas = gameProtocol.load(datas.size());
                return newDatas;
            }
        });
        return listView;
    }
}
