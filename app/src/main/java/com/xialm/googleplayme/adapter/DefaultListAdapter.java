package com.xialm.googleplayme.adapter;

import android.content.Intent;
import android.widget.ListView;

import com.xialm.googleplayme.DetailsActivity;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.holder.BaseViewHolder;
import com.xialm.googleplayme.holder.DefaultListViewHolder;
import com.xialm.googleplayme.utils.UIUtils;

import java.util.List;

/**
 * 再次抽取的Adapter
 * 因为首页,应用和游戏界面是一模一样,只是数据不一样,所以就把Adapter又抽取了一次(原来为首页的Adapter)
 * 该基类抽象,为了不重写loadMore(),让子类去实现
 */
public abstract class DefaultListAdapter extends DefaultBaseAdapter<AppInfos> {

    public DefaultListAdapter(List<AppInfos> datas, ListView listView) {
        super(datas, listView);
    }

    /**
     * 获取界面对应的普通的ViewHolder
     *
     * @return
     */
    @Override
    protected BaseViewHolder getHolder() {
        return new DefaultListViewHolder();
    }

    @Override
    protected void onInnerItemClick(int i) {
        super.onInnerItemClick(i);
        Intent intent = new Intent(UIUtils.getContext(), DetailsActivity.class);
//        UIUtils.getContext().startActivity(intent);
        intent.putExtra("packageName", datas.get(i).packageName);
        UIUtils.startActivity(intent);
    }
}