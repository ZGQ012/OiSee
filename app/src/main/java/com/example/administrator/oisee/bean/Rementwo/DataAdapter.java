package com.example.administrator.oisee.bean.Rementwo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.oisee.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj269 on 2018/10/12.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private List<Response> responses;
    private Context mContext;
    private final LayoutInflater inflater;

    public DataAdapter(List<Response> responses, Context mContext) {
        this.responses = responses;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void update(List<Response> data) {
        responses.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.findremenitem, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.BindData(responses.get(position));
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewname;
        public ImageView imageViewtouxiang;
        public TextView textViewshijian;
        public TextView textViewdizhi;
        public TextView RetwoText;
        public TextView textd1;
        public TextView textd2;
        public TextView textd3;
        public RecyclerView re3;


        public DataViewHolder(View view) {
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
            re3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }

        public void BindData(Response response) {
            textViewname.setText(response.getNick_name());
            if (!TextUtils.isEmpty(response.getAvatar())) {
                Picasso.with(mContext).load(response.getAvatar()).error(R.drawable.img).into(imageViewtouxiang);
            }
            textViewdizhi.setText(response.getLocations());
            RetwoText.setText(response.getContent());
            textd1.setText(String.valueOf(response.getPage_view()));
            textd2.setText(String.valueOf(response.getComment_count()));
            textd3.setText(String.valueOf(response.getThumbup_count()));
            List<String> urls = new ArrayList<>();
            if (response.getType() == 2) {
                urls.add(response.getVideo());
            } else {
                urls.addAll(response.getImage_list());
            }
            ImgAdapter imgAdapter = new ImgAdapter(urls, mContext, response.getType());
            re3.setAdapter(imgAdapter);
        }
    }
}
