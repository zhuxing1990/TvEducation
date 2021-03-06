package com.vunke.education.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.base.Configs;
import com.vunke.education.log.WorkLog;
import com.vunke.education.model.MainDataBean;
import com.vunke.education.network_request.NetWorkRequest;
import com.vunke.education.util.NetUtils;
import com.vunke.education.util.PicassoUtil;
import com.vunke.education.util.SharedPreferencesUtil;
import com.vunke.education.util.UiUtils;
import com.vunke.education.view.HorseRaceLampTextView;
import com.vunke.education.view.KuangRelativeLayout;
import com.vunke.education.view.KuangTextView;
import com.vunke.education.view.RoundAngleImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 首页
 * Created by zhuxi on 2017/3/22.
 */
public class MainActivity extends BaseActivity implements View.OnKeyListener, View.OnClickListener, SurfaceHolder.Callback, android.media.MediaPlayer.OnPreparedListener, android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener {
    private static final String TAG = "MainActivity";
    private String[] videoPaths = {
            "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538"
    };
    // 获取视频文件地址
//    String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
//    String videoPath =  "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";
    String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
    /**
     *  广告播放控件
     */
    private HorseRaceLampTextView main_broadcas_text;
    /**
     *   推荐位  1号
     */
    private KuangRelativeLayout main_relative_view1;
    /**
     *   推荐位  2号
     */
    private KuangRelativeLayout main_relative_view2;
    /**
     *   推荐位  3号
     */
    private KuangRelativeLayout main_relative_view3;
    /**
     *   推荐位  4号
     */
    private KuangRelativeLayout main_relative_view4;
    /**
     *   推荐位  5号
     */
    private KuangRelativeLayout main_relative_view5;
    /**
     *   推荐位  6号
     */
    private KuangRelativeLayout main_relative_view6;
    /**
     *  观看历史
     */
    private KuangRelativeLayout main_relative_watch_history;
    /**
     *  排行榜
     */
    private KuangRelativeLayout main_relative_ranking_list;

    /**
     *  SP 提供内容 1
     */
    private KuangRelativeLayout main_ralative_small_view1;
     /**
     *  SP 提供内容 2
     */
    private KuangRelativeLayout main_ralative_small_view2;
    /**
     *  SP 提供内容 3
     */
    private KuangRelativeLayout main_ralative_small_view3;
    /**
     *  SP 提供内容 4
     */
    private KuangRelativeLayout main_ralative_small_view4;
    /**
     *  订购按钮
     */
    private KuangRelativeLayout main_ordering;

