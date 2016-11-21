package com.xialm.googleplayme.protocol;

import android.util.Log;
import com.xialm.googleplayme.bean.AppInfos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.xialm.googleplayme.utils.LogUtil.TAG;

/**
 * 协议类
 * Created by Xialm on 2016/10/30.
 */
public class HomeProtocol extends BaseProtocol<List<AppInfos>>{

    private List<String> pictures;

    /**
     * 获取轮播图图片的url集合
     *
     * @return
     */
    public List<String> getPictures() {
        return pictures;
    }

    /**
     * 解析json字符串(服务器或者本地)
     * @param jsonStr
     */
    @Override
    protected List<AppInfos> parseJson(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            // 解析轮播图数据url
            JSONArray pictureArray = jsonObject.optJSONArray("picture");
            pictures = new ArrayList<>();
            for (int i = 0; i < pictureArray.length(); i++) {
                String pictureUrl = pictureArray.optString(i);
                pictures.add(pictureUrl);
            }


            JSONArray listArray = jsonObject.optJSONArray("list");
            JSONObject listObject=null;
            AppInfos appInfos=null;
            // xln 对象尽量不要在循环中反复创建
            List<AppInfos> appInfoses = new ArrayList<>();
            for (int i = 0; i < listArray.length(); i++) {
                listObject = listArray.optJSONObject(i);
                long id = listObject.optLong("id");
                String name = listObject.optString("name");
                String packageName = listObject.optString("packageName");
                String iconUrl = listObject.optString("iconUrl");
                double stars = listObject.optDouble("stars");
                long size = listObject.optLong("size");
                String downloadUrl = listObject.optString("downloadUrl");
                String des = listObject.optString("des");

                appInfos = new AppInfos(id ,name,packageName,iconUrl,stars,size,downloadUrl,des);
                appInfoses.add(appInfos);
            }
            return appInfoses;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "parseJson: 解析出错了");
        }
        return null;
    }

    @Override
    protected String getKey() {
        return "home";
    }
}
