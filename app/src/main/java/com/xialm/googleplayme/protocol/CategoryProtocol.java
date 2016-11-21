package com.xialm.googleplayme.protocol;

import com.xialm.googleplayme.bean.CategoryInfo;
import com.xialm.googleplayme.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xialm on 2016/11/14.
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    protected List<CategoryInfo> parseJson(String jsonStr) {
        LogUtil.d("%s", "CategoryProtocol-parseJosn::jsonStr==" + jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObject;
            JSONArray infosArray;
            JSONObject infosObject;
            CategoryInfo categoryInfo;
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                String title = jsonObject.optString("title");
                categoryInfo = new CategoryInfo();
                categoryInfo.title = title;
                categoryInfo.isTitle = true;
                categoryInfos.add(categoryInfo);   //把标题title也单独作为一个category了(条目)

                infosArray = jsonObject.optJSONArray("infos");
                for (int j = 0; j < infosArray.length(); j++) {
                    infosObject = infosArray.optJSONObject(j);
                    String name1 = infosObject.optString("name1");
                    String name2 = infosObject.optString("name2");
                    String name3 = infosObject.optString("name3");
                    String url1 = infosObject.optString("url1");
                    String url2 = infosObject.optString("url2");
                    String url3 = infosObject.optString("url3");
                    categoryInfo = new CategoryInfo(name1, name2, name3, url1, url2, url3);
                    categoryInfos.add(categoryInfo);
                }

            }
            return categoryInfos;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getKey() {
        return "category";
    }
}
