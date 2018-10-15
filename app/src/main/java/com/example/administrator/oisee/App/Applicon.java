package com.example.administrator.oisee.App;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.RemenDialog.GobalDialog;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iknow.android.utils.BaseUtils;

public class Applicon extends Application {
    Context context;
    public static int[] TabLayoutIcon;
    public static String[] TabLayoutTitl;
    public static String[] TabLayoutTitl1;
    public static String[] TabLayoutTitl2;
    public static List<String> listxingbie = new ArrayList<>();
    public JSONObject jsonObject;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        MyQQShare.setAppId("1107835202");
        MyWXShare.setAppId("wxaee1f6934896436a", null);
        TabLayoutIcon = new int[]{R.drawable.lock, R.drawable.lock, R.drawable.lock, R.drawable.lock};
        TabLayoutTitl = new String[]{context.getString(R.string.jiaoxue), context.getString(R.string.zhibo), context.getString(R.string.faxian), context.getString(R.string.wode)};
        TabLayoutTitl2 = new String[]{"关注", "热门"};
        TabLayoutTitl1 = new String[]{"订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单", "订单"};
        listxingbie.add("男");
        listxingbie.add("女");
        BaseUtils.init(this);
        initImageLoader(this);
        initFFmpegBinary(this);
    }

    public static void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private void initFFmpegBinary(Context context) {

        try {
            FFmpeg.getInstance(context).loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }


}
