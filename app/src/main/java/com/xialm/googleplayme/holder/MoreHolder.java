package com.xialm.googleplayme.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.xialm.googleplayme.R;
import com.xialm.googleplayme.adapter.DefaultBaseAdapter;
import com.xialm.googleplayme.utils.UIUtils;

/**
 * Created by Xialm on 2016/11/6.
 */

public class MoreHolder extends BaseViewHolder<Integer> {
    public static final int HAS_MORE = 0; //有加载更多数据
    public static final int ERROR = 1; // 加载更多数据失败
    public static final int HAS_NO_MORE = 2; //没有更多数据
    private DefaultBaseAdapter adapter;
    private boolean hasMore;
    private RelativeLayout rl_more_loading;
    private RelativeLayout rl_more_error;

    public MoreHolder(DefaultBaseAdapter adapter, boolean hasMore) {

        this.adapter = adapter;
        this.hasMore = hasMore;
        if (!hasMore) {
            // 如果没有加载更多数据   隐藏加载更多布局(调用holder.setData(HAS_NO_MORE) 自动隐藏该布局)
            setData(HAS_NO_MORE);
        }
    }


    @Override
    protected View initView() {
//        View view = View.inflate(UIUtils.getContext(), R.layout.item_more, null);
        View view = UIUtils.inflate(R.layout.item_more);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    @Override
    public View getContentView() {
        // 一旦调用getContentView()方法,就是加载更多条目显示的时候
        if (hasMore) { //有更多数据,再去调用加载
            onLoad();
        }
        return super.getContentView();
    }

    private void onLoad() {
        // 1.请求网络数据 2.追加到集合中  3.适配器刷新 adapter.notifyDateSetChanged()
        // 当前方法做不了,但是DefaultBaseAdapter可以做
        if (adapter != null) {
            adapter.onLoad();
            setData(HAS_MORE);
        }
    }

    @Override
    protected void refreshView(Integer state) {
        if (rl_more_loading != null) {
            rl_more_loading.setVisibility(state == HAS_MORE ? View.VISIBLE : View.GONE);
        }
        if (rl_more_error != null) {
            rl_more_error.setVisibility(state == ERROR ? View.VISIBLE : View.GONE);
        }
    }
}
