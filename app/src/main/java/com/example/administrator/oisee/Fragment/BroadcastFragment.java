package com.example.administrator.oisee.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.oisee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcastFragment extends Fragment {


    public BroadcastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcast, container, false);
    }

}
