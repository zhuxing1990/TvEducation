package com.vunke.education.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.TextView;

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
import com.vunke.education.util.UiUtils;
import com.vunke.education.util.UserInfoUtil;
import com.vunke.education.view.KuangRelativeLayout;
import com.vunke.education.view.KuangTextView;
import com.vunke.education.view.RoundAngleImageView;

import org.json.JSONObject;

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
public class MainActivity extends BaseActivity implements View.OnKeyListener, View.OnClickListener,SurfaceHolder.Callback, android.media.MediaPlayer.OnPreparedListener, android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener{
    private static final String TAG = "MainActivity";
    private String[] videoPaths = {
            "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538"
    };
    // 获取视频文件地址
//    String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
//    String videoPath =  "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";
    String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
    private TextView main_broadcas_text;
    private KuangRelativeLayout main_relative_view1;
    private KuangRelativeLayout main_relative_view2;
    private KuangRelativeLayout main_relative_view3;
    private KuangRelativeLayout main_relative_view4;
    private KuangRelativeLayout main_relative_view5;
    private KuangRelativeLayout main_relative_view6;
    private KuangRelativeLayout main_relative_watch_history;
    private KuangRelativeLayout main_relative_ranking_list;
    private KuangRelativeLayout main_ralative_small_view1;
    private KuangRelativeLayout main_ralative_small_view2;
    private KuangRelativeLayout main_ralative_small_view3;
    private KuangRelativeLayout main_ralative_small_view4;
    private KuangRelativeLayout main_ordering;


    private RoundAngleImageView main_big_view1, main_big_view2, main_big_view3, main_big_view4, main_big_view5, main_big_view6;
    private RoundAngleImageView main_small_view1, main_small_view2, main_small_view3, main_small_view4;
    private KuangTextView main_home_page;
    private KuangTextView main_boutique_recommend;
    private KuangTextView main_infant_enlightenment;
    private KuangTextView main_primary_and_secondary_schools;
    private KuangTextView main_foreign_study;
    private KuangTextView main_open_class;
    private KuangTextView main_expert_lecture;
    private KuangTextView main_outdoor_activities;
    private KuangTextView main_free_tutorial;
    private String userId;
    private MainDataBean bean;

