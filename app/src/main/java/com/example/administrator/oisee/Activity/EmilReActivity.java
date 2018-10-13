package com.example.administrator.oisee.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.bean.ZhuceSmScode;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmilReActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText qingshuruyouxiang;
    private EditText shezhimima;
    private EditText yanzhengma;
    private Button zhuce;
    private EditText yaoqingma;
    private Button fasong;
    private TextView fanhui;
    private JSONObject jsonObject;
    private JsonRootBean jsonRootBean;
    private ZhuceSmScode zhuceSmScode;
    private ImageView toolbar_image;
    private LinearLayout bejing;
    private String beijings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emil_re);
        initView();
        data();
    }


    private void data() {
        fasong.setOnClickListener(this);
        zhuce.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        toolbar_image.setOnClickListener(this);
        beijing();
    }

    private void initView() {
        qingshuruyouxiang = findViewById(R.id.qingshuruyouxiang);
        shezhimima = findViewById(R.id.shezhimima);
        zhuce = findViewById(R.id.zhuce);
        fanhui = findViewById(R.id.fanhui);
        yanzhengma = findViewById(R.id.edit_yanzhengma);
        fasong = findViewById(R.id.fasong);
        yaoqingma = findViewById(R.id.edit_yaoqingma);
        toolbar_image=findViewById(R.id.toolbar_image);
        bejing=findViewById(R.id.beijing);
    }

    private void zhuce() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("area_code", "0");
        map.put("mobile_email", qingshuruyouxiang.getText().toString().trim());
        map.put("password", shezhimima.getText().toString().trim());
        map.put("code", yanzhengma.getText().toString().trim());
        map.put("invite_yard", yaoqingma.getText().toString().trim());
        map.put("registrationid", "");
        map.put("type", "2");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/UserBase/GetUserSMSCodeLogin").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);

                try {
                    if (jsonObject.getInt("ErrCode") == 0) {
                        Toast.makeText(EmilReActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        String msg = jsonObject.getString("ErrMsg");
                        Toast.makeText(EmilReActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //发送验证码请求
    private void fasong() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", qingshuruyouxiang.getText().toString().trim());
        map.put("type", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Lib/GetSendEmail").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                zhuceSmScode = JsonUtils.getbean(response, ZhuceSmScode.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (zhuceSmScode.getErrCode() == 0) {
                            yanzhengma.setText(zhuceSmScode.getSms().getSMSCode());
                        } else {
                            Toast.makeText(EmilReActivity.this, zhuceSmScode.getErrMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fasong:
                if (!qingshuruyouxiang.getText().toString().trim().equals("")) {
                    fasong();
                } else {
                    if (qingshuruyouxiang.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入邮箱号", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.zhuce:
                if (!qingshuruyouxiang.getText().toString().equals("") && !yanzhengma.getText().toString().trim().equals("") && !shezhimima.getText().toString().trim().equals("")) {
                    zhuce();
                } else {
                    if (qingshuruyouxiang.getText().toString().equals("")) {
                        Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    } else if (yanzhengma.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else if (shezhimima.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.fanhui:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.toolbar_image:
                startActivity(new Intent(this, LoginActivity.class));
                break;


        }

    }
    //获取网路图片设置背景图
    private void beijing() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "2");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Lib/GetAdvertisingFigure").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                // Drawable d=Drawable.createFromPath();
                try {
                    beijings = jsonObject.getJSONObject("Response").getString("image_url");
                    Picasso.with(EmilReActivity.this).load(beijings).into(target);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });

    }
    //设置背景图片
    private final Target target=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable drawable = new BitmapDrawable(bitmap);
            bejing.setBackground(drawable);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}
