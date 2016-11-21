package com.xialm.googleplayme.holder;

import android.view.View;
import android.widget.TextView;

import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.bean.CategoryInfo;
import com.xialm.googleplayme.utils.UIUtils;

/**
 * Created by Xialm on 2016/11/15.
 */
public class CategoryTitleHolder extends BaseViewHolder<CategoryInfo> {

    private TextView textView;

    @Override
    protected View initView() {
        textView = new TextView(UIUtils.getContext());
        textView.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.grid_item_bg));
//        textView.setBackground(UIUtils.getDrawable(R.drawable.grid_item_bg)); //最低要求版本API16,内部也是调用上面一句代码
        return textView;
    }

    @Override
    protected void refreshView(CategoryInfo categoryInfo) {
        textView.setText(categoryInfo.title);
    }
}
