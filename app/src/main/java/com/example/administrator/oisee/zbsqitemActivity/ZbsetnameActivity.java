package com.example.administrator.oisee.zbsqitemActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.Activity.AnchorActivity;
import com.example.administrator.oisee.Activity.SetNameActivity;
import com.example.administrator.oisee.R;
import com.example.administrator.oisee.View.MyEditText;

public class ZbsetnameActivity extends AppCompatActivity {
    private MyEditText et_set_name;
    private TextView tv_set_name_complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbsetname);
        et_set_name = findViewById(R.id.et_set_name);
        tv_set_name_complete = findViewById(R.id.tv_set_name_complete);
        tv_set_name_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_set_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ZbsetnameActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent=new Intent(ZbsetnameActivity.this, AnchorActivity.class);
                    intent.putExtra("ZBname",name);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
