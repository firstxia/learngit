package com.xialm.googleplayme.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.LogUtil;
import com.xialm.googleplayme.utils.UIUtils;

import java.util.List;

/**
 * Created by Xialm on 2016/11/13.
 */
public class DetailSafeHolder extends BaseViewHolder<AppInfos> implements View.OnClickListener {
    private ImageView[] ivs; //安全扫描结果图片
    private ImageView[] desivs; //安全扫描结果-描述图片
    private TextView[] destvs; //安全扫描结果-描述文字
    private LinearLayout[] deslls; //安全扫描结果-描述信息所在ll
    private LinearLayout safe_content; //描述信息-根布局
    private RelativeLayout safe_layout; //布局根节点
    private Boolean isOpen = false; //默认安全扫描信息-打开
    private ImageView ivArrow;


    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_safe);
        ivs = new ImageView[4]; //安全扫描,约定好,最多四个ImageView
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
        ivs[3] = (ImageView) view.findViewById(R.id.iv_4);

        desivs = new ImageView[4];
        desivs[0] = (ImageView) view.findViewById(R.id.des_iv_1);
        desivs[1] = (ImageView) view.findViewById(R.id.des_iv_2);
        desivs[2] = (ImageView) view.findViewById(R.id.des_iv_3);
        desivs[3] = (ImageView) view.findViewById(R.id.des_iv_4);

        destvs = new TextView[4];
        destvs[0] = (TextView) view.findViewById(R.id.des_tv_1);
        destvs[1] = (TextView) view.findViewById(R.id.des_tv_2);
        destvs[2] = (TextView) view.findViewById(R.id.des_tv_3);
        destvs[3] = (TextView) view.findViewById(R.id.des_tv_4);

        deslls = new LinearLayout[4];
        deslls[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
        deslls[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
        deslls[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
        deslls[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

        safe_content = (LinearLayout) view.findViewById(R.id.safe_content);
        safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
        safe_layout.setOnClickListener(this);

        //箭头 -默认是关闭的
        ivArrow = (ImageView) view.findViewById(R.id.safe_arrow);
        ViewGroup.LayoutParams layoutParams = safe_content.getLayoutParams();
        layoutParams.height = 0;
        safe_content.requestLayout(); //重新测量,重新绘制

        return view;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {
        List<String> safeUrls = appInfos.safeUrls;
        List<String> safeDesUrls = appInfos.safeDesUrls;
        List<String> safeDeses = appInfos.safeDeses;
        List<Integer> safeDesColors = appInfos.safeDesColors;
        for (int i = 0; i < 4; i++) { //0,1,2,3
            if (i < safeUrls.size() && i < safeDesUrls.size() && i < safeDeses.size() && i < safeDesColors.size()) {
                // 要显示的
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + safeUrls.get(i))
                        .into(ivs[i]);
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + safeDesUrls.get(i))
                        .into(desivs[i]);
                destvs[i].setText(safeDeses.get(i));
            } else {
                //要隐藏的
                ivs[i].setVisibility(View.GONE);
                deslls[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int startHeight; //开始高度
        int targetHeight; //目标高度
        if (isOpen) { //当前是打开状态
            isOpen = false;
//            safe_content.setVisibility(View.GONE);
            startHeight = getMeasuredHeight(); //获取高度
            targetHeight = 0;

        } else { //当前是关闭状态
            isOpen = true;
//            safe_content.setVisibility(View.VISIBLE);
            startHeight = 0;
            targetHeight = getMeasuredHeight();
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();  //要与ValueAnimator.ofInt对应()
//                LogUtil.d("%S", "heightValue==" + height);  //放开调试
                ViewGroup.LayoutParams layoutParams = safe_content.getLayoutParams();
                layoutParams.height = height;

                // 更新界面 2种方式
//                safe_content.setLayoutParams(layoutParams); //方式一 重新设置
                // 重新测量,重新绘制  方式二
                safe_content.requestLayout();
            }
        });

        // 属性动画-值动画添加一个完成的监听,用于切换箭头
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isOpen) {
                    ivArrow.setImageResource(R.mipmap.arrow_up);
                } else {
                    ivArrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    /**
     * 获取测量后的高度:重新制定测量规则
     *
     * @return
     */
    private int getMeasuredHeight() {
        /*
        测量规则有三种:  size+mode
            1.View.MeasureSpec.EXACTLY :精确模式:  match_parent或者是100dp都是精确模式
            2.View.MeasureSpec.AT_MOST: 包裹模式 wrap_content
            3.View.MeasureSpec.UNSPECIFIED 未指定
         */
        // 宽的测量规则
        int size = safe_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY);

        //指定高的测量规则--如果是包裹模式,size表示为最大不超过的值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        safe_content.measure(widthMeasureSpec, heightMeasureSpec);

        // 读取控件的高度
        return safe_content.getMeasuredHeight();
    }
}
