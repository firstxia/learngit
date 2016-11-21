package com.xialm.googleplayme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xialm.googleplayme.adapter.DefaultBaseAdapter;
import com.xialm.googleplayme.bean.CategoryInfo;
import com.xialm.googleplayme.holder.BaseViewHolder;
import com.xialm.googleplayme.holder.CategoryContentHolder;
import com.xialm.googleplayme.holder.CategoryTitleHolder;
import com.xialm.googleplayme.protocol.CategoryProtocol;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.BaseListView;
import com.xialm.googleplayme.view.LoadingPage;

import java.util.List;

import static com.xialm.googleplayme.R.id.item_title;

/**
 * 分类
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {

    private static final int ITEM_TITLE = 2;
    private List<CategoryInfo> datas;

    @Override
    protected int load() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        datas = categoryProtocol.load(0);
        return checkDatas(datas); //LoadingPage.STATUS_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new BaseListView(UIUtils.getContext());
        listView.setAdapter(new CategoryAdapter(datas, listView));
        return listView;
    }

    private class CategoryAdapter extends DefaultBaseAdapter<CategoryInfo> {
        private int position;

        public CategoryAdapter(List<CategoryInfo> datas, ListView listView) {
            super(datas, listView);
        }

        /**
         * 获取ListView的行视图的种类数量
         *
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1; // 现在有3种行视图了
        }

        /**
         * 自定义:根据位置返回条目的类型
         *
         * @param position
         * @return
         */
        @Override
        protected int getInnerItemViewType(int position) {
            if (datas.get(position).isTitle) {
                return ITEM_TITLE;
            }
            return super.getInnerItemViewType(position);
        }

        //        /**
//         * 根据当前位置,获取对应的条目的类型
//         *
//         * @param position
//         * @return
//         */
//        @Override
//        public int getItemViewType(int position) {
//            return super.getItemViewType(position) + 1; //0,1,2 我们把标题也作为一个新的holder了,所以+1
//        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            this.position = i;
            return super.getView(i, view, viewGroup);
        }

        @Override
        protected BaseViewHolder<CategoryInfo> getHolder() {
            if (getItemViewType(position) == ITEM_TITLE) {  // 0,1,2
                return new CategoryTitleHolder();
            } else {
                return new CategoryContentHolder();
            }
        }

        @Override
        protected boolean hasMore() {
            return false; //没有更多数据,隐藏加载更多布局,同时不会调用onLoad() 加载更多数据
        }
    }
}
