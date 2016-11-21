package com.xialm.googleplayme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xialm.googleplayme.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Xialm on 2016/11/20.
 */

public class FlowLayout extends ViewGroup {
    private static final int HORIZONTAL_SPACING = UIUtils.dip2px(13);
    private static final int VERTICAL_SPACING = UIUtils.dip2px(13);
    private List<Line> lines = new ArrayList<>();
    private Line currentLine;
    private int useWidth; //当前行使用的宽度

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量view,父view是有义务测量所有的子view的
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        currentLine = null;
        useWidth = 0;

        // 如果父View的模式为精确模式,那么子view的测量规则为包裹模式+最大不超过父view的size
        // 如果父view的模式不是精确模式,那么子view的测量规则和父view的测量规则一致
        // 测量规则是由mode+size组成

        // 获取父view的  宽的  测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        // 子view宽的模式
//        int childWidthMode;
//        if (widthMode == MeasureSpec.EXACTLY) {
//            childWidthMode = MeasureSpec.AT_MOST;
//        } else {
//            childWidthMode=widthMode;
//        }
        int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int childHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;

        // 子view宽的测量规则
        int childWidthMeasureSpce = MeasureSpec.makeMeasureSpec(widthSize, childWidthMode);
        // 子view高的测量规则
        int childHeightMeasureSpce = MeasureSpec.makeMeasureSpec(heightSize, childHeightMode);

        // 测量子view
        currentLine = new Line(); //创建一行
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(childWidthMeasureSpce, childHeightMeasureSpce);

            // 读取子view的测量后的宽高
            int childWidth = childView.getMeasuredWidth();
//            int childHeight = childView.getMeasuredHeight();
            useWidth += childWidth;
            if (useWidth <= widthSize) {
                //当前行使用的宽度 小于父view的宽度
                currentLine.addView(childView);
                useWidth += HORIZONTAL_SPACING; //水平间隔
                if (useWidth > widthSize) {
                    // 换行
                    newLine();
                }
            } else {
                // 换行
                newLine();
                useWidth += childWidth;
                currentLine.addView(childView);
            }
        }

        // 判断最后一行,是否包含在行集合中
        if (!lines.contains(currentLine)) {
            lines.add(currentLine);
        }

        // 计算总高度
        // 所有子view的高度
        int totalHeight = 0;
        for (int i = 0; i < lines.size(); i++) {
            //获取每行的高度
            Line line = lines.get(i);
            int lineHeight = line.getHeight();
            totalHeight += lineHeight;

        }
        totalHeight += (lines.size() - 1) * VERTICAL_SPACING; //加上行间距

        // 为了存储当前view 测量过的宽高
        setMeasuredDimension(widthSize + getPaddingLeft() + getPaddingRight(), totalHeight + getPaddingTop() + getPaddingBottom());
    }

    private void newLine() {
        lines.add(currentLine); //添加到集合中
        currentLine = new Line(); //创建新行
        useWidth = 0; //当前行宽度归0
    }

    class Line {
        private List<View> children = new ArrayList<>(); //当前行维护的view
        private int height;
        private int lineWidth; //当前行的行宽

        public void addView(View childView) {
            // 获取最大的高度
            if (height < childView.getMeasuredHeight()) {
                height = childView.getMeasuredHeight();
            }
            children.add(childView);
            lineWidth += childView.getMeasuredWidth();
        }

        /**
         * 获取行高
         *
         * @return
         */
        public int getHeight() {
            return height;
        }

        /**
         * 放置子view的位置
         *
         * @param l
         * @param t
         */
        public void layout(int l, int t) {
            // 获取当前行剩余的空间宽度
            int remaind = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth - (children.size() - 1) * HORIZONTAL_SPACING;
            // 平分剩余宽度
            int r = remaind / children.size();
            for (int i = 0; i < children.size(); i++) {
                View view = children.get(i);
                view.layout(l, t, l + view.getMeasuredWidth() + r, t + view.getMeasuredHeight()); //右边界+平分宽度
                ///////////////////////////////////////////////////////////////////////////
                // 把view. 去掉就是瀑布流效果
                ///////////////////////////////////////////////////////////////////////////
//                view.layout(l, t, l + getMeasuredWidth() + r, t + getMeasuredHeight()); //右边界+平分宽度

                // 放置第二个子view 及下下一个子view
                l += view.getMeasuredWidth();
                l += HORIZONTAL_SPACING;
                l += r;

            }
        }
    }

    /**
     * 放置子view
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 放置行,让行自己在放置子View
        l += getPaddingLeft();
        t += getPaddingTop();
        for (Line line : lines) {
            line.layout(l, t);
            t += line.getHeight(); //第二行,及以下行
            t += VERTICAL_SPACING;
        }
    }


}
