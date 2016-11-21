package com.xialm.googleplayme.bean;

import static android.R.attr.name;

/**
 * Created by Xialm on 2016/11/9.
 */

public class UserInfo {
    //    name:'传智黄盖',email:'huanggai@itcast.cn',url:'image/user.png'
    public String name;
    public String email;
    public String url;

    public UserInfo(String name, String email, String url) {
        this.name = name;
        this.email = email;
        this.url = url;
    }
}
