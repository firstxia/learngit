package com.xialm.googleplayme.bean;

/**
 * SubjectFragment对应的javaBean
 * Created by Xialm on 2016/11/2.
 */

public class SubjectInfo {
    /*{
        des:'一周新锐游戏精选',
        url:'image/recommend_01.jpg'
    }*/
    public String des;
    public String url;

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
