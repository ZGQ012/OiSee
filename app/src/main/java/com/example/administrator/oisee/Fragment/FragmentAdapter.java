package com.example.administrator.oisee.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.oisee.App.Applicon;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) { super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment=new TeachingFragment();//教学
                break;
            case 1:
                fragment=new BroadcastFragment();//直播
                break;
            case 2:
                fragment=new FindFragment();//发现
                break;
            case 3:
                fragment=new MymFragment();//我的
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Applicon.TabLayoutTitl.length;
    }
}
