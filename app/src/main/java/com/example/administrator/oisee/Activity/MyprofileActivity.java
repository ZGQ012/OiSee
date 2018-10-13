package com.example.administrator.oisee.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.Video.MediaManager;
import com.example.administrator.oisee.View.BitmapUtils;
import com.example.administrator.oisee.View.Dyz;
import com.example.administrator.oisee.View.Fileurl;
import com.example.administrator.oisee.View.GetPathFromUri;
import com.example.administrator.oisee.View.HttpAssistant;
import com.example.administrator.oisee.View.IntentUitls;
import com.example.administrator.oisee.View.Luban;
import com.example.administrator.oisee.View.RealPathFromUriUtils;
import com.example.administrator.oisee.bean.Body;
import com.example.administrator.oisee.features.select.VideoSelectActivity;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.oisee.wxapi.GetSign.getSign;


public class MyprofileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView xingbie;
    private ImageView touxiang;
    private TextView name;
    private TextView qianming;
    private EditText wenzijieshao;
    private JSONObject jsonObject;
    private LinearLayout Linname;
    private LinearLayout linxingbie;
    private LinearLayout linqianming;
    private LinearLayout lintouxiang;
    private LinearLayout updateluyin;
    private ImageButton shipin;
    private int REQUEST_CODE_PICK;
    private ImageView videoview;
    private MediaController controller;
    private ImageView toolbar_image;
    private TextView yuyinjieshao;
    private static final int FILE_SELECT_CODE = 0;
    private static final int REQUEST_PERMISSION = 0x001;

    //权限
    private String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int REQUEST_PICK_IMAGE = 11101;
    private String realPathFromUri;
    private String filePath;
    private String path;
    private String shipin1;
    private Bitmap bitmap;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        jurisdiction();

    }

    private void data() {
        xingbie.setOnClickListener(this);
        touxiang.setOnClickListener(this);
        name.setOnClickListener(this);
        qianming.setOnClickListener(this);
        wenzijieshao.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        wenzijieshao.setOnClickListener(this);
        Linname.setOnClickListener(this);
        linxingbie.setOnClickListener(this);
        linqianming.setOnClickListener(this);
        shipin.setOnClickListener(this);
        lintouxiang.setOnClickListener(this);
        toolbar_image.setOnClickListener(this);
        updateluyin.setOnClickListener(this);
        yuyinjieshao.setOnClickListener(this);
        xianshi();
    }

    private void initview() {
        xingbie = findViewById(R.id.xingbie);
        touxiang = findViewById(R.id.touxiang);
        name = findViewById(R.id.name);
        qianming = findViewById(R.id.gexingqianming);
        wenzijieshao = findViewById(R.id.wenzijieshao);
        Linname = findViewById(R.id.Linname);
        linxingbie = findViewById(R.id.linxingbie);
        linqianming = findViewById(R.id.linqianming);
        shipin = findViewById(R.id.shipinbtn);
        videoview = findViewById(R.id.videoview);
        controller = new MediaController(this);
        lintouxiang = findViewById(R.id.lintouxiang);
        toolbar_image = findViewById(R.id.toolbar_image);
        updateluyin = findViewById(R.id.updateluyin);
        yuyinjieshao = findViewById(R.id.yuyinjieshao);
    }

    private void xianshi() {
        name.setText(MainActivity.loginBean.getResponse().getNick_name());
        xingbie.setText(MainActivity.loginBean.getResponse().getSex());
        qianming.setText(MainActivity.loginBean.getResponse().getPersonalized_signature());
        wenzijieshao.setText(MainActivity.loginBean.getResponse().getText_introduce());
        Glide.with(MyprofileActivity.this).load(MainActivity.loginBean.getResponse().getAvatar()).into(touxiang);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = Dyz.createVideoThumbnail(MainActivity.loginBean.getResponse().getVideo_introduce(), 1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoview.setImageBitmap(bitmap);
                        mDialog.dismiss();
                    }
                });
            }
        }).start();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Linname:
                startActivity(new Intent(this, SetNameActivity.class));
                finish();
                break;
            case R.id.linxingbie:
                final BottomSheetDialog dialog = new BottomSheetDialog(this);
                View selectSexView = getLayoutInflater().inflate(R.layout.setsex, null);
                selectSexView.findViewById(R.id.tv_select_sex_boy).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        updateSex("男");
                    }
                });
                selectSexView.findViewById(R.id.tv_select_sex_girl).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        updateSex("女");
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
            case R.id.linqianming:
                startActivity(new Intent(this, SetqianmingActivity.class));
                finish();
                break;
            case R.id.wenzijieshao:
                startActivity(new Intent(this, WenziActivity.class));
                finish();
                break;
            case R.id.shipinbtn:
                showSelectPhotoDialog();
                break;
            case R.id.lintouxiang:
