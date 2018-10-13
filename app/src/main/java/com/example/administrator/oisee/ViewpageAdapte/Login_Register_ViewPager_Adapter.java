package com.example.administrator.oisee.ViewpageAdapte;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.bean.LbBean;
import com.example.administrator.oisee.bean.Shuffling_list;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Login_Register_ViewPager_Adapter extends PagerAdapter {
    public List<Shuffling_list> list;
    public Activity activity;

    public Login_Register_ViewPager_Adapter(LbBean lbBean, Activity activity) {
        this.list =lbBean.getResponse().getShuffling_list();
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        int newposition = position % list.size();
        //动态创建imageview对象
        ImageView iv = new ImageView(container.getContext());
        //设置imageview显示的图片
        Picasso.with(activity).load(list.get(newposition).getImage_url()).into(iv);
//        iv.setBackgroundResource(Config.TabLayoutIcon[newposition]);
        //将视图添加到容器中
        container.addView(iv);
        return iv;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}