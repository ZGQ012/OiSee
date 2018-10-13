package com.example.administrator.oisee.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.bean.Response;
import com.example.administrator.oisee.bean.ZhuceSmScode;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.example.administrator.oisee.wxapi.spannrAdapte;
import com.google.gson.JsonArray;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private JSONObject jsonObject;
    private JsonRootBean jsonRootBean;
    private JSONArray list = new JSONArray();
    private int nationality_id;
    private TextView guojiaquhao;
    private String gjitem1;
    private String gjitem2;
    private ArrayList<String> bankNameList = new ArrayList<>();
    private ArrayList<String> bankNameList1 = new ArrayList<>();
    private ArrayList<List> bankNameList2 = new ArrayList<>();
    private Button fasong;
    private EditText edit_shoujihao;
    private EditText edit_yanzhengma;
    private EditText edit_mima;
    private ZhuceSmScode zhuceSmScode;
    private Button shezhimima;
    private EditText edit_yaoqingma;
    private String area_code;
    private TextView qiehuanzhuce;
    private ImageView toolbar_image;
    private LinearLayout bejing;
    private String beijings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
        data();


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
                // spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });
    }

    private void data() {
        guojiaquhao.setOnClickListener(this);
        fasong.setOnClickListener(this);
        shezhimima.setOnClickListener(this);
        qiehuanzhuce.setOnClickListener(this);
        toolbar_image.setOnClickListener(this);
        quhao();
        beijing();
        //        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                   // jsonRootBean.getResponse().get(position).getTitle();
//                nationality_id = jsonRootBean.getResponse().get(position).getNationality_id();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    //显示定时器样式
    private void Show() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(PhoneActivity.this, new OnOptionsSelectListener() {
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

    private void initView() {
        //spinner = findViewById(R.id.spinner);
        guojiaquhao = findViewById(R.id.guojiaquhao);
        fasong = findViewById(R.id.fasong);
        edit_shoujihao = findViewById(R.id.edit_shoujihao);
        edit_yanzhengma = findViewById(R.id.edit_yanzhengma);
        edit_mima = findViewById(R.id.edit_mima);
        shezhimima = findViewById(R.id.shezhimima);
        edit_yaoqingma = findViewById(R.id.edit_yaoqingma);
        qiehuanzhuce = findViewById(R.id.qiehuanzhuce);
        toolbar_image=findViewById(R.id.toolbar_image);
        bejing=findViewById(R.id.beijing);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guojiaquhao:
                if(isNetworkAvailable(this)){
                    Show();
                }else {
                    Toast.makeText(this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fasong:
                String i = guojiaquhao.getText().toString();
                if (!i.equals("国家区号") && !edit_shoujihao.getText().toString().trim().equals("")) {
                    fasong();
                } else {
                    if (i.equals("国家区号")) {
                        Toast.makeText(this, "请选择国家区号", Toast.LENGTH_SHORT).show();
                    } else if (edit_shoujihao.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.shezhimima:

                if (!guojiaquhao.getText().toString().equals("国家区号") && !edit_shoujihao.getText().toString().trim().equals("") && !edit_yanzhengma.getText().toString().trim().equals("") && !edit_mima.getText().toString().trim().equals("")) {
                    shezhimima();
                } else {
                    if (guojiaquhao.getText().toString().equals("国家区号")) {
                        Toast.makeText(this, "请选择国家区号", Toast.LENGTH_SHORT).show();
                    } else if (edit_shoujihao.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    } else if (edit_yanzhengma.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else if (edit_mima.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.qiehuanzhuce:
                startActivity(new Intent(this, EmilReActivity.class));
                break;
            case R.id.toolbar_image:
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }

    }

    //发送验证码请求
    private void fasong() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("nationality_id", String.valueOf(nationality_id));
        if (isPhone(edit_shoujihao.getText().toString().trim())) {
            map.put("mobile", edit_shoujihao.getText().toString().trim());
        }

        map.put("type", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Lib/GetSMSCode").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                zhuceSmScode = JsonUtils.getbean(response, ZhuceSmScode.class);
//                String k = zhuceSmScode.getSms().getSMSCode();
//                String k1 = zhuceSmScode.getSms().getSMSCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (zhuceSmScode.getErrCode() == 0) {
                            edit_yanzhengma.setText(zhuceSmScode.getSms().getSMSCode());
                        } else {
                            Toast.makeText(PhoneActivity.this, zhuceSmScode.getErrMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    //设置密码
    private void shezhimima() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("area_code", area_code);
        if (isPhone(edit_shoujihao.getText().toString().trim())) {
            map.put("mobile_email", edit_shoujihao.getText().toString().trim());
        }

        map.put("password", edit_mima.getText().toString().trim());
        map.put("code", edit_yanzhengma.getText().toString().trim());
        map.put("invite_yard", edit_yaoqingma.getText().toString().trim());
        map.put("registrationid", "");
        map.put("type", "1");
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
                        Toast.makeText(PhoneActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        String msg = jsonObject.getString("ErrMsg");
                        Toast.makeText(PhoneActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
                    Picasso.with(PhoneActivity.this).load(beijings).into(target);
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