//                Intent intent1 = new Intent();
//                intent1.setAction(Intent.ACTION_GET_CONTENT);
//                intent1.addCategory(Intent.CATEGORY_OPENABLE);
//                intent1.setType("image/*");
//                startActivityForResult(intent1, 111);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 111);
                break;
            case R.id.toolbar_image:
//                Config.longzhuangtai=1;
//                startActivity(new Intent(MyprofileActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.updateluyin:
                startActivity(new Intent(MyprofileActivity.this, Main3Activity.class));
                break;
            case R.id.yuyinjieshao:
                String i = getIntent().getStringExtra("path");
                if (i != null) {
                    MediaManager.playSound(i, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                        }
                    });
                }

                break;

        }
    }


    //修改性别 网络请求
    private void updateSex(final String sex) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", String.valueOf(MainActivity.loginBean.getResponse().getUserid()));
        map.put("sign", getSign(map));
        String UrlStr = zhuijia("http://121.40.186.118:10020/api/UserBase/PostEditUserInfo", map);
        Map<String, String> data = new HashMap<>();
        data.put("sex", sex);
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
                        Toast.makeText(MyprofileActivity.this, "" + jsonObject.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
                        MainActivity.loginBean.getResponse().setSex(sex);
                        xingbie.setText(sex);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    //修改头像 网络请求
    private void updateURl(final String url) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", String.valueOf(MainActivity.loginBean.getResponse().getUserid()));
        map.put("sign", getSign(map));
        Map<String, String> data = new HashMap<>();
        data.put("avatar", url);
        IntentUitls.SetIntenturl("http://121.40.186.118:10020/api/UserBase/PostEditUserInfo", map, data, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                try {
                    if (jsonObject.getInt("ErrCode") == 0) {
                        Toast.makeText(MyprofileActivity.this, "" + jsonObject.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
                        MainActivity.loginBean.getResponse().setAvatar(url);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MyprofileActivity.this).load(url).into(touxiang);
                                // Picasso.with(MyprofileActivity.this).load(path).into(touxiang);
                            }
                        });
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

    private BottomSheetDialog selectPhotoDialog;

    public void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView = LayoutInflater.from(this).inflate(R.layout.xiangceshipin, null);
            sexView.findViewById(R.id.app_tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    xiangce();
                    Toast.makeText(MyprofileActivity.this, "123", Toast.LENGTH_SHORT).show();
                }
            });
            sexView.findViewById(R.id.app_tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    paizhao();
                    Toast.makeText(MyprofileActivity.this, "456", Toast.LENGTH_SHORT).show();

                }
            });
            sexView.findViewById(R.id.app_tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(this);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }

    //拍摄
    private void paizhao() {
        Intent intent = VedioRecordActivity.startRecordActivity(Environment.getExternalStorageDirectory().getAbsolutePath() + "/custom/", this);
        startActivity(intent);

    }

    //相册
    private void xiangce() {
//        if (PhoneInfo.isMIUI()) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
//        startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 222);
//        } else {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.setType("video/*");
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("video/*");
//        }
        startActivity(new Intent(this, VideoSelectActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplication(), "点击取消从相册选择", Toast.LENGTH_LONG).show();
                    return;
                }
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = null;
                    path = "";
                    cursor = this.getContentResolver().query(uri, proj, null, null, null);
                    //获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径   结果类似：/mnt/sdcard/DCIM/Camera/IMG_20151124_013332.jpg
                    path = cursor.getString(column_index);
                    List<File> files = null;
                    try {
                        files = Luban.with(this).load(path).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File file = files.get(0);
                    String imgStr = BitmapUtils.fileToString(file);
                    updatefile(imgStr);


                    Log.e("imgStr", "" + imgStr + "----" + path);


                }
                break;
            case 222:
                if (resultCode == RESULT_OK) {
                    String videoPath = GetPathFromUri.getRealFilePath(this, data.getData());
                    final Map<String, File> fileMap = new HashMap<>();
                    File file = Fileurl.getFileByUri(data.getData(), this);
                    fileMap.put("file", file);
                    String url = "http://121.40.186.118:10020/api/Lib/PostUploadFile";
                    Map<String, String> map = new HashMap<>();
                    map.put("rnd", "1");
                    map.put("sign", getSign(map));
                    final String UrlStr = zhuijia(url, map);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String respons = HttpAssistant.upLoadFilePost(UrlStr, fileMap);
                                Log.e("respons", "" + respons);
                                jsonObject = JsonUtils.getjsonobj(respons);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //    try {
//                                            videoview.setVideoURI(Uri.parse(jsonObject.getString("Response")));
//                                            controller.setMediaPlayer(videoview);
//                                            videoview.setMediaController(controller);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }

                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                break;
        }
    }

    //修改头像 网络请求
    private void updatefile(final String url) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", "1");
        map.put("sign", getSign(map));
        String UrlStr = zhuijia("http://121.40.186.118:10020/api/Lib/PostUploadFileBase64", map);
        Map<String, String> data = new HashMap<>();
        data.put("file", url);
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
                    updateURl(jsonObject.getJSONObject("Response").getString("img"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void scshipin(String shipin) {
        final Map<String, File> fileMap = new HashMap<>();
        File file = new File(shipin);
        fileMap.put("file", file);
        String url = "http://121.40.186.118:10020/api/Lib/PostUploadFile";
        Map<String, String> map = new HashMap<>();
        map.put("rnd", "1");
        map.put("sign", getSign(map));
        final String UrlStr = zhuijia(url, map);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String respons = HttpAssistant.upLoadFilePost(UrlStr, fileMap);
                    Log.e("respons", "" + respons);
                    jsonObject = JsonUtils.getjsonobj(respons);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (jsonObject.getInt("ErrCode") == 0) {
                                    updatevideo(jsonObject.getString("Response"), shipin);
                                } else {
                                    Toast.makeText(MyprofileActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //修改头像 网络请求
    private void updatevideo(final String url, final String video) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", String.valueOf(MainActivity.loginBean.getResponse().getUserid()));
        map.put("sign", getSign(map));
        Map<String, String> data = new HashMap<>();
        data.put("video_introduce", url);
        IntentUitls.SetIntenturl("http://121.40.186.118:10020/api/UserBase/PostEditUserInfo", map, data, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                try {
                    if (jsonObject.getInt("ErrCode") == 0) {
                        Toast.makeText(MyprofileActivity.this, "" + jsonObject.getString("ErrMsg"), Toast.LENGTH_SHORT).show();

                        MainActivity.loginBean.getResponse().setVideo_introduce(url);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bitmap = Dyz.createVideoThumbnail(url, 1);
                                videoview.setImageBitmap(bitmap);
//                                videoview.setVideoURI(Uri.parse(jsonObject.getString("Response")));
                                // controller.setMediaPlayer(videoview);
//                                videoview.setMediaController(controller);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 权限检查
     */
    private void jurisdiction() {
        // 检查权限 存储设备的读写权限
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 位置的使用权限
        int permissionCheck3 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        // 检查是否有权限
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED ||
                permissionCheck2 != PackageManager.PERMISSION_GRANTED ||
                permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
            // 动态申请权限
            ActivityCompat.requestPermissions(this, new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,// 写文件
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,// 读文件
                            Manifest.permission.CAMERA,//相机权限
                            Manifest.permission.RECORD_AUDIO//录音权限
                    },
                    REQUEST_PERMISSION);
        } else {
//            初始化
            init();
        }
    }

    private void init() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("加载中...");
        mDialog.show();
        initview();
        data();
        shipin1 = getIntent().getStringExtra("shipin");
        if (shipin1 != null) {
            scshipin(shipin1);
        }
    }

    /**
     * 处理权限请求结果
     *
     * @param requestCode  权限请求码
     * @param permissions  请求的权限列表
     * @param grantResults 请求的结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                初始化
                init();
            } else {
                Toast.makeText(this, "无权限使用", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

}
