package com.xialm.googleplayme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xialm.googleplayme.fragment.BaseFragment;
import com.xialm.googleplayme.holder.MenuHolder;
import com.xialm.googleplayme.utils.FragmentFactory;
import com.xialm.googleplayme.utils.LogUtil;
import com.xialm.googleplayme.utils.ToastUtils;
import com.xialm.googleplayme.utils.UIUtils;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private PagerTabStrip pagerTabStrip;
    private Toolbar toolbar;
    /**tab标题的数据源*/
    private String[] tabNames;
    private FrameLayout flMenu;

    @Override
    protected void init() {
        super.init();
        // 初始化标题数据源
        tabNames = UIUtils.getStringArray(R.array.tab_names);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // 获取PagerTabStrip对象,并设置下划线(指示线)的颜色
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        pagerTabStrip.setTabIndicatorColorResource(R.color.indicatorcolor);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        // 界面滑动时,(如果是错误界面)重新请求数据
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 根据位置获取一个fragment,可以通过Fragment的工厂实现
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.show(); // 找到对应的界面,重新请求数据
            }
        });

        // 测试打印日志
        LogUtil.d("%s","我是Debug级别的日志,能看到我吗?");
        LogUtil.e("%s","我是Error级别的日志,能看到我吗?");
        LogUtil.v("%s","我是最低级别的日志,能看到么?");

        // 菜单
        flMenu = (FrameLayout) findViewById(R.id.fl_menu);
        MenuHolder menuHolder = new MenuHolder();  //一旦调用构造,就自动调用initView()
//        menuHolder.setData();
        // 这里可以把用户信息,保存到一个地方,下次再进来时,就取出来,设置进去
        flMenu.addView(menuHolder.getContentView());
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        // 用ToolBar替换ActionBar
        setSupportActionBar(toolbar);
        // 为ActionBar添加tab
        actionBar = getSupportActionBar();
        // 显示ActionBar上的三道杠
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 创建ActionBarDrawerToggle对象,该类是Actionbar与DrawerLayout实现联动的类
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //ActionBarDrawerToggle已经实现DrawerListener接口

        // 让Actionbar与DrawerLayout状态同步--箭头变成三道杠
        actionBarDrawerToggle.syncState();

    }

        private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

            public MyFragmentPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.createFragment(position);
            }

            @Override
            public int getCount() {
                return tabNames.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames[position];
            }
        }

        /**
         * 在这里引用res/menu/xxx.xml文件中的按钮
         *
         * @param menu
         * @return
         */
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main, menu);

            // 修改搜索按钮的点击样式--获取SearchView对象
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
            // 为searchView添加一个点击监听
            searchView.setOnQueryTextListener(this);
            return super.onCreateOptionsMenu(menu);
        }

        /**
         * 响应actionbar上按钮的点击事件
         *
         * @param item
         * @return
         */
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // 处理ActionBar和DrawerLayout 点击的联动
            if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            switch (item.getItemId()) {
                case R.id.action_search:
                    Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:
                    Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        // SearchView添加的搜索的监听
        // 文本内容改变时,调用
        @Override
        public boolean onQueryTextSubmit (String query){
            ToastUtils.showToast(this, query);
            return false; // 返回值true和false一样
        }

        // 修改文本内容,回车时调用
        @Override
        public boolean onQueryTextChange (String newText){
            ToastUtils.showToast(this, newText);
            return false; // 返回值true和false一样
        }
    }
