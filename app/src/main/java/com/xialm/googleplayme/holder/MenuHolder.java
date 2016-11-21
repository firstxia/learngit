package com.xialm.googleplayme.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xialm.googleplayme.R;
import com.xialm.googleplayme.bean.UserInfo;
import com.xialm.googleplayme.protocol.UserProtocol;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.ThreadUtils;
import com.xialm.googleplayme.utils.ToastUtils;
import com.xialm.googleplayme.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Xialm on 2016/11/9.
 */
public class MenuHolder extends BaseViewHolder<UserInfo> {
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.user_email)
    TextView userEmail;
    @InjectView(R.id.photo_layout)
    RelativeLayout photoLayout;
    @InjectView(R.id.home_layout)
    RelativeLayout homeLayout;
    @InjectView(R.id.setting_layout)
    RelativeLayout settingLayout;
    @InjectView(R.id.theme_layout)
    RelativeLayout themeLayout;
    @InjectView(R.id.scans_layout)
    RelativeLayout scansLayout;
    @InjectView(R.id.feedback_layout)
    RelativeLayout feedbackLayout;
    @InjectView(R.id.updates_layout)
    RelativeLayout updatesLayout;
    @InjectView(R.id.about_layout)
    RelativeLayout aboutLayout;
    @InjectView(R.id.exit_layout)
    RelativeLayout exitLayout;
    @InjectView(R.id.image_photo)
    ImageView imagePhoto;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.holder_menu);
        //添加注入
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(UserInfo userInfo) {
        userName.setText(userInfo.name);
        userEmail.setText(userInfo.email);
        Picasso.with(UIUtils.getContext())
                .load(HttpHelper.BASEURL + "/image?name=" + userInfo.url)
                .into(imagePhoto);
    }

    @OnClick({R.id.photo_layout, R.id.home_layout, R.id.setting_layout, R.id.theme_layout, R.id.scans_layout, R.id.feedback_layout, R.id.updates_layout, R.id.about_layout, R.id.exit_layout})
    public void onClick(View view) {
        String text = null;
        switch (view.getId()) {
            case R.id.photo_layout:
                text = "登录";
                login();
                break;
            case R.id.home_layout:
                text = "首页";
                break;
            case R.id.setting_layout:
                text = "设置";
                break;
            case R.id.theme_layout:
                text = "主题";
                break;
            case R.id.scans_layout:
                text = "安装包管理";
                break;
            case R.id.feedback_layout:
                text = "反馈";
                break;
            case R.id.updates_layout:
                text = "检查更新";
                break;
            case R.id.about_layout:
                text = "关于";
                break;
            case R.id.exit_layout:
                text = "退出";
                break;
        }
        ToastUtils.showToast(text);
    }

    private void login() {
        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                UserProtocol userProtocol = new UserProtocol();
                final UserInfo userInfo = userProtocol.load(0);// 是不需要参数的,随便传递一个参数就可以了
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(userInfo); //一旦调用setData()方法,就会自动调用refreshView()方法
                    }
                });
            }
        });
    }
}
