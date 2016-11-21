package com.xialm.googleplayme;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xialm.googleplayme.fragment.DetailsFragment;

import static android.os.Build.VERSION_CODES.N;

/**
 * 商品详情--对应的界面
 */
public class DetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    private String packageName;

    @Override
    protected void init() {
        super.init();
        packageName = getIntent().getStringExtra("packageName");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_details);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageName);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        // 替换ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        // 显示返回箭头
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
