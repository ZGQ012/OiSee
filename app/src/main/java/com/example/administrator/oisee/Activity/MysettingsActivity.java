package com.example.administrator.oisee.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.oisee.App.Applicon;
import com.example.administrator.oisee.Fragment.Config;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.bean.LoginBean;
import com.github.mydialog.MyDialog;

public class MysettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysettings);
        initview();
        data();
    }

    private void data() {
        tv_exit.setOnClickListener(this);
    }

    private void initview() {
        tv_exit = findViewById(R.id.tv_exit);
    }
   //退出登录
    private void exit() {
        MyDialog.Builder mDialog = new MyDialog.Builder(this);
        mDialog.setMessage("是否确认退出登录?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Config.longzhuangtai=0;
                startActivity(new Intent(MysettingsActivity.this,MainActivity.class));
                finish();
            }
        });
        mDialog.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                exit();
                break;
        }
    }
}
