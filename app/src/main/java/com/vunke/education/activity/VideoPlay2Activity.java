package com.vunke.education.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.log.WorkLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhuxi on 2017/4/6.
 */
public class VideoPlay2Activity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,View.OnKeyListener {
    private static final String TAG = "VideoPlay2Activity";
    private SurfaceView video_scrollview;
    private ImageView videoplay_play;
    private ProgressBar videoplay_videoprogress;
    private ProgressBar videoplay_playprogress;
    private TextView videoplay_starttime;
    private TextView videoplay_endtime;
    private TextView videoplay_videoStatus;
    private MediaPlayer mediaPlayer;

    private Observable<Long> observable;
    private Subscriber<Long> subscriber;
    private AlphaAnimation alphaAnimation0To1;
    private AlphaAnimation alphaAnimation1To0;
    private RelativeLayout videoplay_relative;
    private int clickedNum = 0;

    private int currentPosition = 0;
    private int duration;
    private boolean startPlay;
    private  Subscription subscribe;
    private  Subscription subscribe2;
    private  String videoStatus="";
    // 视频文件地址
//    String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
    String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
    //    String videoPath ="http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay2);
        GetVideoPath();
        initView();
        initListener();
        initAlphaAnimation1();
        initAlphaAnimation2();
        initTimerOut();
        SetVideoView();
    }

    /**
     * 获取视频地址
     */
    private void GetVideoPath() {
        Intent intent = getIntent();
        if (intent.hasExtra("videoPath")) {
            videoPath = intent.getStringExtra("videoPath");
            WorkLog.i(TAG, "GetVideoPath: path:" + videoPath);
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        video_scrollview = (SurfaceView) findViewById(R.id.videoplay_surfaceview);
        videoplay_play = (ImageView) findViewById(R.id.videoplay_play);
        videoplay_videoprogress = (ProgressBar) findViewById(R.id.videoplay_videoprogress);
        videoplay_playprogress = (ProgressBar) findViewById(R.id.videoplay_playprogress);
        videoplay_starttime = (TextView) findViewById(R.id.videoplay_starttime);
        videoplay_endtime = (TextView) findViewById(R.id.videoplay_endtime);
        videoplay_videoStatus = (TextView) findViewById(R.id.videoplay_videoStatus);
        videoplay_relative = (RelativeLayout) findViewById(R.id.videoplay_relative);
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        video_scrollview.getHolder().addCallback(this);
        videoplay_play.setOnClickListener(this);
        videoplay_play.requestFocus();
        videoplay_relative.setOnKeyListener(this);
        videoplay_play.setOnKeyListener(this);
    }

    /**
     * 准备播放视频
     */
    private void SetVideoView() {
        WorkLog.i(TAG, "SetVideoView: ");
     Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                WorkLog.i(TAG, "call: start initVideo");
                    initVideo(0);
            }
        });
    }

    /**
     * 初始化视频播放
     * @param msec
     */
    private void initVideo(final int msec) {
        if (TextUtils.isEmpty(videoPath)){
            WorkLog.i(TAG, "initVideo: get videopath is null");
            showToast("获取视频地址失败，关闭播放画面");
            finish();
            return;
        }
        WorkLog.i(TAG, "initVideo: ");
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                WorkLog.i(TAG, "initVideo: mediaPlayer is playing,stopVideo and release mediaplayer");
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            if (videoPath.startsWith("http://")){
                WorkLog.i(TAG, "initVideo: get videopath is network video");
                mediaPlayer.setDataSource(mcontext, Uri.parse(videoPath));
            }else{
                try{
                    File file = new File(videoPath);
                    if (!file.exists()){
                        WorkLog.i(TAG, "initVideo: get videofile not exists");
                        showToast("获取播放视频文件失败或者文件不存在");
                        finish();
                        return;
                    }else{
                        WorkLog.i(TAG, "initVideo: get videopath is local video");
                        mediaPlayer.setDataSource(file.getAbsolutePath());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mediaPlayer.setDisplay(video_scrollview.getHolder());
            WorkLog.i(TAG, "initVideo: loading video");
            mediaPlayer.prepareAsync();
//            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            WorkLog.i(TAG, "initVideo: loading video error");
            showToast("视频加载失败");
            e.printStackTrace();
        }
    }


    /**
     *  生命周期 暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
        WorkLog.i(TAG, "onPause: ");
        pauseVideo();
    }

    /**
     * 生命周期 重新开始
     */
    @Override
    protected void onResume() {
        super.onResume();
        WorkLog.i(TAG, "onResume: ");
        if (startPlay) {
            startPlay = false;
            return;
        }
        if (mediaPlayer!=null&& !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    /**
     * 生命周期 被销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkLog.i(TAG, "onDestroy: ");
        stopVideo();
        if (!subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
        }if (!subscribe2.isUnsubscribed()){
            subscribe2.unsubscribe();
        }
        alphaAnimation0To1 = null;
        alphaAnimation1To0 = null;
    }

    /**
     * 暂停/恢复 播放
     */
    public void pauseVideo() {
        WorkLog.i(TAG, "pauseVideo:");
        String videoStatus = videoplay_videoStatus.getText().toString().trim();
        if (videoStatus.equals("继续") && !mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "pauseVideo: pauseVideo play video");
            videoplay_videoStatus.setText("暂停");
            mediaPlayer.start();
            showToast("继续播放");
            return;
        }
        if (videoStatus.equals("暂停") && mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "pauseVideo: restart play video");
            videoplay_videoStatus.setText("继续");
            mediaPlayer.pause();
            showToast("暂停播放");
            return;
        }
    }
    /*
   * 停止播放
   */
    protected void stopVideo() {
        WorkLog.i(TAG, "stopVideo:");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "stopVideo: video is playing,stop video");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "stopVideo: video not play,release video");
            mediaPlayer.release();
            mediaPlayer = null;
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
            return;
        }
        initVideo(0);
    }

    /**
     * Surfaceview 被创建时调用
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceCreated: surfaceview is create");
        if (currentPosition > 0) {
            // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
            initVideo(currentPosition);
            currentPosition = 0;
        }
    }

    /**
     *  Surfaceview 发生改变时调用
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        WorkLog.i(TAG, "surfaceChanged: SurfaceHolder changed");
    }

    /**
     * Surfaceview 被销毁调用
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceDestroyed: SurfaceHolder is destroyed");
        // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoplay_play:
                videoStatus = videoplay_videoStatus.getText().toString().trim();
                WorkLog.i(TAG, "onClick: videoStatus:"+videoStatus);
                if (videoStatus.equals("暂停")) {
                    videoplay_play.setImageResource(R.drawable.video_stop);
                    pauseVideo();
                } else if (videoStatus.equals("继续")) {
                    videoplay_play.setImageResource(R.drawable.video_play);
                    pauseVideo();
                } else if (videoStatus.equals("重播")) {
                    replay();
                    initTimerOut();
                    videoplay_play.setClickable(true);
                    videoplay_play.setImageResource(R.drawable.video_play);
                }
                break;
        }
    }

    /**
     *  视频播放完毕后调用
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        // 在播放完毕被回调
        WorkLog.i(TAG, "onCompletion: video play completion");
        mediaPlayer.reset();
        videoplay_play.setImageResource(R.drawable.video_restart);
        videoplay_videoStatus.setText("重播");
        if (!subscribe.isUnsubscribed()){
            WorkLog.i(TAG,"onCompletion: unsubscribe");
            subscribe.unsubscribe();
        }
        currentPosition = 0;
        videoplay_starttime.setText("0:00");
        stopTimerOut();
        //重新播放
//        replay();
    }

    /**
     * 视频播放错误时调用
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // 发生错误重新播放
        WorkLog.i(TAG, "onError: play video error");
        mediaPlayer.reset();
        initVideo(0);
        return false;
    }

    /**
     *  视频加载完成时调用
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        WorkLog.i(TAG, "onPrepared: video load success,start play video");
        mediaPlayer.start();
        videoplay_playprogress.setVisibility(View.GONE);
        mediaPlayer.seekTo(0);
        initVideoProgress();
    }

    /**
     * 初始化视频播放位置和播放进度
     */
    private void initVideoProgress() {
        WorkLog.i(TAG, "initVideoProgress: ");
        // 按照初始位置播放
        duration = mediaPlayer.getDuration();
        WorkLog.i(TAG,"duration:"+duration);
        videoplay_videoprogress.setMax(duration);
        videoplay_endtime.setText( getVideoTime(duration));
        subscribe = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                WorkLog.i(TAG, "onError: set video progress error");
                this.unsubscribe();
            }
            @Override
            public void onNext(Long aLong) {
                currentPosition = mediaPlayer.getCurrentPosition();
                WorkLog.i(TAG,"currentPosition:"+currentPosition);
                videoplay_videoprogress.setProgress(currentPosition);
                String videoTime = getVideoTime(currentPosition);
                WorkLog.i(TAG, "onNext: videoTime:"+videoTime);
                videoplay_starttime.setText(videoTime.trim());
            }
        });
    }

    /**
     * 毫秒转 时间格式
     * @param time
     * @return
     */
    private String getVideoTime(int time) {
        try{
        if (time>=0){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            return formatter.format(time);
        }
        }catch (Exception e){
            e.printStackTrace();
            return "0:00";
        }
        return "0:00";
    }

    /**
     *  监听按键事件
     *  根据按键显示播放进度
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            clickedNum++;
            if (clickedNum > 1) {
                videoplay_play.setClickable(true);
            }
            if (subscribe2 != null) {
                stopTimerOut();
            }
            initTimerOut();
        }
        return false;
    }

    /**
     * 初始化隐藏控件功能 10秒隐藏
     */
    private void initTimerOut() {
        WorkLog.i(TAG, "initTimerOut: ");
        observable = Observable.timer(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

//                videoplay_relative.clearAnimation();
                videoplay_relative.startAnimation(alphaAnimation1To0);
                videoplay_play.setClickable(false);
                clickedNum = 0;// 重置clicked计数
                this.unsubscribe();
            }

            @Override
            public void onError(Throwable arg0) {
                this.unsubscribe();
            }

            @Override
            public void onNext(Long arg0) {
            }
        };
        subscribe2 = observable.subscribe(subscriber);
    }

    /**
     * 取消控件隐藏功能
     */
    private void stopTimerOut() {
        WorkLog.i(TAG, "stopTimerOut: ");
        videoplay_relative.clearAnimation();
        Animation animation = videoplay_relative.getAnimation();
        if (alphaAnimation1To0.equals(animation)) {
            videoplay_relative.clearAnimation();
            videoplay_relative.startAnimation(alphaAnimation0To1);
        }
        subscribe2.unsubscribe();
        subscribe2 = null;
        observable = null;
        subscriber = null;
    }

    private void initAlphaAnimation1() {
        alphaAnimation0To1 = new AlphaAnimation(0f, 1f);
        alphaAnimation0To1.setDuration(1000);
        alphaAnimation0To1.setFillAfter(true);

    }

    private void initAlphaAnimation2() {
        alphaAnimation1To0 = new AlphaAnimation(1f, 0f);
        alphaAnimation1To0.setDuration(1000);
        alphaAnimation1To0.setFillAfter(true);
    }
}