    private SurfaceView main_surfaceView;
    private MediaPlayer mediaPlayer;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action.equals(UserInfoUtil.LOAD_USER_INFO_ACTION)) {
                    userId = intent.getStringExtra("userID");//用户ID
                    WorkLog.e(TAG, "initData: userID:" + userId);
                    initData();
                }
            }
        }
    };
    private boolean startPlay = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if (NetUtils.isNetConnected(mcontext)){
            showToast("网络已经连接");
        }else{
            showToast("网络未连接");
        }
        UserInfoUtil.initUserInfo(getApplicationContext());
        UserInfoUtil.registerBoradcastReceiver(getApplicationContext(), mBroadcastReceiver);
        initView();
        initListener();
        initVideo();
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
            json.put("versionCode", userId);
            json.put("userId", versionCode);
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.HOME_DATE).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.e(TAG, "onSuccess:");// WorkLog封装的Log
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")) {
                            int code = js.getInt("code");
                            switch (code) {
                                case 0:
                                    Log.i(TAG, "onSuccess: code is 0");
                                    Gson gson = new Gson();
                                    bean = gson.fromJson(s, MainDataBean.class);

                                    break;
                                case -1:
                                    Log.i(TAG, "onSuccess: code is -1");
                                    break;
                                case 1:
                                    Log.i(TAG, "onSuccess: code is 1");
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
                        if (bean != null && bean.getCode().equals("0")) {
                            if (bean.getIndex() != null && bean.getIndex().size() != 0){
                                Observable.from(bean.getIndex())
                                        .filter(new Func1<MainDataBean.IndexBean, Boolean>() {
                                            @Override
                                            public Boolean call(MainDataBean.IndexBean indexBean) {
                                                return (( indexBean.getModeType().equals("1.0"))||( indexBean.getModeType().equals("1.1"))||( indexBean.getModeType().equals("1.4")));
                                            }
                                        })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<MainDataBean.IndexBean>() {
                                            @Override
                                            public void call(MainDataBean.IndexBean indexBean) {
                                                if (indexBean!=null){
                                                    WorkLog.e(TAG,"图片地址"+indexBean.getImplementContent());
                                                    String ImgContent = indexBean.getImplementContent();
                                                    if (ImgContent.indexOf("six_1")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view1);
                                                    }else if(ImgContent.indexOf("six_2")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view2);
                                                    }else if(ImgContent.indexOf("six_3")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view3);
                                                    }else if(ImgContent.indexOf("six_4")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view4);
                                                    }else if(ImgContent.indexOf("six_5")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view5);
                                                    }else if(ImgContent.indexOf("six_6")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_big_view6);
                                                    }
                                                    else if (ImgContent.indexOf("four_1")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_small_view1);
                                                    }else if (ImgContent.indexOf("four_2")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_small_view2);
                                                    }else if (ImgContent.indexOf("four_3")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_small_view3);
                                                    }else if (ImgContent.indexOf("four_4")!=-1){
                                                        PicassoUtil.getInstantiation().onWidgetImage(mcontext,ImgContent,main_small_view4);
                                                    }
                                                }
                                            }
                                        });
                            }
                            Observable.from(bean.getIndex())
                                    .filter(new Func1<MainDataBean.IndexBean, Boolean>() {
                                        @Override
                                        public Boolean call(MainDataBean.IndexBean indexBean) {
                                            return (( indexBean.getModeType().equals("1.2"))||( indexBean.getModeType().equals("1.3")));
                                        }
                                    })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<MainDataBean.IndexBean>() {
                                        @Override
                                        public void call(MainDataBean.IndexBean indexBean) {
                                            if (indexBean!=null){
                                                WorkLog.e(TAG,"文字"+indexBean.getImplementContent());
                                                if (indexBean.getModeType().equals("1.2")){
                                                    main_broadcas_text.setText(indexBean.getImplementContent());
                                                }else if(indexBean.getModeType().equals("1.3")){
                                                    switch (indexBean.getIndexId()){
                                                        case "13":
                                                            main_home_page.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "14":
                                                            main_boutique_recommend.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "15":
                                                            main_infant_enlightenment.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "16":
                                                            main_primary_and_secondary_schools.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "17":
                                                            main_foreign_study.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "18":
                                                            main_open_class.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "19":
                                                            main_expert_lecture.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "20":
                                                            main_outdoor_activities.setText(indexBean.getImplementContent());
                                                            break;
                                                        case "21":
                                                            main_free_tutorial.setText(indexBean.getImplementContent());
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

//                    Observable.unsafeCreate(new Observable.OnSubscribe<List<MainDataBean.IndexBean>>() {
//                        @Override
//                        public void call(Subscriber<? super List<MainDataBean.IndexBean>> subscriber) {
//
//                        }
//                    }).subscribeOn(Schedulers.io())
//                      .observeOn(AndroidSchedulers.mainThread())
//                      .subscribe(new Subscriber<List<MainDataBean.IndexBean>>() {
//                          @Override
//                          public void onCompleted() {
//
//                          }
//
//                          @Override
//                          public void onError(Throwable e) {
//
//                          }
//
//                          @Override
//                          public void onNext(List<MainDataBean.IndexBean> indexBeen) {
//
//                          }
//                      });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (startPlay) {
            startPlay = false;
            return;
        }
        if (mediaPlayer!=null&& !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mBroadcastReceiver != null) {
                unregisterReceiver(mBroadcastReceiver);
            }
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
        }
        videoStop();
    }

    private void videoStop() {
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer= null;
        }else if (mediaPlayer!=null && !mediaPlayer.isPlaying()){
            mediaPlayer.release();
            mediaPlayer= null;
        }
    }

    private void initVideo() {
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                videoPlay();
            }
        });
    }

    private void videoPlay() {
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(mcontext,Uri.parse(videoPath));
            mediaPlayer.setDisplay(main_surfaceView.getHolder());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            WorkLog.i(TAG, "initVideo: 开始装载");
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initView() {
        main_surfaceView = (SurfaceView) findViewById(R.id.main_surfaceView);
        main_broadcas_text = (TextView) findViewById(R.id.main_broadcas_text);
        main_relative_view1 = (KuangRelativeLayout) findViewById(R.id.main_relative_view1);
        main_relative_view2 = (KuangRelativeLayout) findViewById(R.id.main_relative_view2);
        main_relative_view3 = (KuangRelativeLayout) findViewById(R.id.main_relative_view3);
        main_relative_view4 = (KuangRelativeLayout) findViewById(R.id.main_relative_view4);
        main_relative_view5 = (KuangRelativeLayout) findViewById(R.id.main_relative_view5);
        main_relative_view6 = (KuangRelativeLayout) findViewById(R.id.main_relative_view6);
//
        main_relative_watch_history = (KuangRelativeLayout) findViewById(R.id.main_relative_watch_history);

        main_relative_ranking_list = (KuangRelativeLayout) findViewById(R.id.main_relative_ranking_list);

        main_ralative_small_view1 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view1);
        main_ralative_small_view2 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view2);
        main_ralative_small_view3 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view3);
        main_ralative_small_view4 = (KuangRelativeLayout) findViewById(R.id.main_ralative_small_view4);
        main_ordering = (KuangRelativeLayout) findViewById(R.id.main_ordering);

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

    private void initListener() {
        main_relative_view1.setOnClickListener(this);
        main_relative_view2.setOnClickListener(this);
        main_relative_view3.setOnClickListener(this);
        main_relative_view4.setOnClickListener(this);
        main_relative_view5.setOnClickListener(this);
        main_relative_view6.setOnClickListener(this);

        main_relative_watch_history.setOnClickListener(this);
        main_relative_ranking_list.setOnClickListener(this);
        main_surfaceView.getHolder().addCallback(this);


        main_home_page.setOnClickListener(this);

        main_relative_watch_history.setOnKeyListener(this);
        main_relative_ranking_list.setOnKeyListener(this);

        main_ralative_small_view4.setOnKeyListener(this);
        main_relative_view1.setOnKeyListener(this);
        main_relative_view4.setOnKeyListener(this);
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


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {
            case R.id.main_videoView:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        main_home_page.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.main_ralative_small_view4:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        main_ordering.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.main_relative_view1:
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                        return true;
                }
                break;
            case R.id.main_relative_view4:
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                        return true;
                }
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_relative_view1:
                Configs.intent = new Intent(mcontext,VideoDetailsActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_view2:
                Configs.intent = new Intent(mcontext,VideoDetailsActivity.class);
                startActivity(Configs.intent);;
                break;
            case R.id.main_relative_view3:
                Configs.intent = new Intent(mcontext,VideoDetailsActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_view4:
                Configs.intent = new Intent(mcontext,VideoDetailsActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_watch_history:
                Configs.intent = new Intent(mcontext, WatchHistoryActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_relative_ranking_list:
                Configs.intent = new Intent(mcontext, RankingsActivity.class);
                startActivity(Configs.intent);
                break;
            case R.id.main_home_page:
                Configs.intent = new Intent(mcontext, TvVideoListActivity.class);
                startActivity(Configs.intent);
                break;
            default:
                break;
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
        if (mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    public void onCompletion(android.media.MediaPlayer mp) {
        WorkLog.i(TAG, "onCompletion: 播放完毕");
//        mediaPlayer.reset();
    }

    @Override
    public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
        WorkLog.i(TAG, "onError: 播放错误");
        if (mediaPlayer!=null){
            mediaPlayer.reset();
        }
        return false;

    }

    @Override
    public void onPrepared(android.media.MediaPlayer mp) {
        WorkLog.i(TAG, "onPrepared: ");
        Log.i(TAG, "装载完成");
        mediaPlayer.start();
        // 按照初始位置播放
        mediaPlayer.seekTo(0);
    }
}
