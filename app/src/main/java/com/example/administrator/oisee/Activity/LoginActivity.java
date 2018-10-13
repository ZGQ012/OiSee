package com.example.administrator.oisee.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.bean.LoginBean;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.sdklibrary.base.qq.share.MyQQActivityResult;
import com.sdklibrary.base.qq.share.MyQQLoginCallback;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.qq.share.bean.MyQQUserInfo;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewQQ;
    private TextView toolbar_re;
    private TextView qiehuan;
    private boolean b = true;
    private TextView guojiaquhao;
    private JSONObject jsonObject;
    private JsonRootBean jsonRootBean;
    private LoginBean loginBean;
    private ArrayList<String> bankNameList = new ArrayList<>();
    private ArrayList<String> bankNameList1 = new ArrayList<>();
    private ArrayList<List> bankNameList2 = new ArrayList<>();
    private int nationality_id;
    private String area_code;
    private EditText edit_shoujihao;
    private EditText edit_mima;
    private Button denglu;
    private ImageView toolbar_image;
    private LinearLayout beijing;
    private String beijings;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
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
        quhao();
        beijing();
    }

    private void initview() {
        imageViewQQ = findViewById(R.id.imageViewQQ);
        toolbar_re = findViewById(R.id.toolbar_re);
        qiehuan = findViewById(R.id.qiehuandenglu);
        guojiaquhao = findViewById(R.id.guojiaquhao);
        edit_shoujihao = findViewById(R.id.edit_shoujihao);
        edit_mima = findViewById(R.id.shurumima);
        denglu = findViewById(R.id.denglu);
        toolbar_image = findViewById(R.id.toolbar_image);
        beijing = findViewById(R.id.linbeijing);
        imageViewQQ.setOnClickListener(this);
        toolbar_re.setOnClickListener(this);
        qiehuan.setOnClickListener(this);
        guojiaquhao.setOnClickListener(this);
        denglu.setOnClickListener(this);
        toolbar_image.setOnClickListener(this);
        beijing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewQQ:
                MyQQShare.newInstance(this).login(new MyQQLoginCallback() {
                    @Override
                    public void loginSuccess(MyQQUserInfo userInfo) {
                        //登录成功
                        userInfo.getUserImageUrl();//这里是qq 头像
                        String i = userInfo.getOpenid(); //唯一id
                        userInfo.getSex();//性别
                        userInfo.getNickname();//用户名称
                        if (userInfo != null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name", userInfo.getNickname());
                            intent.putExtra("url", userInfo.getUserImageUrl());
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

//                        Picasso.with(getApplication()).load(userInfo.getUserImageUrl()).into(imageView);
//                        textView.setText(userInfo.getNickname());
                    }

                    @Override
                    public void loginFail() {
                        //登录失败
                    }

                    @Override
                    public void loginCancel() {
                        //取消登录
                    }
                });
                break;
            case R.id.toolbar_re:
                startActivity(new Intent(this, PhoneActivity.class));
                break;
            case R.id.qiehuandenglu:
                if (b == true) {
                    guojiaquhao.setVisibility(View.GONE);
                    b = false;
                } else if (b == false) {
                    guojiaquhao.setVisibility(View.VISIBLE);

                    b = true;
                }
                break;
            case R.id.guojiaquhao:
                if(isNetworkAvailable(this)){
                    Show();
                }else {
                    Toast.makeText(this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.denglu:
                String i = guojiaquhao.getText().toString();
                if (b == true) {
                    if (!i.equals("国家区号") && !edit_shoujihao.getText().toString().trim().equals("") && !edit_mima.getText().toString().trim().equals("")) {
                        denglu();
                    } else {
                        if (i.equals("国家区号")) {
                            Toast.makeText(this, "请选择国家区号", Toast.LENGTH_SHORT).show();
                        } else if (edit_shoujihao.getText().toString().trim().equals("")) {
                            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                        } else if (edit_mima.getText().toString().trim().equals("")) {
                            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (!edit_shoujihao.getText().toString().trim().equals("") && !edit_mima.getText().toString().trim().equals("")) {
                        youxiangdenglu();
                    } else {
                        if (edit_shoujihao.getText().toString().trim().equals("")) {
                            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                        } else if (edit_mima.getText().toString().trim().equals("")) {
                            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.toolbar_image:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;


        }
    }
    //重写手机返回事件
    @Override
    public void onBackPressed() {
        //TODO something
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
    //qq登录 回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyQQActivityResult.onActivityResult(requestCode, resultCode, data);
    }

    //显示定时器样式
    private void Show() {
        bankNameList.clear();
        bankNameList1.clear();
        bankNameList2.clear();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(LoginActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                guojiaquhao.setText(bankNameList.get(options1));
                nationality_id = jsonRootBean.getResponse().get(options1).getNationality_id();
                area_code = jsonRootBean.getResponse().get(options1).getArea_code();

            }
        }).setCyclic(false, false, false).build();

        for (int i = 0; i < jsonRootBean.getResponse().size(); i++) {
            bankNameList.add(jsonRootBean.getResponse().get(i).getTitle());
            bankNameList1.add(jsonRootBean.getResponse().get(i).getArea_code());
            bankNameList2.add(bankNameList1);
        }
        pvOptions.setPicker(bankNameList, bankNameList2);
        pvOptions.show();

    }


    private void denglu() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("area_code", area_code);
        if (isPhone(edit_shoujihao.getText().toString().trim())) {
            map.put("mobile_email", edit_shoujihao.getText().toString().trim());
        }
        map.put("password", edit_mima.getText().toString().trim());
        map.put("registrationid", "");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/UserBase/GetUserLogin").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                loginBean = JsonUtils.getbean(response, LoginBean.class);
                if (loginBean.getErrCode() == 0) {
                    Config.longzhuangtai=1;
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("loginbean", loginBean);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void youxiangdenglu() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("area_code", "0");
        if (checkEmail(edit_shoujihao.getText().toString().trim())) {
            map.put("mobile_email", edit_shoujihao.getText().toString().trim());
        } else {
            Toast.makeText(this, "请输入正确的邮箱号", Toast.LENGTH_SHORT).show();
        }

        map.put("password", edit_mima.getText().toString().trim());
        map.put("registrationid", "");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/UserBase/GetUserLogin").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                loginBean = JsonUtils.getbean(response, LoginBean.class);
                if (loginBean.getErrCode() == 0) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Config.longzhuangtai=1;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("loginbean", loginBean);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败或没有此邮箱", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void quhao() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Lib/GetAreaCode").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });
    }

    //验证手机号
    private boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            Toast.makeText(this, "手机号应为11位数", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();

            if (!isMatch) {
                Toast.makeText(this, "请填入正确的手机号", Toast.LENGTH_SHORT).show();
            }
            return isMatch;
        }
    }

    //验证邮箱
    private boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
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
                    Picasso.with(LoginActivity.this).load(beijings).into(target);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });

    }

    //设置背景图片
    private final Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable drawable = new BitmapDrawable(bitmap);
            beijing.setBackground(drawable);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
   //判断网络
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

