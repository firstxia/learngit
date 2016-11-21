package com.xialm.googleplayme.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义的,已实现无限轮播的ViewPager
 * Created by Xialm on 2016/11/8.
 */

public class RecyclerViewPager extends ViewPager {
    public RecyclerViewPager(Context context) {
        super(context);
    }

    public RecyclerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        // 包装
        InnerOnPageChangeListener innerOnPageChangeListener = new InnerOnPageChangeListener(listener);
        super.addOnPageChangeListener(innerOnPageChangeListener); //使用包装之后的innerOnPageChangeListener
    }

    public class InnerOnPageChangeListener implements OnPageChangeListener {

        private int position;
        private OnPageChangeListener listener;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (listener != null) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            this.position = position;
            if (listener != null) {
                listener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                // 空闲状态时,进行瞧瞧的切换
                //  if (position == RecyclerViewPager.this.getAdapter().getCount() - 1) { //其实可以用下句代替
                if (position == getAdapter().getCount() - 1) {
                    setCurrentItem(1, false); //参数2:false不让平滑滑动,立即悄悄切换;true平滑滑动
                } else if (position == 0) {
                    setCurrentItem(getAdapter().getCount() - 2, false);
                }
            }
            if (listener != null) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        // 包装
        InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(adapter);

        // 设置适配器之前,调用界面切换的监听
        addOnPageChangeListener(null);

        super.setAdapter(innerPagerAdapter); //使用包装之后的adapter

        // 设置完适配器之后调用
        setCurrentItem(1); // 让当前ViewPager正常显示第一个界面(解决第一次不能向右滑动的bug)
        //开启自动轮播
        startScroll();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            startScroll();
        }
    };

    private void startScroll() {
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    private void stopScroll() {
        handler.removeMessages(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL: //手指滑动到外部,取消事件
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
        }
        return super.onTouchEvent(ev);
    }

    public class InnerPagerAdapter extends PagerAdapter {
        private PagerAdapter adapter;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getCount() { //ViewPager的界面个数
            return adapter.getCount() + 2; //新增2个界面 [ABCD]-->[DABCDA]
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return adapter.isViewFromObject(view, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 现在的position就是 [DABCDA] 中的position,我们需要重新矫正位置
            if (position == 0) {
                // 第一个元素D,找到在原来集合中的索引
//                position = urls.size() - 1;
                position = adapter.getCount() - 1;
            } else if (position == getCount() - 1) {
                // 最后一个元素A,找到在原来集合中的索引
                position = 0;
            } else {
                // 找到其它界面的索引
                position -= 1;
            }

            return adapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
//            container.removeView((View) object);
            adapter.destroyItem(container, position, object);
        }
    }
}
