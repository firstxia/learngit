package com.xialm.googleplayme.utils;

import android.os.Environment;

import java.io.File;

/**
 * 统一管理我们自己的缓存目录
 * Created by Xialm on 2016/10/31.
 */

public class FileUtils {

    public static final String ROOT = "GooglePlayMe"; // 根目录
    public static final String CACHE = "cache"; // 缓存文件
    public static final String ICON = "icon";

    /**
     * 得到一个目录:缓存目录或者是存放图片的icon目录
     * sd卡存在:
     *      /mnt/sdcard/GooglePalyMe/cache
     *      /mnt/sdcard/GooglePalyMe/icon
     * Sd卡不存在:
     *      /data/data/GooglePalyMe/cache/cache
     *      /data/data/GooglePalyMe/cache/icon
     * @param dirname 目录名称
     * @return
     */
    public static File getDir(String dirname) {
        StringBuilder sb = new StringBuilder();
//        File file = Environment.getExternalStorageDirectory();
        // 如果sd卡存在,就在sd卡的根目录创建并维护一个文件夹
        if (isSDCardAvailable()) {
            String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sb.append(sdcardPath);  //  /mnt/sdcard
            sb.append(File.separator);  //  /mnt/sdcard/
            sb.append(ROOT);    //  /mnt/sdcard/GooglePlayMe
            sb.append(File.separator);  //  /mnt/sdcard/GooglePlayMe/
            sb.append(dirname); //  /mnt/sdcard/GooglePlayMe/cache
        } else {
            // sd卡不可用,就在系统data目录下创建并维护一个文件夹
//            File cacheDir = UIUtils.getContext().getCacheDir();
            String sdCachePath = UIUtils.getContext().getCacheDir().getAbsolutePath();
            sb.append(sdCachePath);  // data/data/包名/cache
            sb.append(File.separator);  // data/data/包名/cache/
            sb.append(dirname);     // data/data/包名/cache/cache或者是icon

        }

        File file = new File(sb.toString());
        // 如果目录不存在,并且不是一个文件,就创建该目录
        if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
        }
        return  file;
    }

    /**
     * 判断sd卡是否可用
     * @return 返回true可用;返回false不可用
     */
    private static boolean isSDCardAvailable() {
        // 是否挂载
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取数据缓存目录
     * @return
     *      /mnt/sdcard/GooglePalyMe/cache
     *   或 /data/data/GooglePalyMe/cache/cache
     */
    public static File getCache() {
        return getDir(CACHE);
    }

    /**
     * 获取图片缓存目录
     * @return
     *      /mnt/sdcard/GooglePalyMe/icon
     *    或/data/data/GooglePalyMe/cache/icon
     */
    public static File getIcon() {
        return getDir(ICON);
    }
}
