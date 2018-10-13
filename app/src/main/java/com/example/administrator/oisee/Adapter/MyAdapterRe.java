package com.example.administrator.oisee.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.Dyz;
import com.example.administrator.oisee.View.ImagePagerActivity;
import com.example.administrator.oisee.View.MultiImageView;
import com.example.administrator.oisee.View.PhotoInfo;
import com.example.administrator.oisee.bean.Rementwo.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterRe extends RecyclerView.Adapter<MyAdapterRe.ViewHolder>  {
    private List<PhotoInfo> photolist = new ArrayList<PhotoInfo>();
    private List<Response> datas = null;
    private Context mContext;
    private Activity activity;
    public MyAdapterRe(List<com.example.administrator.oisee.bean.Rementwo.Response> datas, Context context, Activity activity) {
        this.datas = datas;
        this.mContext = mContext;
        this.activity = activity;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyAdapterRe.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.findremenitem, viewGroup, false);
        MyAdapterRe.ViewHolder vh = new MyAdapterRe.ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyAdapterRe.ViewHolder viewHolder, int position) {
        viewHolder.textViewname.setText(datas.get(position).getNick_name());
        if (!TextUtils.isEmpty(datas.get(position).getAvatar())) {
            Picasso.with(mContext).load(datas.get(position).getAvatar()).error(R.drawable.img).into(viewHolder.imageViewtouxiang);
        }
        viewHolder.textViewdizhi.setText(datas.get(position).getLocations());
        viewHolder.RetwoText.setText(datas.get(position).getContent());
        viewHolder.textd1.setText(String.valueOf(datas.get(position).getPage_view()));
        viewHolder.textd2.setText(String.valueOf(datas.get(position).getComment_count()));
        viewHolder.textd3.setText(String.valueOf(datas.get(position).getThumbup_count()));
        if (datas.get(position).getType() == 1) {
            viewHolder.videoimage.setVisibility(View.GONE);
            for (int i = 0; i < datas.get(position).getImage_list().size(); i++) {
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setUrl(datas.get(position).getImage_list().get(i));
                photolist.add(photoInfo);
            }
            viewHolder.re3.setList(photolist);
        } else if (datas.get(position).getType() == 2) {
            viewHolder.re3.setVisibility(View.GONE);

            Bitmap bitmap = Dyz.createVideoThumbnail(datas.get(position).getVideo(), 1);
            viewHolder.videoimage.setImageBitmap(bitmap);

        }
        viewHolder.re3.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //imagesize是作为loading时的图片size
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                List<String> photoUrls = new ArrayList<String>();
                for (PhotoInfo photoInfo : photolist) {
                    Log.i("Test", "photoInfo: " + photoInfo.getUrl() + "\n" + photoInfo.toString());
                    photoUrls.add(photoInfo.getUrl());
                }
                Log.e("jjjj", "" + photoUrls.toString());
                ImagePagerActivity.startImagePagerActivity((activity), photoUrls, position, imageSize);
            }
        });

    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewname;
        public ImageView imageViewtouxiang;
        public TextView textViewshijian;
        public TextView textViewdizhi;
        public TextView RetwoText;
        public TextView textd1;
        public TextView textd2;
        public TextView textd3;
        public MultiImageView re3;
        public ImageView videoimage;

        public ViewHolder(View view) {
            super(view);
            imageViewtouxiang = view.findViewById(R.id.touxiang);
            textViewname = view.findViewById(R.id.textView1);
            textViewshijian = view.findViewById(R.id.textView2);
            textViewdizhi = view.findViewById(R.id.textView3);
            RetwoText = view.findViewById(R.id.RetwoText);
            textd1 = view.findViewById(R.id.textd1);
            textd2 = view.findViewById(R.id.textd2);
            textd3 = view.findViewById(R.id.textd3);
            re3 = view.findViewById(R.id.re3);
            videoimage = view.findViewById(R.id.videoimage);
        }

    }
}
