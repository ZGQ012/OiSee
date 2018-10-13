package com.example.administrator.oisee.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oisee.R;
import com.example.administrator.oisee.Video.AudioButton;
import com.example.administrator.oisee.Video.MediaManager;
import com.example.administrator.oisee.View.Fileurl;
import com.example.administrator.oisee.View.HttpAssistant;
import com.example.administrator.oisee.utils.RecordPlayer;
import com.example.administrator.oisee.wxapi.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.oisee.View.IntentUitls.zhuijia;
import static com.example.administrator.oisee.wxapi.GetSign.getSign;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private AudioButton mAudioButton;
    private Recorder recorder;
    private TextView button;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAudioButton = findViewById(R.id.id_recoderbutton);
        button = findViewById(R.id.id_button);
        button.setOnClickListener(this);
        mAudioButton.setAudioFinishReListener(new AudioButton.AudioFinishReListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                recorder = new Recorder(seconds, filePath);

            }
        });
    }

    class Recorder {
        float time;
        String filePath;

        public Recorder(float time, String filePath) {
            super();
            this.time = time;
            this.filePath = filePath;
        }

        public float getTime() {
            return time;
        }
        public void setTime(float time) {
            this.time = time;
        }
        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_button:
                if (recorder.filePath != null) {
                    Intent intent = new Intent(Main3Activity.this, MyprofileActivity.class);
                    intent.putExtra("path", recorder.filePath);
                    startActivity(intent);
                    finish();
                }
                //  final Map<String, File> fileMap = new HashMap<>();
//            File file = Fileurl.getFileByUri(Uri.parse(recorder.filePath), this);
//          Uri uri=  Fileurl.getImageContentUri(this, new File(recorder.filePath));
//                File file = new File(recorder.filePath);
//                fileMap.put("file", file);
//                String url = "http://121.40.186.118:10020/api/Lib/PostUploadFile";
//                Map<String, String> map = new HashMap<>();
//                map.put("rnd", "1");
//                map.put("sign", getSign(map));
//                final String UrlStr = zhuijia(url, map);
//                MediaManager.playSound(recorder.filePath, new MediaPlayer.OnCompletionListener() {
//                                    @Override
//                                    public void onCompletion(MediaPlayer mp) {
//
//                                    }
//                                });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//
//                            String respons = HttpAssistant.upLoadFilePost(UrlStr, fileMap);
//                            try {
//                                jsonObject = JsonUtils.getjsonobj(respons);
//                                String path = jsonObject.getString("Response");
//                                MediaManager.playSound(path, new MediaPlayer.OnCompletionListener() {
//                                    @Override
//                                    public void onCompletion(MediaPlayer mp) {
//
//                                    }
//                                });

//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            Log.e("123", "" + respons);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();


                break;
        }
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MediaManager.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MediaManager.resume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        MediaManager.release();
//    }
}
