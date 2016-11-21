package com.xialm.googleplayme.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.adapter.DefaultBaseAdapter;
import com.xialm.googleplayme.bean.SubjectInfo;
import com.xialm.googleplayme.holder.BaseViewHolder;
import com.xialm.googleplayme.protocol.SubjectProtocol;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.BaseListView;

import java.util.List;

/**
 * 专题
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfo> subjectInfos;
    private ListView listView;

    @Override
    protected int load() {
        // 1.请求服务器数据    2.缓存    3.复用缓存  4.解析
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        subjectInfos = subjectProtocol.load(0);
        return checkDatas(subjectInfos);
    }

    @Override
    protected View createSuccessView() {
        listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new SubjectAdapter(subjectInfos));
        return listView;
    }

    private class SubjectAdapter extends DefaultBaseAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas) {
            super(datas, listView);
        }

        @Override
        protected List<SubjectInfo> loadMore() {
            SubjectProtocol subjectProtocol = new SubjectProtocol();
            List<SubjectInfo> newDatas = subjectProtocol.load(datas.size());
            return newDatas;
        }

        @Override
        protected BaseViewHolder getHolder() {
            return new SubjectViewHolder();
        }
    }

    public class SubjectViewHolder extends BaseViewHolder<SubjectInfo> {
        public View contentView;
        public ImageView item_icon;
        public TextView item_txt;
        private SubjectInfo subjectInfo;

        @Override
        protected View initView() {
            View contentView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
            this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
            this.item_txt = (TextView) contentView.findViewById(R.id.item_txt);
            return contentView;
        }

        @Override
        protected void refreshView(SubjectInfo subjectInfo) {
            this.item_txt.setText(subjectInfo.des);
            Picasso.with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + subjectInfo.url)
                    .into(this.item_icon);
        }
    }
}
