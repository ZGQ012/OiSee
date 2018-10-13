package com.example.administrator.oisee.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.R;


public class FindFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        data();

    }

    private void data() {
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FragmentAdapterguanzhu(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < Applicon.TabLayoutTitl2.length; i++) {
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            tab.setText(Applicon.TabLayoutTitl2[i]);
        //    tab.setIcon(Applicon.TabLayoutIcon[i]);
        }
    }

    private void initview(View view) {
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);

    }

}
