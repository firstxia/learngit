package com.xialm.googleplayme.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xialm.googleplayme.protocol.TopProtocol;
import com.xialm.googleplayme.utils.DrawableUtils;
import com.xialm.googleplayme.utils.ToastUtils;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * 排行
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends BaseFragment {

    private List<String> datas;

    @Override
    protected int load() {
        TopProtocol topProtocol = new TopProtocol();
        datas = topProtocol.load(0);
        return checkDatas(datas);//LoadingPage.STATUS_ERROR;
    }

    @Override
    protected View createSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
//        LinearLayout layout = new LinearLayout(UIUtils.getContext());
//        layout.setOrientation(LinearLayout.VERTICAL);
        FlowLayout layout = new FlowLayout(UIUtils.getContext());
        //设置padding值
        int paddingValue = UIUtils.dip2px(13);
        layout.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);

        scrollView.addView(layout);

        int textPaddingV = UIUtils.dip2px(4);
        int textPaddingH = UIUtils.dip2px(7);
        int backColor = 0xffcecece; //默认点击的背景颜色


        TextView textView = null;
        Random random = new Random();
        for (int i = 0; i < datas.size(); i++) {
            final String text = datas.get(i);
            textView = new TextView(UIUtils.getContext());
            textView.setTextColor(Color.WHITE);
            int alpha = 255;
            int red = random.nextInt(201) + 30; //取值范围:0-255 为了解决纯黑盒纯白小概率事件,+30
            int green = random.nextInt(201) + 30; //取值: 0-255
            int blue = random.nextInt(201) + 30; //取值: 0-255
//            int color=Color.argb(alpha,red,green,blue);
            int color = Color.rgb(red, green, blue);
            textView.setBackgroundDrawable(DrawableUtils.createStateListDrawable(DrawableUtils.create(backColor), DrawableUtils.create(color)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16); //16dp
            textView.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
            textView.setText(text);
            // textView的宽高都是包裹内容
            layout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2));
            // 让textView响应点击事件:2中方式
            // 1.添加点击事件
            //2.设置textView.setClickable(true);属性
//            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(text);
                }
            });
        }

        return scrollView;
    }
}
