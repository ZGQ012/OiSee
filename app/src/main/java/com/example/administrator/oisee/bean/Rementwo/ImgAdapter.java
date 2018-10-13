package com.example.administrator.oisee.bean.Rementwo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.Dyz;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lj269 on 2018/10/12.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgViewHolder>{
    private List<String> urls;
    private Context mContext;
    private int type;

    public ImgAdapter(List<String> urls, Context context,int type) {
        this.urls = urls;
        this.mContext = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        return new ImgViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgViewHolder holder, int position) {
        holder.BindData(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ImgViewHolder extends RecyclerView.ViewHolder{

        public ImgViewHolder(View itemView) {
            super(itemView);

        }

        public void BindData(String url){
            ImageView imageView = (ImageView) itemView;
            if(type==2){
                Bitmap bitmap = Dyz.createVideoThumbnail(url, 1);
                imageView.setImageBitmap(bitmap);
            }else{
                Picasso.with(mContext).load(url).error(R.mipmap.ic_launcher).into(imageView);
            }
        }
    }
}
