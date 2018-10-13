package com.example.administrator.oisee.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.MyEditText;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.oisee.wxapi.GetSign.getSign;

public class WenziActivity extends AppCompatActivity {
    private MyEditText et_set_name;
    private TextView tv_set_name_complete;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wenzi);
        et_set_name = findViewById(R.id.et_set_name);
        tv_set_name_complete = findViewById(R.id.tv_set_name_complete);
        tv_set_name_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_set_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(WenziActivity.this, "请输入文字介绍", Toast.LENGTH_SHORT).show();
                    return;
                }
                setName(name);
            }
        });
    }

    private void setName(final String name) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", String.valueOf(MainActivity.loginBean.getResponse().getUserid()));
        map.put("sign", getSign(map));
        String UrlStr = zhuijia("http://121.40.186.118:10020/api/UserBase/PostEditUserInfo", map);
        Map<String, String> data = new HashMap<>();
        data.put("text_introduce", name);
        Gson gson = new Gson();
        String s = gson.toJson(data);
        OkHttpUtils.postString().url(UrlStr)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(s)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                try {
                    if (jsonObject.getInt("ErrCode") == 0) {
                        Toast.makeText(WenziActivity.this, "" + jsonObject.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
                        MainActivity.loginBean.getResponse().setText_introduce(name);
                        startActivity(new Intent(WenziActivity.this, MyprofileActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });


    }

    public String zhuijia(String UrlStr, Map<String, String> maps) {
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
