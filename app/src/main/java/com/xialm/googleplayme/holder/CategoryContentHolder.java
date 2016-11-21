package com.xialm.googleplayme.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.CategoryInfo;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.ToastUtils;
import com.xialm.googleplayme.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static java.lang.System.load;

/**
 * Created by Xialm on 2016/11/14.
 */

public class CategoryContentHolder extends BaseViewHolder<CategoryInfo> {

    private ImageView[] ivs;
    private TextView[] tvs;
    private RelativeLayout[] rls;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_category_content);

        ivs = new ImageView[3];
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);

        tvs = new TextView[3];
        tvs[0] = (TextView) view.findViewById(R.id.tv_1);
        tvs[1] = (TextView) view.findViewById(R.id.tv_2);
        tvs[2] = (TextView) view.findViewById(R.id.tv_3);

        rls = new RelativeLayout[3];
        rls[0] = (RelativeLayout) view.findViewById(R.id.rl_1);
        rls[1] = (RelativeLayout) view.findViewById(R.id.rl_2);
        rls[2] = (RelativeLayout) view.findViewById(R.id.rl_3);
        return view;
    }

    @Override
    protected void refreshView(final CategoryInfo categoryInfo) {
        if (!TextUtils.isEmpty(categoryInfo.name1) && !TextUtils.isEmpty(categoryInfo.url1)) {
            //显示
            Picasso.with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url1)
                    .placeholder(R.mipmap.ic_default)  //设置占位图
                    .into(ivs[0]);
            tvs[0].setText(categoryInfo.name1);
            rls[0].setVisibility(View.VISIBLE); //解决复用的bug
            rls[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(categoryInfo.name1);
                }
            });
        } else {
            //隐藏
            rls[0].setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(categoryInfo.name2) && !TextUtils.isEmpty(categoryInfo.url2)) {
            //显示
            Picasso.with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url2)
                    .placeholder(R.mipmap.ic_default)   //设置占位图
                    .into(ivs[1]);
            tvs[1].setText(categoryInfo.name2);
            rls[1].setVisibility(View.VISIBLE);
            rls[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(categoryInfo.name2);
                }
            });
        } else {
            //隐藏
            rls[1].setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(categoryInfo.name3) && !TextUtils.isEmpty(categoryInfo.url3)) {
            //显示
            Picasso.with(UIUtils.getContext())
                    .load(HttpHelper.BASEURL + "/image?name=" + categoryInfo.url3)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivs[2]);
            tvs[2].setText(categoryInfo.name3);
            rls[2].setVisibility(View.VISIBLE);
            rls[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(categoryInfo.name3);
                }
            });
        } else {
            //隐藏
            rls[2].setVisibility(View.INVISIBLE);
        }
    }
}
