package com.example.administrator.oisee.Video;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oisee.R;

public class DilogManager {
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;

    private TextView mLable;
    private Context mContext;

    public DilogManager(Context context) {
        mContext = context;
    }

    public void showReDialog() {
        mDialog = new Dialog(mContext, R.style.Theam_AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialig, null);
        mDialog.setContentView(view);
        mIcon = mDialog.findViewById(R.id.re_dialog_icon);
        mVoice = mDialog.findViewById(R.id.re_dialog_vioice);
        mLable = mDialog.findViewById(R.id.dilog_lable);
        mDialog.show();
    }
     public void recording(){
        if(mDialog!=null&&mDialog.isShowing()){
           mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.recorder);
            mLable.setText("手指上滑，取消发送");
        }
     }
    public void wantToCan() {
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.cancel);
            mLable.setText("松开手指，取消发送");
        }
    }

    public void tooShort() {
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.voice_to_short);
            mLable.setText("录音时间过短");
        }
    }

    public void dimissDialog() {
        if(mDialog!=null&&mDialog.isShowing()){
          mDialog.dismiss();
          mDialog=null;
        }

    }
   //通过level更新vioce上的图片
    public void update(int level) {
        if(mDialog!=null&&mDialog.isShowing()){
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLable.setVisibility(View.VISIBLE);
            int resId=mContext.getResources().getIdentifier("v"+level,"drawable",mContext.getPackageName());
            mVoice.setImageResource(resId);
        }

    }
}