    /**
     *  推荐位  图片控件
     */
    private RoundAngleImageView main_big_view1, main_big_view2, main_big_view3, main_big_view4, main_big_view5, main_big_view6;
    /**
     *  SP 提供内容 图片控件
     */
    private RoundAngleImageView main_small_view1, main_small_view2, main_small_view3, main_small_view4;
    /**
     *  分类标签 首页
     */
    private KuangTextView main_home_page;
    /**
     *  分类标签 精品推荐
     */
    private KuangTextView main_boutique_recommend;
    /**
     *  分类标签 幼儿启蒙
     */
    private KuangTextView main_infant_enlightenment;
    /**
     *  分类标签 中小学
     */
    private KuangTextView main_primary_and_secondary_schools;
    /**
     *  分类标签 外国留学
     */
    private KuangTextView main_foreign_study;
    /**
     *  分类标签 公开课
     */
    private KuangTextView main_open_class;
    /**
     *  分类标签 户外活动
     */
    private KuangTextView main_expert_lecture;
    /**
     *  分类标签 专家讲座
     */
    private KuangTextView main_outdoor_activities;
    /**
     *  分类标签 免费教程
     */
    private KuangTextView main_free_tutorial;
    /**
     * 用户ID
     */
    private String userId;
    /**
     *首页数据信息
     */
    private MainDataBean bean;
    private boolean startPlay = true;
    /**
     *  首页视频播放控件
     */
    private SurfaceView main_surfaceView;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if (NetUtils.isNetConnected(mcontext)) {
            showToast("网络已经连接");
        } else {
            showToast("网络未连接");
        }
        userId = SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,"");
        WorkLog.i(TAG, "onCreate: userId:"+userId);
        initView();
        initListener();
        initVideo();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        main_surfaceView = (SurfaceView) findViewById(R.id.main_surfaceView);
        main_broadcas_text = (HorseRaceLampTextView) findViewById(R.id.main_broadcas_text);

        main_relative_view1 = (KuangRelativeLayout) findViewById(R.id.main_relative_view1);
        main_relative_view2 = (KuangRelativeLayout) findViewById(R.id.main_relative_view2);
        main_relative_view3 = (KuangRelativeLayout) findViewById(R.id.main_relative_view3);
        main_relative_view4 = (KuangRelativeLayout) findViewById(R.id.main_relative_view4);
        main_relative_view5 = (KuangRelativeLayout) findViewById(R.id.main_relative_view5);
        main_relative_view6 = (KuangRelativeLayout) findViewById(R.id.main_relative_view6);

        main_relative_watch_history = (KuangRelativeLayout) findViewById(R.id.main_relative_watch_history);
        main_relative_ranking_list = (KuangRelativeLayout) findViewById(R.id.main_relative_ranking_list);
        main_ordering = (KuangRelativeLayout) findViewById(R.id.main_ordering);

        main_ralative_small_view1 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view1);
        main_ralative_small_view2 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view2);
        main_ralative_small_view3 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view3);
        main_ralative_small_view4 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view4);

        main_big_view1 = (RoundAngleImageView) findViewById(R.id.main_big_view1);
        main_big_view2 = (RoundAngleImageView) findViewById(R.id.main_big_view2);
        main_big_view3 = (RoundAngleImageView) findViewById(R.id.main_big_view3);
        main_big_view4 = (RoundAngleImageView) findViewById(R.id.main_big_view4);
        main_big_view5 = (RoundAngleImageView) findViewById(R.id.main_big_view5);
        main_big_view6 = (RoundAngleImageView) findViewById(R.id.main_big_view6);
        main_small_view1 = (RoundAngleImageView) findViewById(R.id.main_small_view1);
        main_small_view2 = (RoundAngleImageView) findViewById(R.id.main_small_view2);
        main_small_view3 = (RoundAngleImageView) findViewById(R.id.main_small_view3);
        main_small_view4 = (RoundAngleImageView) findViewById(R.id.main_small_view4);

        main_home_page = (KuangTextView) findViewById(R.id.main_home_page);
        main_boutique_recommend = (KuangTextView) findViewById(R.id.main_boutique_recommend);
        main_infant_enlightenment = (KuangTextView) findViewById(R.id.main_infant_enlightenment);
        main_primary_and_secondary_schools = (KuangTextView) findViewById(R.id.main_primary_and_secondary_schools);
        main_foreign_study = (KuangTextView) findViewById(R.id.main_foreign_study);
        main_open_class = (KuangTextView) findViewById(R.id.main_open_class);
        main_expert_lecture = (KuangTextView) findViewById(R.id.main_expert_lecture);
        main_outdoor_activities = (KuangTextView) findViewById(R.id.main_outdoor_activities);
        main_free_tutorial = (KuangTextView) findViewById(R.id.main_free_tutorial);
    }

    /**
     * 监听事件
     */
    private void initListener() {
        main_relative_view1.setOnClickListener(this);
        main_relative_view2.setOnClickListener(this);
        main_relative_view3.setOnClickListener(this);
        main_relative_view4.setOnClickListener(this);
        main_relative_view5.setOnClickListener(this);
        main_relative_view6.setOnClickListener(this);

        main_relative_view4.setOnKeyListener(this);

        main_relative_watch_history.setOnClickListener(this);
        main_relative_ranking_list.setOnClickListener(this);
        main_surfaceView.getHolder().addCallback(this);

        main_relative_watch_history.setOnKeyListener(this);
        main_relative_ranking_list.setOnKeyListener(this);

        main_ralative_small_view4.setOnKeyListener(this);
        main_relative_view1.setOnKeyListener(this);
        main_relative_view4.setOnKeyListener(this);

        main_ralative_small_view1.setOnClickListener(this);
        main_ralative_small_view2.setOnClickListener(this);
        main_ralative_small_view3.setOnClickListener(this);
        main_ralative_small_view4.setOnClickListener(this);

        main_home_page.setOnClickListener(this);
        main_boutique_recommend.setOnClickListener(this);
        main_infant_enlightenment.setOnClickListener(this);
        main_primary_and_secondary_schools.setOnClickListener(this);
        main_foreign_study.setOnClickListener(this);
        main_open_class.setOnClickListener(this);
        main_expert_lecture.setOnClickListener(this);
        main_outdoor_activities.setOnClickListener(this);
        main_free_tutorial.setOnClickListener(this);
    }

    /**
     * 请求网络数据
     */
    private void initData() {
        if (TextUtils.isEmpty(userId)) {
            userId = "null";
            WorkLog.e(TAG, "initData: userId:" + userId);
        }
        int versionCode = UiUtils.getVersionCode(getApplicationContext());
        WorkLog.e(TAG, "initData: versionCode:" + versionCode);
        try {
            //1.1.2接口入参 json = {“versionCode”,”xx”,”userId”:”id”}
            JSONObject json = new JSONObject();
            json.put("versionCode", versionCode);
            json.put("userId", userId);
            WorkLog.i(TAG, "initData: json:" + json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.HOME_DATE).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: \n" + s);// WorkLog封装的Log
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")) {
                            int code = js.getInt("code");
                            switch (code) {
                                case 200:
                                    Log.i(TAG, "onSuccess: code is 0");
                                    Gson gson = new Gson();
                                    bean = gson.fromJson(s, MainDataBean.class);

                                    break;
                                case 400:
                                    Log.i(TAG, "onSuccess: code is 400");
                                    break;
                                case 500:
                                    Log.i(TAG, "onSuccess: code is 500");
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.e(TAG, "onError:---------------------------------------------------------------- ");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    try {
                        if (bean != null && bean.getCode().equals("200")) {
                            if (bean.getIndex() != null && bean.getIndex().size() != 0) {
                                Observable.from(bean.getIndex())
                                        .filter(new Func1<MainDataBean.IndexBean, Boolean>() {
                                            @Override
                                            public Boolean call(MainDataBean.IndexBean indexBean) {
                                                return (indexBean.getMode_type().equals("1.0")) || indexBean.getMode_type().equals("1.1") || indexBean.getMode_type().equals("1.4");
                                            }
                                        })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<MainDataBean.IndexBean>() {
                                            @Override
                                            public void call(MainDataBean.IndexBean indexBean) {
                                                if (indexBean != null) {
                                                    WorkLog.i(TAG, "图片地址" + indexBean.getImplement_content());
                                                    String ImgContent = indexBean.getImplement_content();
                                                    if (ImgContent.indexOf("six_1") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view1);
                                                    } else if (ImgContent.indexOf("six_2") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view2);
                                                    } else if (ImgContent.indexOf("six_3") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view3);
                                                    } else if (ImgContent.indexOf("six_4") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view4);
                                                    } else if (ImgContent.indexOf("six_5") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view5);
                                                    } else if (ImgContent.indexOf("six_6") != -1) {
                                                        PicassoUtil.getInstantiation().onBigNetImage(mcontext, ImgContent, main_big_view6);
                                                    } else if (ImgContent.indexOf("four_1") != -1) {
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext, ImgContent, main_small_view1);
                                                    } else if (ImgContent.indexOf("four_2") != -1) {
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext, ImgContent, main_small_view2);
                                                    } else if (ImgContent.indexOf("four_3") != -1) {
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext, ImgContent, main_small_view3);
                                                    } else if (ImgContent.indexOf("four_4") != -1) {
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext, ImgContent, main_small_view4);
                                                    }
                                                }
                                            }
                                        });
                            }
                            Observable.from(bean.getIndex())
                                    .filter(new Func1<MainDataBean.IndexBean, Boolean>() {
                                        @Override
                                        public Boolean call(MainDataBean.IndexBean indexBean) {
                                            return ((indexBean.getMode_type().equals("1.2")) || (indexBean.getMode_type().equals("1.3")));
                                        }
                                    })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<MainDataBean.IndexBean>() {
                                        @Override
                                        public void call(MainDataBean.IndexBean indexBean) {
                                            if (indexBean != null) {
                                                WorkLog.i(TAG, "文字" + indexBean.getImplement_content());
                                                if (indexBean.getMode_type().equals("1.2")) {
                                                    main_broadcas_text.setText(indexBean.getImplement_content());
                                                } else if (indexBean.getMode_type().equals("1.3")) {
                                                    switch (indexBean.getIndex_id()) {
                                                        case "13":
                                                            main_home_page.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "14":
                                                            main_boutique_recommend.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "15":
                                                            main_infant_enlightenment.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "16":
                                                            main_primary_and_secondary_schools.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "17":
                                                            main_foreign_study.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "18":
                                                            main_open_class.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "19":
                                                            main_expert_lecture.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "20":
                                                            main_outdoor_activities.setText(indexBean.getImplement_content());
                                                            break;
                                                        case "21":
                                                            main_free_tutorial.setText(indexBean.getImplement_content());
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化视频
     */
    private void initVideo() {
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                videoPlay();
            }
        });
    }

    /**
     * 播放视频
     */
    private void videoPlay() {
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
            mediaPlayer.setDisplay(main_surfaceView.getHolder());
            WorkLog.i(TAG, "initVideo: loading video");
            mediaPlayer.prepareAsync();
//            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            WorkLog.i(TAG, "initVideo: loading video error");
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceCreated: surfaceView创建");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        WorkLog.i(TAG, "surfaceChanged: surfaceView发生改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceDestroyed: surfaceView 被销毁");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    public void onCompletion(android.media.MediaPlayer mp) {
        // 在播放完毕被回调
        WorkLog.i(TAG, "onCompletion: video play completion");
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        replay();
    }

    @Override
    public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
        WorkLog.i(TAG, "onError: 播放错误");
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer = null;
        }
        initVideo();
        return false;
    }

    @Override
    public void onPrepared(android.media.MediaPlayer mp) {
        WorkLog.i(TAG, "onPrepared: video load success,start play video");
        mediaPlayer.start();
        // 按照初始位置播放
        mediaPlayer.seekTo(0);
    }

    /**
     * 停止播放视频
     */
    private void videoStop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
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
            return;
        }
        initVideo();
    }

    /**
     * Activity 生命周期  暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "onPause: video Pause");
            mediaPlayer.pause();
        }
    }
    /**
     * Activity 生命周期   重新开始
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (startPlay) {
            startPlay = false;
            return;
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            WorkLog.i(TAG, "onResume: video restart");
            mediaPlayer.start();
        }
    }
    /**
     * Activity 生命周期   销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoStop();
    }

    /**
     * 按键监听事件
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {
            case R.id.main_videoView:
                if (isKeyDown(event)) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        main_relative_view1.requestFocus();
                        return true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        return true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        main_relative_watch_history.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.main_relative_ranking_list:

                break;
            case R.id.main_relative_watch_history:
                if (isKeyDown(event)) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        main_home_page.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.main_ralative_small_view4:
                if (isKeyDown(event)) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        main_ordering.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.main_relative_view1:
                if (isKeyDown(event)) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                        return true;
                }
                break;
            case R.id.main_relative_view4:
                if (isKeyDown(event)) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                        return true;
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        main_ralative_small_view1.requestFocus();
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     *   判断按键是否是在按下的状态
     *   ture 按下   false 弹起
     * @param event
     * @return
     */
    private boolean isKeyDown(KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN;
    }


    /**
     *  记录按下返回键的时间
     */
    private long back_time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - back_time >= 2000) {
                showToast("再按一次退出");
                back_time = System.currentTimeMillis();
                return false;
            } else {
//                this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击时间
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_relative_view1:
                StartUp(1);
                break;
            case R.id.main_relative_view2:
                StartUp(2);
                break;
            case R.id.main_relative_view3:
                StartUp(3);
                break;
            case R.id.main_relative_view4:
                StartUp(4);
                break;
            case R.id.main_relative_view5:
                StartUp(5);
                break;
            case R.id.main_relative_view6:
                StartUp(bean.getIndex().size() - 1);
                break;
            case R.id.main_relative_watch_history:
                Configs.intent = new Intent(mcontext, WatchHistoryActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_ranking_list:
                Configs.intent = new Intent(mcontext, RankingsActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_ralative_small_view1:
                StartUp(6);
                break;
            case R.id.main_ralative_small_view2:
                StartUp(7);
                break;
            case R.id.main_ralative_small_view3:
                StartUp(8);
                break;
            case R.id.main_ralative_small_view4:
                StartUp(9);
                break;
            case R.id.main_home_page:
                StartUp(11);
                break;
            case R.id.main_boutique_recommend:
                StartUp(12);
                break;
            case R.id.main_infant_enlightenment:
                StartUp(13);
                break;
            case R.id.main_primary_and_secondary_schools:
                StartUp(14);
                break;
            case R.id.main_foreign_study:
                StartUp(15);
                break;
            case R.id.main_open_class:
                StartUp(16);
                break;
            case R.id.main_expert_lecture:
                StartUp(17);
                break;
            case R.id.main_outdoor_activities:
                StartUp(18);
                break;
            case R.id.main_free_tutorial:
                StartUp(19);
                break;
            default:
                break;
        }
    }

    /**
     * 开始启动应用
     * @param position
     */
    private void StartUp(int position) {
        if (BeanHasData(bean)) {
            String packageName = bean.getIndex().get(position).getImplement_package();
            String className = bean.getIndex().get(position).getImplement_address();
            int implementId = bean.getIndex().get(position).getImplement_id();
            UiUtils.StartAPP(packageName, className, implementId, mcontext);
        }
        UploadTimes(position);
    }

    /**
     *  上传点击次数
     * @param position
     */
    private void UploadTimes(int position) {
        try {
            String statisticsId = bean.getIndex().get(position).getIndex_id();
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("frequency", "");//点击次数
            jsonItem.put("statisticsId", statisticsId);
            JSONArray jsArray = new JSONArray();
            jsArray.put(jsonItem);
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("versionCode", UiUtils.getVersionCode(mcontext));
            json.put("item", jsArray);
            WorkLog.i(TAG, "UploadTimes: json:" + json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.STATISTICES_RBIT).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: upload times success" + s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.e(TAG, "onError: upload times error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean BeanHasData(MainDataBean bean) {
        try {
            if (bean == null) {
                Log.i(TAG, "BeanHasData: bean is null");
                return false;
            }
            if (!bean.getCode().equals("200")) {
                Log.i(TAG, "BeanHasData: bean.getcode not is 0");
                return false;
            }
            if (bean.getIndex().size() == 0 && bean.getIndex().isEmpty()) {
                Log.i(TAG, "BeanHasData: bean.getIndex is null");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
