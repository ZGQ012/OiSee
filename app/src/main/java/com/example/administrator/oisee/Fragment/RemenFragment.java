package com.example.administrator.oisee.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.Adapter.MyAdapter;
import com.example.administrator.oisee.Adapter.MyAdapterRe;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.PhotoInfo;
import com.example.administrator.oisee.bean.Remen.JsonRootBean;
import com.example.administrator.oisee.bean.Remen.Response;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemenFragment extends Fragment {

    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private Context mcontext;
    private JSONObject jsonObject;
    private JsonRootBean jsonRootBean;
    private MyAdapter myAdapter;
    private com.example.administrator.oisee.bean.Rementwo.JsonRootBean jsonRootBean1;
    //    private MyAdapterRe myAdapterRe;
    private PtrFrameLayout ptr_frame;
    private MediaController controller;
    private int num = 1;
    private List<PhotoInfo> photolist = new ArrayList<PhotoInfo>();
    private Dialog gobalDialog;
    private MyAdapterRe myAdapterRe;
    private RefreshLayout refreshLayout;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private ProgressDialog mProgressDialog;

    public RemenFragment() {
        // Required empty public constructor
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
        mProgressDialog = new ProgressDialog(mcontext);
        mProgressDialog.setMessage("加载数据中,请稍等...");
        mProgressDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        //创建ProgressDialog
        createProgressDialog();
        //启动线程
        executorService.execute(mRunnable);
    }

    private void data() {

        remen();
        setPtrFrameAttribute();
        if (Config.longzhuangtai == 1) {
            Re2();
            Re1();

        }

    }

    private void initview(View view) {
        mRecyclerView1 = view.findViewById(R.id.Re1);
        mRecyclerView2 = view.findViewById(R.id.Re2);
        ptr_frame = view.findViewById(R.id.ptr_frame);
        controller = new MediaController(mcontext);
    }

    //关注RecyclerView配置
    private void remen() {
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mcontext);
        LinearLayoutManager mLayoutManagerRe = new LinearLayoutManager(mcontext);

        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView2.setLayoutManager(mLayoutManagerRe);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView2.setHasFixedSize(true);
        //设置布局
        mLayoutManager = new LinearLayoutManager(mcontext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManagerRe = new LinearLayoutManager(mcontext);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView2.setLayoutManager(mLayoutManagerRe);
//        //设置Adapter
//        mRecyclerView1.setAdapter(new RemenFragment.MyAdapter(Applicon.TabLayoutTitl1));
        //  mRecyclerView2.setAdapter(new RemenFragment.MyAdapterRe(Applicon.TabLayoutTitl1));
        //设置分割线
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.HORIZONTAL));
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.VERTICAL));
    }

    //网络请求
    private void Re1() {
        Map<String, String> map = new HashMap<String, String>();
        String id = String.valueOf(MainActivity.loginBean.getResponse().getUserid());
        map.put("type", "2");
        map.put("user_id", id);
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Found/GetFoundUserList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //设置Adapter
                if (jsonRootBean.getErrCode() == 0) {
                    myAdapter = new MyAdapter(jsonRootBean.getResponse(), mcontext);
                    mRecyclerView1.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mcontext, "" + jsonRootBean.getErrMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //网络请求
    private void Re2() {
        Map<String, String> map = new HashMap<String, String>();
        String id = String.valueOf(MainActivity.loginBean.getResponse().getUserid());
        map.put("type", "2");
        map.put("user_id", id);
        map.put("pagesize", "20");
        map.put("page", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Found/GetFoundList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonRootBean1 = JsonUtils.getbean(response, com.example.administrator.oisee.bean.Rementwo.JsonRootBean.class);
                if (jsonRootBean1.getErrCode() == 0) {
                    myAdapterRe = new MyAdapterRe(jsonRootBean1.getResponse(), mcontext, getActivity());
                    //    myAdapterRe = new MyAdapterRe(jsonRootBean1.getResponse());
                    mRecyclerView2.setAdapter(myAdapterRe);
                    //myAdapterRe.notifyDataSetChanged();
                } else {
                    Toast.makeText(mcontext, "" + jsonRootBean.getErrMsg(), Toast.LENGTH_SHORT).show();
                }
                // Drawable d=Drawable.createFromPath();
//                try {
//                   jsonObject.getJSONObject("Response").getString("image_url");
//                    Picasso.with(PhoneActivity.this).load(beijings).into(target);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                // jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));
            }
        });

    }

    //刷新
    private void setPtrFrameAttribute() {
        // 头部阻尼系数
        ptr_frame.setResistanceHeader(2.0f);
        // 底部阻尼系数
        ptr_frame.setResistanceFooter(2.0f);
        // 默认1.2f，移动达到头部高度1.2倍时触发刷新操作
        ptr_frame.setRatioOfHeaderHeightToRefresh(1.2f);
        // 头部回弹时间
        ptr_frame.setDurationToCloseHeader(2000);
        // 底部回弹时间
        ptr_frame.setDurationToCloseFooter(2000);
        // 释放刷新
        ptr_frame.setPullToRefresh(false);
        // 释放时恢复到刷新状态的时间
        ptr_frame.setDurationToBackHeader(2000);
        ptr_frame.setDurationToBackFooter(2000);

        // Matrial风格头部的实现
        final MaterialHeader header = new MaterialHeader(getActivity());
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr_frame.setHeaderView(header);
        ptr_frame.addPtrUIHandler(header);

        // 经典的底部布局实现
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getActivity());
        footer.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr_frame.setFooterView(footer);
        ptr_frame.addPtrUIHandler(footer);

        // 设置支持刷新和加载更多 可以任意开启或者关闭某一个特性（开关）
        ptr_frame.setMode(PtrFrameLayout.Mode.LOAD_MORE);

        // 进入界面自动刷新
        ptr_frame.post(new Runnable() {
            @Override
            public void run() {
                // 进入界面自动刷新
                //  ptr_frame.autoRefresh();
            }
        });

        /**
         * 不用判断什么时候刷新和加载，方法里有自己的判断，适合大多数的情况
         */
        ptr_frame.setPtrHandler(new PtrDefaultHandler2() {
            // 加载更多开始会执行该方法
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                // 这里做一些加载的操作
                LoadMore();
                // 用于关闭上拉加载
                ptr_frame.refreshComplete();
            }

            // 刷新开始会执行该方法
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 这里做一些刷新操作
                Refresh();

                // 用于关闭下拉刷新
                ptr_frame.refreshComplete();
            }
        });

    }

    //上拉加载
    private void LoadMore() {
        if (Config.longzhuangtai == 1) {
            Reshangla();
         }
    }

    //下拉刷新
    private void Refresh() {

    }

    private void Reshangla() {
        num++;
        Map<String, String> map = new HashMap<String, String>();
        String id = String.valueOf(MainActivity.loginBean.getResponse().getUserid());
        map.put("type", "2");
        map.put("user_id", id);
        map.put("pagesize", "5");
        map.put("page", String.valueOf(num));
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Found/GetFoundList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                com.example.administrator.oisee.bean.Rementwo.JsonRootBean jsonRootBean2 = null;
                jsonRootBean2 = JsonUtils.getbean(response, com.example.administrator.oisee.bean.Rementwo.JsonRootBean.class);
                if (jsonRootBean2.getErrCode() == 0) {
                    if (jsonRootBean2.getResponse().size() != 0) {
                        jsonRootBean1.getResponse().addAll(jsonRootBean2.getResponse());
                        myAdapterRe.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mcontext, "没有更多数据啦", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mcontext, "" + jsonRootBean.getErrMsg(), Toast.LENGTH_SHORT).show();
                }
                // Drawable d=Drawable.createFromPath();
//                try {
//                   jsonObject.getJSONObject("Response").getString("image_url");
//                    Picasso.with(PhoneActivity.this).load(beijings).into(target);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                // jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //   spinner.setAdapter(new spannrAdapte(PhoneActivity.this, jsonRootBean));

            }
        });

    }
}
