package com.xialm.googleplayme.protocol;

import com.xialm.googleplayme.bean.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xialm on 2016/11/2.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>>{
    @Override
    protected List<SubjectInfo> parseJson(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject listObject=null;
            SubjectInfo subjectInfo=null;
            List<SubjectInfo> subjectInfos= new ArrayList<>();  //对象的引用是放到栈内存中的
            for (int i = 0; i < jsonArray.length(); i++) {
                listObject = jsonArray.optJSONObject(i);
                String des = listObject.optString("des");
                String url = listObject.optString("url");
                subjectInfo = new SubjectInfo(des, url);
                subjectInfos.add(subjectInfo);
            }
            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getKey() {
        return "subject";
    }
}
