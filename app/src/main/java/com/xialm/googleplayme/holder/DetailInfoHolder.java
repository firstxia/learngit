package com.xialm.googleplayme.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Xialm on 2016/11/12.
 */
public class DetailInfoHolder extends BaseViewHolder<AppInfos> {


    @InjectView(R.id.item_icon)
    ImageView itemIcon;
    @InjectView(R.id.item_title)
    TextView itemTitle;
    @InjectView(R.id.item_rating)
    RatingBar itemRating;
    @InjectView(R.id.item_download)
    TextView itemDownload;
    @InjectView(R.id.item_version)
    TextView itemVersion;
    @InjectView(R.id.item_date)
    TextView itemDate;
    @InjectView(R.id.item_size)
    TextView itemSize;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_info);
        // 手动加入注入
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {
        itemTitle.setText(appInfos.name);
        itemRating.setRating((float) appInfos.stars);
        Picasso.with(UIUtils.getContext())
                .load(HttpHelper.BASEURL + "/image?name=" + appInfos.iconUrl)
                .placeholder(R.mipmap.ic_default)
                .into(itemIcon);

        itemDownload.setText(String.format(UIUtils.getStringFromResId(R.string.txt_download_num), appInfos.downloadNum));
        itemVersion.setText(String.format(UIUtils.getStringFromResId(R.string.txt_version), appInfos.version));
        itemDate.setText(String.format(UIUtils.getStringFromResId(R.string.txt_date), appInfos.date));
        itemSize.setText(String.format(UIUtils.getStringFromResId(R.id.txt_size), Formatter.formatFileSize(UIUtils.getContext(), appInfos.size)));
    }
}
