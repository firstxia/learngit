package com.xialm.googleplayme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xialm.googleplayme.R;

/**
 * 具有固定宽高比例的布局:
 * 如服务器返回的图片大小不一,但是我们又要展示,就可以使用该布局,包裹ImageView,让ImageView填充该布局即可
 * Created by Xialm on 2016/11/14.
 */

public class RatioLayout extends FrameLayout {
    private float radio = 2.43f;  //  宽/高

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义的属性,解析   参数二:要一个数组,已经把我们自定义的属性自动转换为一个数组了
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        radio = typedArray.getFloat(R.styleable.RatioLayout_ratio, radio);  //参数二:找不到取默认值
        typedArray.recycle();  // 一定要释放资源
    }

    /**
     * 重新制定宽高测量规则
     *
     * @param widthMeasureSpec  宽的测量规则
     * @param heightMeasureSpec 高的测量规则
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 如果宽是精确模式,高不是精确模式,用宽测量高
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            heightSize = (int) (widthSize * 1.0 / radio + 0.5);  // 6.2-->6   6.6-->7
        } else if (heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY) {
            // 如果高是精确模式,宽不是精确模式,用高测量宽
            widthSize = (int) (heightSize * radio + 0.5);
        }

        // 重新制定宽高的测量规则
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
