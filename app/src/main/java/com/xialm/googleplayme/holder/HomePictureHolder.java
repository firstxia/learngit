package com.xialm.googleplayme.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.UIUtils;
import com.xialm.googleplayme.view.RecyclerViewPager;

import java.util.List;

import static com.xialm.googleplayme.R.id.imageView;
import static com.xialm.googleplayme.R.id.viewPager;

/**
 * 首页轮播图对应的Holder
 * Created by Xialm on 2016/11/8.
 */
public class HomePictureHolder extends BaseViewHolder<List<String>> {

    private ViewPager viewPager;

    @Override
    protected View initView() {
        FrameLayout frameLayout = new FrameLayout(UIUtils.getContext());
        viewPager = new RecyclerViewPager(UIUtils.getContext());
        frameLayout.addView(viewPager, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getDimen(R.dimen.list_header_height))); // px
        return frameLayout;
    }

    @Override
    protected void refreshView(final List<String> urls) {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return urls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(UIUtils.getContext());
                container.addView(imageView);
                Picasso.with(UIUtils.getContext())
                        .load(HttpHelper.BASEURL + "/image?name=" + urls.get(position))
                        .into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView((View) object);
            }
        });
//        viewPager.setCurrentItem(1); // 让当前ViewPager正常显示第一个界面(解决第一次不能向右滑动的bug)
    }
}
