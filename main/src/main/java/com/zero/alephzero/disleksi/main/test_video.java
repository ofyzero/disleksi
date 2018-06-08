package com.zero.alephzero.disleksi.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class test_video extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video);

        final Button play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v) {
                VideoView videoView = (VideoView)findViewById(R.id.videoView);

                videoView.setVideoPath("android.resource://" + getPackageName()+ "/" +  R.raw.test);
                videoView.requestFocus();
                videoView.start();
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }




}
