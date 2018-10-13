package com.example.administrator.oisee.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.oisee.Activity.AnchorActivity;
import com.example.administrator.oisee.Activity.FeedbackActivity;
import com.example.administrator.oisee.Activity.MycollectionActivity;
import com.example.administrator.oisee.Activity.MymentActivity;
import com.example.administrator.oisee.Activity.MyprofileActivity;
import com.example.administrator.oisee.Activity.MysettingsActivity;
import com.example.administrator.oisee.Activity.MywalletActivity;
import com.example.administrator.oisee.MainActivity;
import com.example.administrator.oisee.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MymFragment extends Fragment implements View.OnClickListener {


    private LinearLayout wodeyuyue;
    private LinearLayout wodekecheng;
    private LinearLayout wodeqianbao;
    private LinearLayout yaoqinghaoyou;
    private LinearLayout wodejinbi;
    private LinearLayout wodejifen;
    private LinearLayout wodeyingxiao;
    private LinearLayout jiaoshishenqing;
    private TextView zhuboshenqing;
    private LinearLayout wodeshoucang;
    private LinearLayout wodedingyue;
    private LinearLayout youhuijuan;
    private LinearLayout bangzhuzhongxin;
    private LinearLayout fankuijianyi;
    private TextView name;
    private ImageView url;
    private LinearLayout mylin;
    private ImageView toolbar_image;

    public MymFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mym, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);//初始化控件
        data();
    }

    private void data() {
        wodeyuyue.setOnClickListener(this);
        wodekecheng.setOnClickListener(this);
        wodeqianbao.setOnClickListener(this);
        yaoqinghaoyou.setOnClickListener(this);
        wodejinbi.setOnClickListener(this);
        wodejifen.setOnClickListener(this);
        wodeyingxiao.setOnClickListener(this);
        jiaoshishenqing.setOnClickListener(this);
        zhuboshenqing.setOnClickListener(this);
        wodeshoucang.setOnClickListener(this);
        wodedingyue.setOnClickListener(this);
        youhuijuan.setOnClickListener(this);
        bangzhuzhongxin.setOnClickListener(this);
        fankuijianyi.setOnClickListener(this);
        mylin.setOnClickListener(this);
        toolbar_image.setOnClickListener(this);
        Settinginformation();
    }

    //设置资料
    private void Settinginformation() {
        name.setText(MainActivity.name);
        Picasso.with(getActivity()).load(MainActivity.url).into(url);
    }

    private void initview(View view) {
        wodeyuyue = view.findViewById(R.id.wodeyuyue);
        wodekecheng = view.findViewById(R.id.wodekecheng);
        wodeqianbao = view.findViewById(R.id.wodeqianbao);
        yaoqinghaoyou = view.findViewById(R.id.yaoqinghaoyou);
        wodejinbi = view.findViewById(R.id.wodejinbi);
        wodejifen = view.findViewById(R.id.wodejifen);
        wodeyingxiao = view.findViewById(R.id.wodeyingxiao);
        jiaoshishenqing = view.findViewById(R.id.jiaoshishenqing);
        zhuboshenqing = view.findViewById(R.id.zhuboshenqing);
        wodeshoucang = view.findViewById(R.id.wodeshoucang);
        wodedingyue = view.findViewById(R.id.wodedingyue);
        youhuijuan = view.findViewById(R.id.youhuijuan);
        bangzhuzhongxin = view.findViewById(R.id.bangzhuzhongxin);
        fankuijianyi = view.findViewById(R.id.fanjian);
        name = view.findViewById(R.id.name);
        url = view.findViewById(R.id.url);
        mylin = view.findViewById(R.id.mylin);
        toolbar_image= view.findViewById(R.id.toolbar_image);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wodeyuyue:
                //我的预约
                startActivity(new Intent(getActivity(), MymentActivity.class));
                break;
            case R.id.wodekecheng:
                //我的课程
                //startActivity(new Intent(getActivity(), MymentActivity.class));
                break;
            case R.id.wodeqianbao:
                //我的钱包
                startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.yaoqinghaoyou:
                //邀请好友
                //  startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.wodejinbi:
                //我的金币
                // startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.wodejifen:
                //我的积分
                //startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.wodeyingxiao:
                //我的营销
                //  startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.jiaoshishenqing:
                //教师申请
                //startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.zhuboshenqing:
                //主播申请
                startActivity(new Intent(getActivity(), AnchorActivity.class));
                break;
            case R.id.wodeshoucang:
                //我的收藏
                startActivity(new Intent(getActivity(), MycollectionActivity.class));
                break;
            case R.id.wodedingyue:
                //我的订阅
                // startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.youhuijuan:
                //优惠劵
                //  startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.bangzhuzhongxin:
                //帮助中心
                //  startActivity(new Intent(getActivity(), MywalletActivity.class));
                break;
            case R.id.fanjian:
                //反馈与建议
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.mylin:
                //反馈与建议
                startActivity(new Intent(getActivity(), MyprofileActivity.class));
                break;
            case R.id.toolbar_image:
                //设置
                startActivity(new Intent(getActivity(), MysettingsActivity.class));
                break;


        }
    }
}
