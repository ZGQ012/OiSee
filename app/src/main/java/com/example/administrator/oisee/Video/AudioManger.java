package com.example.administrator.oisee.Video;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioManger {
    private MediaRecorder mediaRecorder;
    private String mDir;
    private String mCurrentFilepath;
    private static AudioManger mInstance;
    private boolean isPrepa;

    private AudioManger(String dir) {
        mDir = dir;
    }

    public String getCurrentFilePath() {
        return mCurrentFilepath;
    }


    //回调准备完毕
    public interface AudioStateLinener {
        void wellPrepared();
    }

    public AudioStateLinener mListener;

    public void setOnAudioStatListener(AudioStateLinener listener) {
        mListener = listener;
    }

    public static AudioManger getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManger.class) {
                if (mInstance == null) {
                    mInstance = new AudioManger(dir);
                }
            }
        }
        return mInstance;
    }

    public void preparAudio() {


        try {
            isPrepa = false;
            File dir = new File(mDir);
            if (!dir.exists())
                dir.mkdir();
            String fileName = geterateFileName();
            File file = new File(dir, fileName);
            mCurrentFilepath = file.getAbsolutePath();
            mediaRecorder = new MediaRecorder();
            //设置输出文件
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置Media的音频源为麦克风
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            //准备结束
            isPrepa = true;
            if (mListener != null) {
                mListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //随机生成文件的名称
    private String geterateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }


    public int getVoiceLevel(int maxLevel) {
        if (isPrepa) {
            try {
                return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {

            }
        }
        return 1;
    }

    public void release() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void cancel() {

        release();
        if (mCurrentFilepath != null) {
            File file = new File(mCurrentFilepath);
            file.delete();
            mCurrentFilepath = null;
        }

    }

    public static class getInstance extends AudioManger {
        public getInstance(String dir) {
            super(dir);
        }
    }
}
