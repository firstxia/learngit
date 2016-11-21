package com.xialm.googleplayme.protocol;

import com.xialm.googleplayme.bean.AppInfos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xialm on 2016/11/4.
 */
public class GameProtocol extends BaseProtocol<List<AppInfos>> {
    @Override
    protected List<AppInfos> parseJson(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObject = null;
            AppInfos appInfos = null;
            List<AppInfos> appInfoses = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                long id = jsonObject.optLong("id");
                String name = jsonObject.optString("name");
                String packageName = jsonObject.optString("packageName");
                String iconUrl = jsonObject.optString("iconUrl");
                double stars = jsonObject.optDouble("stars");
                long size = jsonObject.optLong("size");
                String downloadUrl = jsonObject.optString("downloadUrl");
                String des = jsonObject.optString("des");
                appInfos = new AppInfos(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfoses.add(appInfos);
            }
            return appInfoses;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String getKey() {
        return "game";
    }
}
