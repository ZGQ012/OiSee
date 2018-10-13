package com.example.administrator.oisee.Video;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.oisee.R;

public class AudioButton extends android.support.v7.widget.AppCompatButton implements AudioManger.AudioStateLinener {
    private static final int Y = 50;
    private static final int STAT_NOM = 1;
    private static final int STAT_RE = 2;
    private static final int STAT_WANT = 3;

    private int mCu = STAT_NOM;
    //已经开始录音
    private boolean isRecoding = false;

    private DilogManager mDilogManager;
    private AudioManger mAudioManger;
    private float mTime;
    //是否触发longclick
    private boolean mReady;

    public AudioButton(Context context) {
        this(context, null);
    }

    public AudioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDilogManager = new DilogManager(getContext());
        String dir = Environment.getExternalStorageDirectory() + "/jkx_audios";
        mAudioManger = new AudioManger.getInstance(dir);
        mAudioManger.setOnAudioStatListener(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManger.preparAudio();
                return false;
            }
        });
    }

    //录音完成后的回调
    public interface AudioFinishReListener {
        void onFinish(float seconds, String filePath);
    }

    private AudioFinishReListener mListener;

    public void setAudioFinishReListener(AudioFinishReListener listener) {
        mListener = listener;
    }

    //获取音量大小
    private Runnable mgetVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecoding) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private static final int MSG_AUDIO_RE = 0X110;
    private static final int MSG_VOICE_CHANGED = 0X111;
    private static final int MSG_DIALOG_DIMISS = 0X112;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_RE:
                    //TODO 真正显示在audio以后
                    mDilogManager.showReDialog();
                    isRecoding = true;
                    new Thread(mgetVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mDilogManager.update(mAudioManger.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                     mDilogManager.dimissDialog();
                    break;
            }
        }

        ;
    };

    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_RE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                changstate(STAT_RE);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecoding) {
                    //根据x,y的坐标,判断是否想要取消
                    if (wantTocancel(x, y)) {
                        changstate(STAT_WANT);
                    } else {
                        changstate(STAT_RE);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                if (!isRecoding || mTime < 0.6f) {
                    mDilogManager.tooShort();
                    mAudioManger.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                } else if (mCu == STAT_RE) {//正常录制结束
                    //reles
                    mDilogManager.dimissDialog();
                    mAudioManger.release();
                    if(mListener!=null){
                        mListener.onFinish(mTime,mAudioManger.getCurrentFilePath());
                    }
                    //callback
                } else if (mCu == STAT_WANT) {
                    mDilogManager.dimissDialog();
                    mAudioManger.cancel();
                    //cancle

                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    //恢复状态及标志位
    private void reset() {
        isRecoding = false;
        mReady = false;

        mTime = 0;
        changstate(STAT_NOM);

    }

    private boolean wantTocancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -y || y > getHeight() + y) {
            return true;
        }
        return false;
    }

    private void changstate(int statRe) {
        if (mCu != statRe) {
            mCu = statRe;
            switch (statRe) {
                case STAT_NOM:
                    //TODO
                    isRecoding = true;
                    setBackgroundResource(R.drawable.btn_re_nom);
                    setText(R.string.str_re_nom);
                    break;
                case STAT_RE:
                    setBackgroundResource(R.drawable.btn_reding);
                    setText(R.string.str_re_recding);
                    if (isRecoding) {

                        mDilogManager.recording();
                    }
                    break;
                case STAT_WANT:
                    setBackgroundResource(R.drawable.btn_reding);
                    setText(R.string.str_want);
                    mDilogManager.wantToCan();
                    break;
            }
        }
    }


}
