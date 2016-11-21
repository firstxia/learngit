package com.xialm.googleplayme.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.UIUtils;

/**
 * Created by Xialm on 2016/11/13.
 */
public class DetailDesHolder extends BaseViewHolder<AppInfos> implements View.OnClickListener {

    private TextView des_content;
    private TextView des_author;
    private ImageView des_arrow;
    private RelativeLayout des_layout; //布局根节点
    boolean isOpen = false; //简介信息,默认打开

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_detail_des);
        des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);
        des_content = (TextView) view.findViewById(R.id.des_content);
        des_author = (TextView) view.findViewById(R.id.des_author);
        des_arrow = (ImageView) view.findViewById(R.id.des_arrow);

        // 给布局根节点添加点击监听:打开或关闭简介信息
        des_layout.setOnClickListener(this);

        // 简介:默认关闭为7行高度
        ViewGroup.LayoutParams lp = des_content.getLayoutParams();
        lp.height = getShortMeasuredHeight();
        des_content.requestLayout();
        return view;
    }

    @Override
    protected void refreshView(AppInfos appInfos) {
        des_content.setText(appInfos.des);
        des_author.setText(String.format(UIUtils.getStringFromResId(R.string.app_detail_author), appInfos.author));
    }

    @Override
    public void onClick(View view) {
        final ScrollView scrollView = getParentScrollView(view);
        int startHeight;
        int targetHeight;
        if (isOpen) {
            //关闭
            isOpen = false;
//            des_content.setVisibility(View.GONE);
            startHeight = getLongMeasuredHeight();
            targetHeight = getShortMeasuredHeight();

        } else {
            //打开
            isOpen = true;
//            des_content.setVisibility(View.VISIBLE);
            startHeight = getShortMeasuredHeight();
            targetHeight = getLongMeasuredHeight();
//            if (scrollView != null) {
//                scrollView.scrollTo(0,scrollView.getMeasuredHeight());
//            }
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, targetHeight);
        // 值动画,值改变的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newHeight = (int) valueAnimator.getAnimatedValue();
//                LogUtil.d("%s","DetailDesHolder::newHeight=="+newHeight);
                ViewGroup.LayoutParams lp = des_content.getLayoutParams();
                lp.height = newHeight;


                // 刷新
//                des_content.setLayoutParams(lp);  //等同于下式
                des_content.requestLayout(); //重新测量,重新绘制
                if (scrollView != null) {
                    scrollView.scrollTo(0, scrollView.getMeasuredHeight());
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isOpen) {
                    des_arrow.setImageResource(R.mipmap.arrow_up);
                } else {
                    des_arrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.setDuration(600);
        valueAnimator.start();
    }

    /**
     * 得到父ScrollView
     * XLN 使用到了递归:递归调用本身是存在问题的
     * 递归是入栈和出栈操作:但是在调用的过程中,可能一直在进栈而没有出栈,如果结构比较复杂,就会出现栈内存溢出
     * 解决方式:  1.使用循环代替  2.使用尾递归调用(返回的公式,不增长)---面试常问递归算法的替代(如何替代)
     * 经典案例:  1.斐波那契函数  2.八皇后问题
     *
     * @param view
     * @return
     */
    private ScrollView getParentScrollView(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ScrollView) {
                return (ScrollView) parent;
            } else {
                return getParentScrollView((View) parent);
            }
        }
        return null;
    }


    /**
     * 获取7行Textview的高度:
     * 创建一个TextView,文字大小要跟布局中文字大小一致  14dp
     *
     * @return
     */
    private int getShortMeasuredHeight() {
        TextView textView = new TextView(UIUtils.getContext());
        // 字体大小设置为dp有2种方式:第一种:px--dp  第二种:使用下面的方式,设置字体大小时,指定单位
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14); //14dp
        textView.setLines(7); //7行

        // 预测量下,7行高度
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(des_content.getMeasuredWidth(), View.MeasureSpec.EXACTLY); //精确模式
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        textView.measure(widthMeasureSpec, heightMeasureSpec);

        return textView.getMeasuredHeight();
    }

    /**
     * 预测量:获取描述文本测量后的高度
     *
     * @return
     */
    private int getLongMeasuredHeight() {
        // 测量规则:精确模式,包裹内容和未知模式   size+mode
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(des_content.getMeasuredWidth(), View.MeasureSpec.EXACTLY); //精确模式
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST); //包裹内容
        des_content.measure(widthMeasureSpec, heightMeasureSpec);

        // 根据新的测量尺子,获取测量后的高度
        return des_content.getMeasuredHeight();
    }
}
