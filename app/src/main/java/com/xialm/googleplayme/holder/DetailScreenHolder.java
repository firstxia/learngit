package com.xialm.googleplayme.holder;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.UIUtils;

import java.util.List;

/**
 * Created by Xialm on 2016/11/13.
 * 泛型,拿什么数据去显示,泛就写什么;使用图片的url,但是是在AppInfos中
 */
public class DetailScreenHolder extends BaseViewHolder<AppInfos> {
    private ImageView[] ivs; //屏幕截图图片所在的数组,约定好的最多不超多5张图片

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_screen);
        ivs = new ImageView[5];
        ivs[0] = (ImageView) view.findViewById(R.id.screen_1);
        ivs[1] = (ImageView) view.findViewById(R.id.screen_2);
        ivs[2] = (ImageView) view.findViewById(R.id.screen_3);
        ivs[3] = (ImageView) view.findViewById(R.id.screen_4);
        ivs[4] = (ImageView) view.findViewById(R.id.screen_5);
        return view;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {
        List<String> screens = appInfos.screens;
        for (int i = 0; i < 5; i++) { // 0,1,2,3,4
            if (i < screens.size()) {
                // 需要显示的
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + screens.get(i))
                        .into(ivs[i]);
            } else {
                //需要隐藏的
                ivs[i].setVisibility(View.GONE);
            }
        }
    }
}
