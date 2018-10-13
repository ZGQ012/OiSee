package com.example.administrator.oisee.View;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntentUitls {
    //post请求带url参数
    public static void SetIntenturl(String url, Map<String, String> mapurl, Map<String, String> mapPamas, StringCallback stringCallback) {
        String UrlStr = zhuijia(url, mapurl);
        Gson gson = new Gson();
        String s = gson.toJson(mapPamas);
        OkHttpUtils.postString().url(UrlStr)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(s)
                .build().execute(stringCallback);

    }
   //get请求
    public static void SetIntent(String url, Map<String, String> map, StringCallback stringCallback) {
        OkHttpUtils.get().url(url).params(map)
                .build().execute(stringCallback);
    }

    public static String zhuijia(String UrlStr, Map<String, String> maps) {
        if (maps == null || maps.keySet().size() == 0) {
            return UrlStr;
        }
        StringBuffer sb = new StringBuffer(UrlStr);
        List<String> strs = new ArrayList<>(maps.keySet());
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            if (i == 0) {
                sb.append("?" + str + "=" + maps.get(str));
            } else {
                sb.append(str + "=" + maps.get(str));
            }
            if (i != (strs.size() - 1)) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

}
