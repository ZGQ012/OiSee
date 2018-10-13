package com.example.administrator.oisee.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.administrator.oisee.App.Applicon;

public class FragmentAdapterguanzhu extends FragmentAdapter {
    public FragmentAdapterguanzhu(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment=new GuanzhuFragment();//关注
                break;
            case 1:
                fragment=new RemenFragment();//热门
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Applicon.TabLayoutTitl2.length;
    }
}
