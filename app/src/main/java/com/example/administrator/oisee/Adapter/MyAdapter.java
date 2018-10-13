package com.example.administrator.oisee.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.Fragment.RemenFragment;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.Remen.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public List<Response> datas = null;
    public Context mComtext;

    public MyAdapter(List<Response> datas, Context mComtext) {
        this.datas = datas;
        this.mComtext = mComtext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.findremenone, viewGroup, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {

        viewHolder.mTextView.setText(datas.get(position).getNick_name());
        if (!TextUtils.isEmpty(datas.get(position).getAvatar())) {
            Picasso.with(mComtext).load(datas.get(position).getAvatar()).error(R.drawable.img).into(viewHolder.imageView);
        }


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
            mTextView = view.findViewById(R.id.oneName);
            imageView = view.findViewById(R.id.oneImageview);
        }
    }
}
