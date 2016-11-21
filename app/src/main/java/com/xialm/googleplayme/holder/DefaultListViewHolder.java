package com.xialm.googleplayme.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.UIUtils;

/**
 * 抽取的VIewHolder:
 * 因为首页,应用和游戏界面是一模一样,只是数据不一样,所以就把VIewHolder又抽取了一次(原来为首页的VIewHolder)
 */
public class DefaultListViewHolder extends BaseViewHolder<AppInfos> {
    public View contentView;
    public ImageView item_icon;
    public FrameLayout action_progress;
    public TextView action_txt;
    public TextView item_title;
    public RatingBar item_rating;
    public TextView item_size;
    public TextView item_bottom;
    private AppInfos appInfos;

    @Override
    protected View initView() {
        View contentView = UIUtils.inflate(R.layout.item_appinfo);
        this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
        this.action_progress = (FrameLayout) contentView.findViewById(R.id.action_progress);
        this.action_txt = (TextView) contentView.findViewById(R.id.action_txt);
        this.item_title = (TextView) contentView.findViewById(R.id.item_title);
        this.item_rating = (RatingBar) contentView.findViewById(R.id.item_rating);
        this.item_size = (TextView) contentView.findViewById(R.id.item_size);
        this.item_bottom = (TextView) contentView.findViewById(R.id.item_bottom);
        return contentView;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {
        this.item_title.setText(appInfos.name);
        this.item_rating.setRating((float) appInfos.stars);
        this.item_size.setText(Formatter.formatFileSize(UIUtils.getContext(), appInfos.size));
        this.item_bottom.setText(appInfos.des);

        // 使用Picasso显示图片
        Picasso.with(UIUtils.getContext())
                .load(HttpHelper.BASEURL + "/image?name=" + appInfos.iconUrl)
                .into(this.item_icon);
    }

}