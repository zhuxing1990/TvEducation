package com.vunke.education.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.base.Configs;
import com.vunke.education.view.KuangRelativeLayout;

import java.util.Random;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by zhuxi on 2017/3/22.
 */
public class MainActivity extends BaseActivity implements View.OnKeyListener,View.OnClickListener{
    private String [] video ={
            "http://movie.ks.js.cn/flv/other/1_0.mp4"
    };

    String url = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
//    private TextView main_broadcas_text;
//
    private KuangRelativeLayout main_relative_view1;
//    private KuangRelativeLayout main_relative_view2;
//    private KuangRelativeLayout main_relative_view3;
//    private KuangRelativeLayout main_relative_view4;
//    private KuangRelativeLayout main_relative_view5;
//    private KuangRelativeLayout main_relative_view6;
    private KuangRelativeLayout main_relative_watch_history;
    private KuangRelativeLayout main_relative_ranking_list;
//    private KuangRelativeLayout main_ralative_small_view1;
//    private KuangRelativeLayout main_ralative_small_view2;
//    private KuangRelativeLayout main_ralative_small_view3;
//    private KuangRelativeLayout main_ralative_small_view4;
//    private KuangRelativeLayout main_ordering;
//
//    private TextView main_home_page;
//    private TextView main_boutique_recommend;
//    private TextView main_infant_enlightenment;
//    private TextView main_primary_and_secondary_schools;
//    private TextView main_foreign_study;
//    private TextView main_open_class;
//    private TextView main_expert_lecture;
//    private TextView main_outdoor_activities;
//    private TextView main_free_tutorial;
    private VideoView videoView;
    /** 当前缩放模式 */
    private int mLayout = VideoView.VIDEO_LAYOUT_STRETCH;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initVideo();
//        initListener();
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
        videoView = (VideoView) findViewById(R.id.main_videoView);
        videoPlay();
    }
    private void videoPlay() {
        String path = "";
        path=video[new Random().nextInt(video.length)];
        path = url;
    //播放文件路径
//        videoView.setVideoPath(path);
        videoView.setVideoURI(Uri.parse(url));
//        videoView.setForegroundTintMode();

//        videoView.setMediaController(new MediaController(this));
        videoView.setMediaController(null);
        videoView.setVideoLayout(mLayout, 0);
        videoView.requestFocus();
        videoView.setOnKeyListener(this);
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


    private void initView() {
        main_relative_view1 = (KuangRelativeLayout) findViewById(R.id.main_relative_view1);
        main_relative_view1.setOnClickListener(this);
//        main_relative_view2 = (KuangRelativeLayout) findViewById(R.id.main_relative_view2);
//        main_relative_view3 = (KuangRelativeLayout) findViewById(R.id.main_relative_view3);
//        main_relative_view4 = (KuangRelativeLayout) findViewById(R.id.main_relative_view4);
//        main_relative_view5 = (KuangRelativeLayout) findViewById(R.id.main_relative_view5);
//        main_relative_view6 = (KuangRelativeLayout) findViewById(R.id.main_relative_view6);
//
        main_relative_watch_history = (KuangRelativeLayout) findViewById(R.id.main_relative_watch_history);
        main_relative_watch_history.setOnClickListener(this);
        main_relative_ranking_list = (KuangRelativeLayout) findViewById(R.id.main_relative_ranking_list);
        main_relative_ranking_list.setOnClickListener(this);
//        main_ralative_small_view1 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view1);
//        main_ralative_small_view2 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view2);
//        main_ralative_small_view3 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view3);
//        main_ralative_small_view4 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view4);
//        main_ordering = (KuangRelativeLayout) findViewById(R.id.main_ordering);
//
//        main_home_page = (TextView) findViewById(R.id.main_home_page);
//        main_boutique_recommend = (TextView) findViewById(R.id.main_boutique_recommend);
//        main_infant_enlightenment = (TextView) findViewById(R.id.main_infant_enlightenment);
//        main_primary_and_secondary_schools = (TextView) findViewById(R.id.main_primary_and_secondary_schools);
//        main_foreign_study = (TextView) findViewById(R.id.main_foreign_study);
//        main_open_class = (TextView) findViewById(R.id.main_open_class);
//        main_expert_lecture = (TextView) findViewById(R.id.main_expert_lecture);
//        main_outdoor_activities = (TextView) findViewById(R.id.main_outdoor_activities);
//        main_free_tutorial = (TextView) findViewById(R.id.main_free_tutorial);

    }

//    private void initListener() {
//        main_relative_view1.setOnFocusChangeListener(this);
//        main_relative_view2.setOnFocusChangeListener(this);
//        main_relative_view3.setOnFocusChangeListener(this);
//        main_relative_view4.setOnFocusChangeListener(this);
//        main_relative_view5.setOnFocusChangeListener(this);
//        main_relative_view6.setOnFocusChangeListener(this);
//
//        main_relative_watch_history.setOnFocusChangeListener(this);
//        main_relative_ranking_list.setOnFocusChangeListener(this);
//
//        main_ralative_small_view1.setOnFocusChangeListener(this);
//        main_ralative_small_view2.setOnFocusChangeListener(this);
//        main_ralative_small_view3.setOnFocusChangeListener(this);
//        main_ralative_small_view4.setOnFocusChangeListener(this);
//        main_ordering.setOnFocusChangeListener(this);
//
//        main_home_page.setOnFocusChangeListener(this);
//        main_boutique_recommend.setOnFocusChangeListener(this);
//        main_infant_enlightenment.setOnFocusChangeListener(this);
//        main_primary_and_secondary_schools.setOnFocusChangeListener(this);
//        main_foreign_study.setOnFocusChangeListener(this);
//        main_open_class.setOnFocusChangeListener(this);
//        main_expert_lecture.setOnFocusChangeListener(this);
//        main_outdoor_activities.setOnFocusChangeListener(this);
//        main_free_tutorial.setOnFocusChangeListener(this);
//    }

//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus){
//            Animation testAnim = AnimationUtils.loadAnimation(getApplicationContext(),
//                    R.anim.anim_scale_big);
//            v.startAnimation(testAnim);
//            v.bringToFront();
//        }else{
//            v.clearAnimation();
//        }
//
//    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()){
            case R.id.main_videoView:
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                    main_relative_view1.requestFocus();
                    return  true;
                }
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_relative_view1:
                Configs.intent = new Intent(MainActivity.this,TvVideoListActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_watch_history:
                Configs.intent = new Intent(MainActivity.this,WatchHistoryActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_ranking_list:
                Configs.intent = new Intent(MainActivity.this,RankingsActivity.class);
                startActivity(Configs.intent);
                break;
            default:
                break;
        }
    }
}
