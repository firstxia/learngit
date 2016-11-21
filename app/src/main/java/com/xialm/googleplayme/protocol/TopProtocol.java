package com.xialm.googleplayme.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xialm on 2016/11/16.
 */
public class TopProtocol extends BaseProtocol<List<String>> {
    @Override
    protected List<String> parseJson(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String keyStr = jsonArray.optString(i);
                strings.add(keyStr);
            }
            return strings;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String getKey() {
        return "hot";
    }
}
