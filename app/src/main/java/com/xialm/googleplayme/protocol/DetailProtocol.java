package com.xialm.googleplayme.protocol;

import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xialm on 2016/11/12.
 */
public class DetailProtocol extends BaseProtocol<AppInfos> {
    private String packageName;

    // 通过构造传递包名
    public DetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected AppInfos parseJson(String jsonStr) {
//        LogUtil.d("%s",jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            long id = jsonObject.optLong("id");
            String name = jsonObject.optString("name");
            String packageName = jsonObject.optString("packageName");
            String iconUrl = jsonObject.optString("iconUrl");
            double stars = jsonObject.optDouble("stars");
            long size = jsonObject.optLong("size");
            String downloadUrl = jsonObject.optString("downloadUrl");
            String des = jsonObject.optString("des");
            String author = jsonObject.optString("author");
            String date = jsonObject.optString("date");
            String downloadNum = jsonObject.optString("downloadNum");
            String version = jsonObject.optString("version");

            List<String> screens = new ArrayList<>();
            if (jsonObject.has("screen")) { //如果对象中有screen这个字段的话,返回true
                // 有关键字的话,获取数组,下面遍历数组就不会空指针了
                JSONArray screenArray = jsonObject.optJSONArray("screen");
                for (int i = 0; i < screenArray.length(); i++) {
                    String screen = screenArray.optString(i);
                    screens.add(screen);
                }
            }

            List<String> safeDeses = new ArrayList<>();
            List<String> safeDesUrls = new ArrayList<>();
            List<String> safeUrls = new ArrayList<>();
            List<Integer> safeDesColors = new ArrayList<>();
            if (jsonObject.has("safe")) {
                JSONArray safeArray = jsonObject.optJSONArray("safe");
                JSONObject safeObject = null;
                for (int i = 0; i < safeArray.length(); i++) {
                    safeObject = safeArray.optJSONObject(i);
                    String safeDes = safeObject.optString("safeDes");
                    String safeDesUrl = safeObject.optString("safeDesUrl");
                    String safeUrl = safeObject.optString("safeUrl");
                    int safeDesColor = safeObject.optInt("safeDesColor");
                    safeDeses.add(safeDes);
                    safeDesUrls.add(safeDesUrl);
                    safeUrls.add(safeUrl);
                    safeDesColors.add(safeDesColor);
                }
            }
            AppInfos appInfos = new AppInfos(id, name, packageName, iconUrl, stars, size, downloadUrl, des, author, date, downloadNum, version, screens, safeDeses, safeDesUrls, safeUrls, safeDesColors);
            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String getKey() {
        return "detail";
    }

    @Override
    protected String getExtraParams() {
        return "&packageName=" + packageName;
    }
}
