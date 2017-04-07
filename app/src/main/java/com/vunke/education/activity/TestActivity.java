package com.vunke.education.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.log.WorkLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class TestActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final String TAG = "TestActivity";
    private SurfaceView video_scrollview;
    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private int duration;
    private boolean startPlay = false;
    private boolean isPlaying = false;
    private ProgressBar videoplay_playprogress, videoplay_videoprogress;
    private ImageView videoplay_play;
    private TextView videoplay_starttime, videoplay_endtime;
    private TextView videoplay_videoStatus;

    // 视频文件地址
//    String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
    String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
//    String videoPath ="http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay2);
//        GetVideoPath();
        initView();
        initListener();
    }

    private void GetVideoPath() {
        Intent intent = getIntent();
        if (intent.hasExtra("videoPath")) {
            videoPath = intent.getStringExtra("videoPath");
            Log.i(TAG, "GetVideoPath: path:" + videoPath);
        }
    }

    private void initView() {
        video_scrollview = (SurfaceView) findViewById(R.id.videoplay_surfaceview);
        videoplay_playprogress = (ProgressBar) findViewById(R.id.videoplay_playprogress);
        videoplay_videoprogress = (ProgressBar) findViewById(R.id.videoplay_videoprogress);
        videoplay_play = (ImageView) findViewById(R.id.videoplay_play);

        videoplay_starttime = (TextView) findViewById(R.id.videoplay_starttime);
        videoplay_endtime = (TextView) findViewById(R.id.videoplay_endtime);
        videoplay_videoStatus = (TextView) findViewById(R.id.videoplay_videoStatus);
        video_scrollview.getHolder().addCallback(this);
         Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
        @Override
        public void call(Long aLong) {
            initVideo(0);
        }
    });
//        handler.sendEmptyMessageDelayed(1111, 500);
    }

    private void initListener() {
        videoplay_play.setFocusable(true);
        videoplay_play.requestFocus();
        videoplay_play.setOnClickListener(this);

    }

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1111:
//                    initVideo(0);
//                    break;
//
//            }
//        }
//    };


    private void initVideo(final int msec) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            // mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.setDataSource(mcontext, Uri.parse(videoPath));
            mediaPlayer.setDisplay(video_scrollview.getHolder());

            WorkLog.i(TAG, "initVideo: 开始装载");
//            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoplay_play:
                String videoStatus = videoplay_videoStatus.getText().toString().trim();
                Log.i(TAG, "onClick: videoStatus:"+videoStatus);
                if (videoStatus.equals("暂停")) {
                    videoplay_play.setImageResource(R.drawable.video_play);
                    pause();
                } else if (videoStatus.equals("继续")) {
                    videoplay_play.setImageResource(R.drawable.video_stop);
                    pause();
                } else if (videoStatus.equals("重播")) {
                    replay();
                }
                break;
        }
    }

    public void pause() {
        String videoStatus = videoplay_videoStatus.getText().toString().trim();
        if (videoStatus.equals("继续") && !mediaPlayer.isPlaying()) {
            videoplay_videoStatus.setText("暂停");
            mediaPlayer.start();
            showToast("继续播放");
            isPlaying = true;
            return;
        }
        if (videoStatus.equals("暂停") && mediaPlayer.isPlaying()) {
            videoplay_videoStatus.setText("继续");
            mediaPlayer.pause();
            showToast("暂停播放");
            isPlaying = false;
            return;
        }
    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            showToast("重新播放");
            videoplay_videoStatus.setText("暂停");
            isPlaying = true;
            return;
        }
        isPlaying = false;
        initVideo(0);
    }

    /*
     * 停止播放
	 */
    protected void stop() {
        Log.i(TAG, "stopVideo:  关闭视频");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: 销毁Activity");
        stop();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceCreated: SurfaceHolder 被创建");
        if (currentPosition > 0) {
            // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
            initVideo(currentPosition);
            currentPosition = 0;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        WorkLog.i(TAG, "surfaceChanged: SurfaceHolder 大小被改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceDestroyed: SurfaceHolder 被销毁");
        // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        WorkLog.i(TAG, "onPrepared: 装载完成");
        mediaPlayer.start();
        videoplay_playprogress.setVisibility(View.GONE);
        // 按照初始位置播放
        mediaPlayer.seekTo(0);
        duration = mediaPlayer.getDuration();
        startPlay = true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 在播放完毕被回调
        WorkLog.i(TAG, "onCompletion: 播放完毕");
        videoplay_play.setImageResource(R.drawable.video_restart);
        videoplay_videoStatus.setText("重播");
//        replay();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // 发生错误重新播放
        WorkLog.i(TAG, "onError: 播放错误");
//        mediaPlayer.reset();
        initVideo(0);
        isPlaying = false;
        return false;
    }
}