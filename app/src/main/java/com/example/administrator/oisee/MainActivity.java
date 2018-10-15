package com.example.administrator.oisee;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.oisee.Activity.AnchorActivity;
import com.example.administrator.oisee.Activity.EmilActivity;
import com.example.administrator.oisee.Activity.EmilReActivity;
import com.example.administrator.oisee.Activity.FeedbackActivity;
import com.example.administrator.oisee.Activity.FindActivity;
import com.example.administrator.oisee.Activity.FindCountryActivity;
import com.example.administrator.oisee.Activity.LoginActivity;
import com.example.administrator.oisee.Activity.MycollectionActivity;
import com.example.administrator.oisee.Activity.MymentActivity;
import com.example.administrator.oisee.Activity.MyprofileActivity;
import com.example.administrator.oisee.Activity.MysettingsActivity;
import com.example.administrator.oisee.Activity.MywalletActivity;
import com.example.administrator.oisee.Activity.PhoneActivity;
import com.example.administrator.oisee.Activity.PhonegetbackActivity;
import com.example.administrator.oisee.Activity.TeacherActivity;
import com.example.administrator.oisee.Activity.VideoActivity;
import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.Fragment.FragmentAdapter;
import com.example.administrator.oisee.RemenDialog.GobalDialog;
import com.example.administrator.oisee.bean.LoginBean;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static String name;
    public static String url;
    public static LoginBean loginBean;
    private JSONObject jsonObject;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private ProgressDialog mProgressDialog;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        //启动线程
        executorService.execute(mRunnable);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //若工作完成，取消动画，初始化界面
            if (msg.what == 1)
            {}
            /*//开始初始化界面
            initView();*/
        }
    };
    /**
     * 线程
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            /*
                需要耗时的工作
            */
            data();
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    };

    /**
     * 创建ProrgressDialog
     */
    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载数据中,请稍等...");
        mProgressDialog.show();
    }

    private void data() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < Applicon.TabLayoutTitl.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(Applicon.TabLayoutTitl[i]);
            tab.setIcon(Applicon.TabLayoutIcon[i]);
        }
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        loginBean = (LoginBean) getIntent().getSerializableExtra("loginbean");
        //tablayout 点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 3) {
                    if (Config.longzhuangtai == 0) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initview() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
