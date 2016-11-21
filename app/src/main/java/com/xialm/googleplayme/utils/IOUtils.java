package com.xialm.googleplayme.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * 关闭操作流的工具类
 * Created by Xialm on 2016/10/30.
 */

public class IOUtils {
    /**
     * 关闭流资源
     * @param closeable 输入流和输出流的父接口
     */
    public static void close(Closeable closeable){
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
