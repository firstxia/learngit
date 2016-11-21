package com.xialm.googleplayme.bean;

import java.util.List;

/**
 * Created by Xialm on 2016/10/31.
 */

public class AppInfos {
    // 公用的数据属性
    public long id;
    public String name;
    public String packageName;
    public String iconUrl;
    public double stars;
    public long size;
    public String downloadUrl;
    public String des;

    ///////////////////////////////////////////////////////////////////////////
    // 详情界面特有的属性
    ///////////////////////////////////////////////////////////////////////////
    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public List<String> screens; //所有的截图信息
    public List<String> safeDeses; //所有安全扫描,描述文本
    public List<String> safeDesUrls; //所有安全扫描,描述图片url
    public List<String> safeUrls; //所有安全扫描,结果的图片url
    public List<Integer> safeDesColors;//所有安全扫描,描述文字的颜色

    public AppInfos(long id, String name, String packageName, String iconUrl, double stars, long size, String downloadUrl, String des) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
    }

    /**
     * 详情Fragment使用的构造
     */
    public AppInfos(long id, String name, String packageName, String iconUrl, double stars, long size, String downloadUrl, String des, String author, String date, String downloadNum, String version, List<String> screens, List<String> safeDeses, List<String> safeDesUrls, List<String> safeUrls, List<Integer> safeDesColors) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
        this.author = author;
        this.date = date;
        this.downloadNum = downloadNum;
        this.version = version;
        this.screens = screens;
        this.safeDeses = safeDeses;
        this.safeDesUrls = safeDesUrls;
        this.safeUrls = safeUrls;
        this.safeDesColors = safeDesColors;
    }

    @Override
    public String toString() {
        return "AppInfos{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                '}';
    }

}
