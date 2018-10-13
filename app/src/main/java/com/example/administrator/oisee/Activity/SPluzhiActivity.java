package com.example.administrator.oisee.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.oisee.R;

public class SPluzhiActivity extends AppCompatActivity implements SurfaceHolder.Callback {
   private SurfaceHolder mSurfaceHolder;
    private  SurfaceView mSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spluzhi);
        mSurfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        //设置屏幕分辨率
        mSurfaceHolder.setFixedSize(100, 100);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
