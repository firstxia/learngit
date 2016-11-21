package com.xialm.googleplayme.holder;


import android.view.View;

/**
 * ViewHolder的基类
 * Created by Xialm on 2016/11/3.
 */

public abstract class BaseViewHolder<Data> {

    public View contentView;
    protected Data data;

    public BaseViewHolder() {
        this.contentView = initView();
        contentView.setTag(this);
    }

    public void setData(Data data) {
        this.data = data;
        refreshView(data);
    }

    /**
     * 返回一个view对象
     * @return getView()方法的返回值
     */
    public View getContentView() {
        return contentView;
    }

    public Data getData() {
        return data;
    }

    /**
     * 1.初始化View,将一个xml文件转化为一个view
     * 2.查找控件,findViewById
     * @return 将布局View返回
     */
    protected abstract View initView();

    /**
     * 自动更新界面:给View赋值
     * @param data 一个条目的javaBean
     */
    protected abstract void refreshView(Data data);
}
