package com.example.administrator.oisee.wxapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.JsonRootBean;
import com.example.administrator.oisee.bean.Response;
import com.google.gson.JsonArray;

import java.util.List;

public class spannrAdapte extends BaseAdapter {
    private List<Response> list;
    private Context mycontext;
    private JsonRootBean jsonRootBean;

    public spannrAdapte(Context context, JsonRootBean jsonRootBean) {
        this.mycontext = context;
        this.list = jsonRootBean.getResponse();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mycontext);
        tv.setText(list.get(position).getTitle());
        tv.setTextSize(20.0f);
        tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        return tv;
    }
}
