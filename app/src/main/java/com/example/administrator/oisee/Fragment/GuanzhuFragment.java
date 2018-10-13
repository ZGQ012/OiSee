package com.example.administrator.oisee.Fragment;

;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.wxapi.GetSign;
import com.example.administrator.oisee.wxapi.JsonUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class GuanzhuFragment extends Fragment {
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private Context mcontext;
    private JSONObject jsonObject;

    public GuanzhuFragment() {
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
        return inflater.inflate(R.layout.fragment_guanzhu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        data();
    }

    private void data() {
        guanzhu();
        if(Config.longzhuangtai==1){
            Re2();
            Re1();
        }

    }

    private void initview(View view) {
        mRecyclerView1 = view.findViewById(R.id.Re1);
        mRecyclerView2 = view.findViewById(R.id.Re2);

    }

    //关注RecyclerView配置
    private void guanzhu() {
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
        //设置Adapter
        mRecyclerView1.setAdapter(new GuanzhuFragment.MyAdapter(Applicon.TabLayoutTitl1));
        mRecyclerView2.setAdapter(new GuanzhuFragment.MyAdapterRe(Applicon.TabLayoutTitl1));
        //设置分割线
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.HORIZONTAL));
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.VERTICAL));
    }

    //关注 adapter  第一个
    public class MyAdapter extends RecyclerView.Adapter<GuanzhuFragment.MyAdapter.ViewHolder> {
        public String[] datas = null;

        public MyAdapter(String[] datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public GuanzhuFragment.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.findguanzhuitemone, viewGroup, false);
            GuanzhuFragment.MyAdapter.ViewHolder vh = new GuanzhuFragment.MyAdapter.ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(GuanzhuFragment.MyAdapter.ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas[position]);
            //  viewHolder.imageView.setImageDrawable(R.drawable.);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                mTextView = view.findViewById(R.id.oneName);

            }
        }
    }

    //关注 adapter  第二个
    public class MyAdapterRe extends RecyclerView.Adapter<GuanzhuFragment.MyAdapterRe.ViewHolder> {
        public String[] datas = null;

        public MyAdapterRe(String[] datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public GuanzhuFragment.MyAdapterRe.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.findgunzhuitem, viewGroup, false);
            GuanzhuFragment.MyAdapterRe.ViewHolder vh = new GuanzhuFragment.MyAdapterRe.ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(GuanzhuFragment.MyAdapterRe.ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas[position]);
            //  viewHolder.imageView.setImageDrawable(R.drawable.);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                mTextView = view.findViewById(R.id.RetwoText);

            }
        }
    }
    //网络请求
    private void Re1() {
        Map<String, String> map = new HashMap<String, String>();
        String id = String.valueOf(MainActivity.loginBean.getResponse().getUserid());
        map.put("type", "1");
        map.put("user_id", id);
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Found/GetFoundUserList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);

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
    //网络请求
    private void Re2() {
        Map<String, String> map = new HashMap<String, String>();
        String id = String.valueOf(MainActivity.loginBean.getResponse().getUserid());
        map.put("type", "1");
        map.put("user_id", id);
        map.put("pagesize", "0");
        map.put("page", "1");
        map.put("sign", GetSign.getSign(map));
        OkHttpUtils.get().url("http://121.40.186.118:10020/api/Found/GetFoundList").params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                jsonObject = JsonUtils.getjsonobj(response);

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
