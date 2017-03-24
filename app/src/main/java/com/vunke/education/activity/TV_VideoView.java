package com.vunke.education.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vunke.education.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class TV_VideoView extends AppCompatActivity {
    String url = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/index.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170324142437&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170324142437&encrypt=3adb2bfc2f9dfe44c116f76842300192";
    private VideoView videoView;
    private int mLayout = VideoView.VIDEO_LAYOUT_STRETCH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv__video_view);
        initVideo();
    }
    private void stopPlayer() {
        if (videoView != null)
            videoView.pause();
    }

    private void startPlayer() {
        if (videoView != null)
            videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null)
            videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null)
            videoView.resume();
        startPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null)
            videoView.stopPlayback();
    }

    private void initVideo() {
        Vitamio.isInitialized(this);
        videoView = (VideoView) findViewById(R.id.tv_videoview);
        videoPlay();
    }
    private void videoPlay() {
        String path = "";
        path = url;
        //播放文件路径
        videoView.setVideoPath(path);
        videoView.setMediaController(new MediaController(this));
        videoView.setMediaController(null);
        videoView.setVideoLayout(mLayout, 0);
        videoView.requestFocus();
        //准备播放器
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(io.vov.vitamio.MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);//播放速度正常1.0
            }
        });
        //播放完的监听
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(io.vov.vitamio.MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
    }

}
