package com.xialm.googleplayme.holder;

import android.view.View;
import android.widget.Button;

import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.ToastUtils;
import com.xialm.googleplayme.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Xialm on 2016/11/14.
 */
public class DetailBottomHolder extends BaseViewHolder<AppInfos> {
    @InjectView(R.id.bottom_favorites)
    Button bottomFavorites;
    @InjectView(R.id.bottom_share)
    Button bottomShare;
    @InjectView(R.id.progress_btn)
    Button progressBtn;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_bottom);
        // 不是在activity中,缺少注入
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {

    }

    @OnClick({R.id.bottom_favorites, R.id.bottom_share, R.id.progress_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_favorites:
                ToastUtils.showToast("收藏");
                break;
            case R.id.bottom_share:
                ToastUtils.showToast("分享");
                break;
            case R.id.progress_btn:
                ToastUtils.showToast("下载");
                break;
        }
    }
}
