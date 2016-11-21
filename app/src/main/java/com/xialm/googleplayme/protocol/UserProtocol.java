package com.xialm.googleplayme.protocol;

import com.xialm.googleplayme.bean.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xialm on 2016/11/9.
 */
public class UserProtocol extends BaseProtocol<UserInfo> {
    @Override
    protected UserInfo parseJson(String jsonStr) {
//        {name:'传智黄盖',email:'huanggai@itcast.cn',url:'image/user.png'}
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String name = jsonObject.optString("name");
            String email = jsonObject.optString("email");
            String url = jsonObject.optString("url");
            UserInfo userInfo = new UserInfo(name, email, url);
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String getKey() {
        return "user";
    }
}
