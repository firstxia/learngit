package com.xialm.googleplayme.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xialm.googleplayme.holder.BaseViewHolder;
import com.xialm.googleplayme.holder.MoreHolder;
import com.xialm.googleplayme.utils.ThreadUtils;
import com.xialm.googleplayme.utils.ToastUtils;

import java.util.List;

/**
 * ListView的适配器:抽取的BaseAdapter的基类
 * Created by Xialm on 2016/11/4.
 */

public abstract class DefaultBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final int ITEM_VIEW_MORE = 0; //加载更多view类型
    private static final int ITEM_VIEW_DEFAULT = 1; //默认的view类型
    protected List<T> datas;
    private ListView listView;
    private MoreHolder moreHolder;

    public DefaultBaseAdapter(List<T> datas, ListView listView) {
        this.datas = datas;
        this.listView = listView;
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 矫正位置
        i -= listView.getHeaderViewsCount();
//        ToastUtils.showToast("position=="+i);
        onInnerItemClick(i);
    }

    /**
     * 让子类选择性的实现条目的点击事件
     *
     * @param i
     */
    protected void onInnerItemClick(int i) {
    }

    ;

    @Override
    public int getCount() {
        return datas.size() + 1; //增加了一个加载更多条目
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 返回ListView条目的类型的个数
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        //  return super.getViewTypeCount()+1;
        return super.getViewTypeCount() + 1; // XLN 坑  在这里我们添加几种类型,就在原来的基础上几
    }

    /**
     * 根据位置,返回ListView对应条目的int值类型
     * 如果getViewTypeCount()方法返回的类型个数为2:则这个方法返回值只能为(0和1)
     * 如果getViewTypeCount()方法返回的类型个数为3:则这个方法返回值为(0,1,2);以此类推
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return ITEM_VIEW_MORE;
        }
        return getInnerItemViewType(position);
    }

    protected int getInnerItemViewType(int position) {
        return ITEM_VIEW_DEFAULT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseViewHolder bvh = null;
        switch (getItemViewType(i)) {
            case ITEM_VIEW_MORE: //加载更多条目
                //如果是最后一个条目,显示加载中MoreHolder
                if (view == null) {
                    bvh = getMoreHolder();
                } else {
                    bvh = (BaseViewHolder) view.getTag();
                }

                break;
//            case ITEM_VIEW_DEFAULT:
            default:  //除了加载更多条目之外,我们都设置为默认
                if (view == null) {
                    bvh = getHolder();
                } else {
                    bvh = (BaseViewHolder) view.getTag();
                }
                T t = datas.get(i);
                bvh.setData(t);
                break;
        }

        return bvh.getContentView();
    }

    /**
     * 请加载更多数据(请求服务器,添加到集合中,刷新)
     */
    public void onLoad() {
        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                final List<T> newDatas = loadMore();
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newDatas == null) {
                            // 说明请求失败了
                            moreHolder.setData(MoreHolder.ERROR);
                        } else {
                            if (newDatas.size() == 0) {
                                // 说明没有更多数据了
                                moreHolder.setData(MoreHolder.HAS_NO_MORE);
                            } else {
                                // 请求更多数据成功
                                moreHolder.setData(MoreHolder.HAS_MORE);
                                datas.addAll(newDatas);
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 加载更多,请求网络数据
     *空实现:需要加载更多数据,就重写该方法即可
     * @return
     */
    protected List<T> loadMore() {
        return null;
    }

    /**
     * 获取加载更多脚布局MoreHolder
     *
     * @return 返回一个MoreHolder
     */
    private BaseViewHolder getMoreHolder() {
        if (moreHolder == null) {
            moreHolder = new MoreHolder(this, hasMore());
        }
        return moreHolder;
    }

    /**
     * 是否有更多数据:默认有更多数据
     *
     * @return
     */
    protected boolean hasMore() {
        return true;
    }

    /**
     * 获取界面对应的VIewHolder
     *
     * @return 返回BaseVIewHolder的实现子类
     */
    protected abstract BaseViewHolder<T> getHolder();

}
