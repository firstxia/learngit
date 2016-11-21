package com.xialm.googleplayme.utils;

import android.support.v4.app.Fragment;

import com.xialm.googleplayme.fragment.AppFragment;
import com.xialm.googleplayme.fragment.BaseFragment;
import com.xialm.googleplayme.fragment.CategoryFragment;
import com.xialm.googleplayme.fragment.GameFragment;
import com.xialm.googleplayme.fragment.HomeFragment;
import com.xialm.googleplayme.fragment.SubjectFragment;
import com.xialm.googleplayme.fragment.TopFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment的工厂,用于生产fragment
 * Created by Xialm on 2016/10/23.
 * 工厂模式:
 *  1.静态工厂
 *  2.简单工程模式
 *  3.抽象工厂
 */

public class FragmentFactory {
    private static Map<Integer,BaseFragment> fragments=new HashMap<>();
    /**
     * 根据位置,生产fragment
     *      将每次创建的Fragment对象,先放到缓存中,下次在使用的时候,先在缓存中取,如果缓存中存在,
     *      就使用缓存中的对象;如果缓存中不存在,就直接创建新的对象,再次添加到缓存中;
     * @param position
     * @return
     */
    public static BaseFragment createFragment(int position) {
        BaseFragment currentFragment = fragments.get(position); // 从缓存中获取
        if (currentFragment == null) {  // 如果缓存中没有,创建新的对象,并添加到缓存中
            switch (position) {
                case 0:
                    currentFragment = new HomeFragment();
                    break;
                case 1:
                    currentFragment = new AppFragment();
                    break;
                case 2:
                    currentFragment = new GameFragment();
                    break;
                case 3:
                    currentFragment = new SubjectFragment();
                    break;
                case 4:
                    currentFragment = new CategoryFragment();
                    break;
                case 5:
                    currentFragment = new TopFragment();
                    break;
            }
            if (currentFragment != null) {
                // 不为null,缓存下来
                fragments.put(position,currentFragment);
            }
        }
        return currentFragment;
    }
}
