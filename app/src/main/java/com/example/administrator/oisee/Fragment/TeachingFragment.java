package com.example.administrator.oisee.Fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.Activity.PhoneActivity;
import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.RemenDialog.GobalDialog;
import com.example.administrator.oisee.ViewpageAdapte.Login_Register_ViewPager_Adapter;
import com.example.administrator.oisee.bean.LbBean;
import com.example.administrator.oisee.bean.LoginBean;
import com.example.administrator.oisee.bean.OnevOne.JsonRootBean;
import com.example.administrator.oisee.bean.OnevOne.Response;
import com.example.administrator.oisee.bean.Shuffling_list;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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


public class TeachingFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private boolean islooper = true;
    private boolean twoRe = true;
    private int Page = 1;
    private Handler handler;
    private RecyclerView mRecyclerView;
    private RecyclerView Re_tuijian_jiaoshi;
    int i;
    private LinearLayout linearLayout;
    private Context mcontext;
    private boolean b = true;
    private ImageView imageView;
    private NestedScrollView scrolv;
    private ImageView top;
    private ImageView qiehuanRe;
    private JSONObject jsonObject;
    private LbBean lbBean;
    private PtrFrameLayout ptr_frame;
    public MyAdapter myAdapter;
    public MyAdapterjiaoshi myAdapterjiaoshi;
    private List<Shuffling_list> lists;
    public MyAdaptertwo myAdaptertwo;
    private JsonRootBean jsonRootBean;

    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private int KEEP_ALIVE_TIME = 1;
    private TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingDeque<Runnable>(128));
    private ProgressDialog mProgressDialog;
    private Dialog dialog;

    public TeachingFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_teaching, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initview(view);//初始化控件
        ansy();
        looper();
          //创建ProgressDialog
    //    createProgressDialog();
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
     * 创建ProrgressDialog
     */
    private void createProgressDialog() {

        mProgressDialog = new ProgressDialog(mcontext);
        mProgressDialog.setMessage("加载数据中,请稍等...");
        mProgressDialog.show();
    }

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

    private void data() {
        lunbo();
        jiaoxueRe();
        setPtrFrameAttribute();
        yvy();
    }


    //教学RecyclerView配置
    private void jiaoxueRe() {
        Shuffling_list shuffling_list = new Shuffling_list();
        shuffling_list.setImage_url("123");
        lists = new ArrayList<>();
        lists.add(shuffling_list);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManagerjiaoshi = new LinearLayoutManager(mcontext);
        Re_tuijian_jiaoshi.setLayoutManager(mLayoutManagerjiaoshi);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        Re_tuijian_jiaoshi.setHasFixedSize(true);
        //设置布局
        LinearLayoutManager mLayoutManager = new GridLayoutManager(mcontext, 3);
        mLayoutManagerjiaoshi = new LinearLayoutManager(mcontext);
        mLayoutManagerjiaoshi.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Re_tuijian_jiaoshi.setLayoutManager(mLayoutManagerjiaoshi);
//        //设置Adapter
//        myAdapter = new MyAdapter(lists);
//        mRecyclerView.setAdapter(myAdapter);
        myAdapterjiaoshi = new MyAdapterjiaoshi(lists);
        Re_tuijian_jiaoshi.setAdapter(myAdapterjiaoshi);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.VERTICAL));
        Re_tuijian_jiaoshi.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.HORIZONTAL));
    }

    private void initview(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        linearLayout = view.findViewById(R.id.layout);
        mRecyclerView = view.findViewById(R.id.rv_jiaoxue);
        Re_tuijian_jiaoshi = view.findViewById(R.id.rv_tuijian_jiaoshi);
        imageView = view.findViewById(R.id.qiehuanRe);
        scrolv = view.findViewById(R.id.scrolv);
        top = view.findViewById(R.id.top);
        qiehuanRe = view.findViewById(R.id.qiehuanRe);
        ptr_frame = view.findViewById(R.id.ptr_frame);
        imageView.setOnClickListener(this);
        top.setOnClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newposition = position % lbBean.getResponse().getShuffling_list().size();
        linearLayout.getChildAt(newposition).setEnabled(true);
        linearLayout.getChildAt(i).setEnabled(false);
        i = newposition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * viewpager自动滑动
     */
    private void looper() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (islooper) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    /**
     * 设置viewpager进度点
     */
    private void dot(LbBean lbBean) {
        View view;
        for (int j = 0; j < lbBean.getResponse().getShuffling_list().size(); j++) {
            //动态创建圆点
            view = new ImageView(mcontext);
            //设置圆点路径
            view.setBackgroundResource(R.drawable.shape_indicator_selector);
            //大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            if (j != 0) {
                params.leftMargin = 15;
            }
            view.setLayoutParams(params);
            //设置第一个为实心圆
            // view.setEnabled(false);
            view.setEnabled(j == 0);
            //添加到容器
            linearLayout.addView(view);
        }
    }

    @SuppressLint("HandlerLeak")
    public void ansy() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qiehuanRe:
                if (b == false) {
                    //点击后想要变成什么要的布局样式
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mcontext, 3));
                    mRecyclerView.setAdapter(myAdapter);
                    //给布尔值重新赋值
                    b = true;
                    //给点击按钮的图片重新赋值
                    qiehuanRe.setImageResource(R.drawable.menu);
                } else if (b == true) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
                    myAdaptertwo = new MyAdaptertwo(jsonRootBean.getResponse());
                    mRecyclerView.setAdapter(myAdaptertwo);
                    myAdaptertwo.notifyDataSetChanged();
                    //给布尔值重新赋值
                    b = false;
                    //给点击按钮的图片重新赋值
                    qiehuanRe.setImageResource(R.drawable.buju);
                }
                break;
            case R.id.top:
                //回到顶部
                scrolv.scrollTo(0, 0);
                break;
        }
    }

    //教学 adapter
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public List<Response> datas = null;

        public MyAdapter(List<Response> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tuijianjiaoxue, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.jiaoxuename.setText(datas.get(position).getName());
            viewHolder.guojiText.setText(datas.get(position).getNationality());
            Picasso.with(getActivity()).load(datas.get(position).getAvatar()).into(viewHolder.jiaoxueimageView);
            Picasso.with(getActivity()).load(datas.get(position).getNational_flag()).into(viewHolder.guojiimageView);
            //  viewHolder.imageView.setImageDrawable(R.drawable.);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView jiaoxuename;
            public ImageView jiaoxueimageView;
            public TextView guojiText;
            public ImageView guojiimageView;

            public ViewHolder(View view) {
                super(view);
                jiaoxuename = view.findViewById(R.id.jiaoxueText);
                jiaoxueimageView = view.findViewById(R.id.jiaoxueImage);
                guojiText = view.findViewById(R.id.jiaoxueguoji);
                guojiimageView = view.findViewById(R.id.jiaoxuegjimage);

            }
        }
    }

    //教学 adapter two
    public class MyAdaptertwo extends RecyclerView.Adapter<MyAdaptertwo.ViewHolder> {
        public List<Response> datas = null;

        public MyAdaptertwo(List<Response> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tuijianjiaoxuetwo, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextViewname.setText(datas.get(position).getName());
            viewHolder.mTextViewqianming.setText(datas.get(position).getDescribe_experience());
            viewHolder.mTextViewpice.setText(String.valueOf(datas.get(position).getPrice()));
            viewHolder.mTextViewguoji.setText(datas.get(position).getNationality());
            Picasso.with(getActivity()).load(datas.get(position).getAvatar()).into(viewHolder.imageView);
            Picasso.with(getActivity()).load(datas.get(position).getNational_flag()).into(viewHolder.imageViewguoji);
            //  viewHolder.imageView.setImageDrawable(R.drawable.);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewname;
            public TextView mTextViewqianming;
            public TextView mTextViewpice;
            public TextView mTextViewguoji;
            public ImageView imageView;
            public ImageView imageViewguoji;

            public ViewHolder(View view) {
                super(view);
                mTextViewname = view.findViewById(R.id.textname);
                mTextViewqianming = view.findViewById(R.id.Retotext);
                mTextViewpice = view.findViewById(R.id.piceText);
                mTextViewguoji = view.findViewById(R.id.guojiText);
                imageView = view.findViewById(R.id.imageView);
                imageViewguoji = view.findViewById(R.id.guojiImage);
            }
        }

    }

    //教学 adapterjiaoshi
    public class MyAdapterjiaoshi extends RecyclerView.Adapter<MyAdapterjiaoshi.ViewHolder> {
        public List<Shuffling_list> datas = null;


        public MyAdapterjiaoshi(List<Shuffling_list> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tuijianjiaoshi, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas.get(position).getImage_url());
            //  viewHolder.imageView.setImageDrawable(R.drawable.);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                mTextView = view.findViewById(R.id.jiaoshiText);

            }
        }
    }

    //获取首页轮播网路图片
    private void lunbo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Teaching/GetTeachingTop").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                lbBean = JsonUtils.getbean(response, LbBean.class);
                viewPager.setAdapter(new Login_Register_ViewPager_Adapter(lbBean, getActivity()));
                viewPager.addOnPageChangeListener(TeachingFragment.this);
                dot(lbBean);
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

    //1v1教学Re
    private void yvy() {
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;// 获取昨天日期
        String mou = mYear + "-" + mMonth + "-" + mDay;
        Map<String, String> map = new HashMap<String, String>();
        map.put("language_id", "0");
        map.put("nationality_id", "0");
        map.put("price_sort", "0");
        map.put("kaike_time", mou);
        map.put("pagesize", "20");
        map.put("page", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Teaching/GetOneVOneList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                jsonRootBean = JsonUtils.getbean(response, JsonRootBean.class);
                //设置Adapter
                myAdapter = new MyAdapter(jsonRootBean.getResponse());
                myAdapter.setHasStableIds(true);
                mRecyclerView.setAdapter(myAdapter);

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

    //1v1教学Re ++
    private void yvyNum() {
        int num = 2;
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;// 获取昨天日期
        String mou = mYear + "-" + mMonth + "-" + mDay;
        Map<String, String> map = new HashMap<String, String>();
        map.put("language_id", "0");
        map.put("nationality_id", "0");
        map.put("price_sort", "0");
        map.put("kaike_time", mou);
        map.put("course_type", "0");
        map.put("kaike_time", "course_level");
        map.put("pagesize", "20");
        map.put("page", "1");
        map.put("sign", GetSign.getSign(map));
        num++;
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Teaching/GetOneVOneList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("request", "" + request);

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);
                JsonRootBean jsonRootBean1 = null;
                jsonRootBean1 = JsonUtils.getbean(response, JsonRootBean.class);
                jsonRootBean.getResponse().addAll(jsonRootBean1.getResponse());
                //设置Adapter
                myAdaptertwo = new MyAdaptertwo(jsonRootBean.getResponse());
                mRecyclerView.setAdapter(myAdaptertwo);
                myAdapter.notifyDataSetChanged();
                myAdaptertwo.notifyDataSetChanged();

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
        ptr_frame.setMode(PtrFrameLayout.Mode.BOTH);

        // 进入界面自动刷新
        ptr_frame.post(new Runnable() {
            @Override
            public void run() {
                // 进入界面自动刷新
                //   ptr_frame.autoRefresh();
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
//        Shuffling_list shuffling_list = new Shuffling_list();
//        shuffling_list.setImage_url("456");
//        lists.add(shuffling_list);
        yvyNum();
//        myAdapterjiaoshi.notifyDataSetChanged();
        //  myAdapter.notifyDataSetChanged();
        if (b == false) {
            myAdaptertwo.notifyDataSetChanged();
        }
    }

    //下拉刷新
    private void Refresh() {
        Shuffling_list shuffling_list = new Shuffling_list();
        shuffling_list.setImage_url("456");
        lists.add(shuffling_list);
        myAdapterjiaoshi.notifyDataSetChanged();

//        myAdapter.notifyDataSetChanged();
//
//        if (b == true) {
//            myAdaptertwo.notifyDataSetChanged();
//
//        }

    }
}
