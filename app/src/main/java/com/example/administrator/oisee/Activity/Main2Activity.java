package com.example.administrator.oisee.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.Fragment.TeachingFragment;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.bean.LbBean;
import com.example.administrator.oisee.bean.LoginBean;
import com.example.administrator.oisee.bean.Resulh;
import com.example.administrator.oisee.bean.Shuffling_list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class Main2Activity extends AppCompatActivity{
    private PtrFrameLayout ptr_frame;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private List<String> data = new ArrayList<>();
    private Random mRandom = new Random();
    private MyAdaptertwo adaptertwo;
    private MyAdapterone adapterone;
     private LbBean lbBean=new LbBean();
    private List<Shuffling_list> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Shuffling_list shuffling_list=new Shuffling_list();
        shuffling_list.setImage_url("123");
        lists = new ArrayList<>();
        lists.add(shuffling_list);
        //lbBean.getResponse().getShuffling_list().add((Shuffling_list) lists);
        // 获取PtrFrameLayout控件
        ptr_frame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        recyclerView = findViewById(R.id.rv_tuijian_jiaoshi);
        recyclerView1 = findViewById(R.id.rv_jiaoxue);
        // PtrFrameLayout属性设置及刷新加载的实现
        setPtrFrameAttribute();
        for (int i = 0; i < 3; i++) {
            data.add("皮皮熊\t\t" + i);
        }
        LinearLayoutManager mLayoutManagerjiaoshi = new LinearLayoutManager(this);
        mLayoutManagerjiaoshi.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManagerjiaoshi);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        adaptertwo = new MyAdaptertwo(lists);
       // adaptertwo = new MyAdaptertwo(data);
        recyclerView.setAdapter(adaptertwo);
        adapterone = new MyAdapterone(data);
        recyclerView1.setAdapter(adapterone);

    }

    /***
     * 必要属性设置及刷新加载的实现
     */
    private void setPtrFrameAttribute() {
        // 头部阻尼系数
        ptr_frame.setResistanceHeader(1.7f);
        // 底部阻尼系数
        ptr_frame.setResistanceFooter(1.7f);
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
        final MaterialHeader header = new MaterialHeader(this);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr_frame.setHeaderView(header);
        ptr_frame.addPtrUIHandler(header);

        // 经典的底部布局实现
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(this);
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
                ptr_frame.autoRefresh();
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
        // 在底部添加数据  修改数据源
        data.addAll(Arrays.asList("张三丰\t" + mRandom.nextInt(10), "二蛋\t" + mRandom.nextInt(10)));
        // 同属适配器更新
        adaptertwo.notifyDataSetChanged();
        adapterone.notifyDataSetChanged();
    }

    //下拉刷新
    private void Refresh() {
        data.addAll(0, Arrays.asList("皮皮虾\t" + mRandom.nextInt(10), "倒霉熊\t" + mRandom.nextInt(10)));
        Shuffling_list shuffling_list=new Shuffling_list();
        shuffling_list.setImage_url("456");
        lists.add(shuffling_list);


        adaptertwo.notifyDataSetChanged();
         adapterone.notifyDataSetChanged();
    }

    public class MyAdaptertwo extends RecyclerView.Adapter<Main2Activity.MyAdaptertwo.ViewHolder> {
        public List<Shuffling_list> datas = null;

        public MyAdaptertwo(List<Shuffling_list> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public Main2Activity.MyAdaptertwo.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tuijianjiaoshi, viewGroup, false);
            Main2Activity.MyAdaptertwo.ViewHolder vh = new Main2Activity.MyAdaptertwo.ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(Main2Activity.MyAdaptertwo.ViewHolder viewHolder, int position) {
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

    public class MyAdapterone extends RecyclerView.Adapter<Main2Activity.MyAdapterone.ViewHolder> {
        public List<String> datas = null;

        public MyAdapterone(List<String> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public Main2Activity.MyAdapterone.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tuijianjiaoxuetwo, viewGroup, false);
            Main2Activity.MyAdapterone.ViewHolder vh = new Main2Activity.MyAdapterone.ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(Main2Activity.MyAdapterone.ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas.get(position));
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
                mTextView = view.findViewById(R.id.Retotext);

            }
        }
    }

}
