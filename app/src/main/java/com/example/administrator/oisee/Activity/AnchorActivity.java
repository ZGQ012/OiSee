package com.example.administrator.oisee.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.IntentUitls;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.example.administrator.oisee.zbsqitemActivity.ZbsetnameActivity;
import com.github.androidtools.inter.MyOnClickListener;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AnchorActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout Linname;
    private LinearLayout linguoji;
    private LinearLayout linxingbie;
    private LinearLayout linshengri;
    private LinearLayout linzbfenlei;
    private LinearLayout linzbyuyan;
    private EditText wenziEdit;
    private LinearLayout lindianhua;
    private TextView nameText;
    private TextView guojiText;
    private TextView xingbieText;
    private JSONObject jsonObject;
    private JsonRootBean jsonRootBean;
    private ArrayList<String> bankNameList = new ArrayList<>();
    private String title;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anchor);
        initview();
        initdata();
        //创建ProgressDialog
        createProgressDialog();
        //启动线程
        executorService.execute(mRunnable);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //若工作完成，取消动画，初始化界面
            if (msg.what == 1)
                mProgressDialog.cancel();
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
        guojiliebiao();
    }

    private void initdata() {
        String ZBname = getIntent().getStringExtra("ZBname");
        if (!TextUtils.isEmpty(ZBname)) {
            nameText.setText(ZBname);
        }
    }

    private void initview() {
        Linname = findViewById(R.id.Linname);
        Linname.setOnClickListener(this);
        linguoji = findViewById(R.id.linguoji);
        linguoji.setOnClickListener(this);
        linxingbie = findViewById(R.id.linxingbie);
        linxingbie.setOnClickListener(this);
        linshengri = findViewById(R.id.linshengri);
        linshengri.setOnClickListener(this);
        linzbfenlei = findViewById(R.id.linzbfenlei);
        linzbfenlei.setOnClickListener(this);
        linzbyuyan = findViewById(R.id.linzbyuyan);
        linzbyuyan.setOnClickListener(this);
        wenziEdit = findViewById(R.id.wenziEdit);
        wenziEdit.setFocusable(false);
        wenziEdit.setOnClickListener(this);
        lindianhua = findViewById(R.id.lindianhua);
        lindianhua.setOnClickListener(this);
        nameText = findViewById(R.id.nameText);
        guojiText = findViewById(R.id.guojiText);
        xingbieText = findViewById(R.id.xingbieText);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Linname:
                startActivity(new Intent(this, ZbsetnameActivity.class));
                finish();
                break;
            case R.id.linguoji:
                Show();
                break;
            case R.id.linxingbie:
                final BottomSheetDialog dialog = new BottomSheetDialog(this);
                View selectSexView = getLayoutInflater().inflate(R.layout.setsex, null);
                selectSexView.findViewById(R.id.tv_select_sex_boy).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        xingbieText.setText("男");
                    }
                });
                selectSexView.findViewById(R.id.tv_select_sex_girl).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        xingbieText.setText("女");

                    }
                });
                selectSexView.findViewById(R.id.tv_select_sex_cancel).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(selectSexView);
                dialog.show();
                break;
            case R.id.linshengri:
                break;
            case R.id.linzbfenlei:
                break;
            case R.id.linzbyuyan:
                break;
            case R.id.wenziEdit:
                break;
            case R.id.lindianhua:
                break;

        }
    }

    private void guojiliebiao() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", "1");
        map.put("sign", GetSign.getSign(map));
        IntentUitls.SetIntent(Config.guojiUrl, map, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);

            }
        });
    }

    //显示定时器样式
    private void Show() {
        bankNameList.clear();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(AnchorActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                guojiText.setText(bankNameList.get(options1));
                title = jsonRootBean.getResponse().get(options1).getTitle();

            }
        }).setCyclic(false, false, false).build();

        for (int i = 0; i < jsonRootBean.getResponse().size(); i++) {
            bankNameList.add(jsonRootBean.getResponse().get(i).getTitle());

        }
        pvOptions.setPicker(bankNameList);
        pvOptions.show();

    }
}
