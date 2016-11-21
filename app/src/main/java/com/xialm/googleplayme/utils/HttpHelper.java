package com.xialm.googleplayme.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Xialm on 2016/10/30.
 */
public class HttpHelper {
    public static final String BASEURL="http://127.0.0.1:8090";
    private OkHttpClient okHttpClient=new OkHttpClient();
    private String url;

    public HttpHelper(String url) {
        this.url = url;
    }

    /**
     * 同步get请求
     * @return json字符串
     */
    public String getSync() {
        Request request=new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) { // 响应码200代表成功
                String responseString = response.body().string();
                return responseString;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
