package com.example.administrator.oisee.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.github.fastshape.MyTextView;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private int recLen = 5;//跳过倒计时提示5秒
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    private ImageView iv_splash;
    private MyTextView tv_splash;
    private JSONObject jsonObject;
    private String beijings;
    //private Subscription subscription;
    boolean isClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_splash);
        initview();
        data();
        beijing();
    }

    private void data() {

        tv_splash.setOnClickListener(this);
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);//延迟5S后发送handler信息

    }


    private void initview() {
        tv_splash=findViewById(R.id.tv_splash);
        iv_splash=findViewById(R.id.iv_splash);
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv_splash.setText("跳过 " + recLen+"s");
                    if (recLen < 0) {
                        timer.cancel();
                        tv_splash.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    /**
     * 点击跳过
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_splash:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }

    }

    //获取网路图片设置背景图
    private void beijing() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "1");
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
                    Picasso.with(SplashActivity.this).load(beijings).into(iv_splash);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });

    }
}
