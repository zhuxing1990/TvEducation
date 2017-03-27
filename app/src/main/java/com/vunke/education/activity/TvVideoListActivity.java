package com.vunke.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.vunke.education.R;
import com.vunke.education.adapter.TvVideoListAdapter;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.base.Configs;
import com.vunke.education.model.VideoListBean;
import com.vunke.education.view.KuangRelativeLayout;
import com.vunke.education.view.KuangTextView;
import com.vunke.education.view.TvFocusGridView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TvVideoListActivity extends BaseActivity implements View.OnClickListener, View.OnKeyListener {
    private static final String TAG = "TvVideoListActivity";
    private TvFocusGridView videolist_gridview;
    private KuangTextView videolist_bestv;
    private KuangTextView videolist_aitewei;
    private KuangTextView videolist_xdf;
    private boolean isFirst_catalog = false;
    private ScrollView videolist_school_scroll;
    private KuangRelativeLayout videolist_primary_school;
    private VideoListBean videoListBean;
    private List<VideoListBean> videoList;
    private Subscription subscribe;
    private TvVideoListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_video_list);
        initView();
        initListener();
        initData();
        ininGridView();
    }

    private void initData() {

        videoList = new ArrayList<>();

        subscribe = Observable.unsafeCreate(new Observable.OnSubscribe<List<VideoListBean>>() {
            @Override
            public void call(Subscriber<? super List<VideoListBean>> subscriber) {
                for (int i = 0; i < 50; i++) {
                    videoListBean = new VideoListBean();
                    videoListBean.setVideoDrawable(getResources().getDrawable(R.drawable.videolist_gridview_item_img));
                    videoListBean.setVideoImgPath("");
                    videoListBean.setVideoName("测试" + i);
                    videoListBean.setVideoPath("http://movie.ks.js.cn/flv/other/1_0.mp4");
                    videoListBean.setVideoTime("1:00");
                    videoList.add(videoListBean);
                }
                subscriber.onNext(videoList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoListBean>>() {
                    @Override
                    public void onCompleted() {
                        videolist_gridview.setNumColumns(4);//设置girdView列数  1行6列
                        videolist_gridview.setGravity(Gravity.CENTER);// 位置居中
//                        videolist_gridview.setVerticalSpacing(12);// 垂直间隔
                        // gridView.setHorizontalSpacing(8);// 水平间隔
//                        videolist_gridview.setStretchMode(GridView);
                        videolist_gridview.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
                        videolist_gridview.setSelected(true);//支持选择
                        videolist_gridview.setSelection(0);// 选择当前下标为 0  第一个
                        videolist_gridview.setSelector(android.R.color.transparent);//设置选中后的透明效果
                        videolist_gridview.setMySelector(R.drawable.kuang2);//设置选中后的边框
                        videolist_gridview.setMyScaleValues(1.1f, 1.1f);//设置选中后 默认扩大倍数
                        videolist_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Configs.intent = new Intent(TvVideoListActivity.this,VideodetailsActivity.class);
                                startActivity(Configs.intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: rx:initData");
                        e.printStackTrace();
                        subscribe.unsubscribe();
                    }

                    @Override
                    public void onNext(List<VideoListBean> videoListBeen) {
                        if (!videoListBeen.isEmpty()&&videoListBeen.size()!=0){
                            adapter = new TvVideoListAdapter(videoListBeen,getApplicationContext());
                            videolist_gridview.setAdapter(adapter);
                        }
                    }
                });
    }

    private void initListener() {
//        videolist_bestv.setOnClickListener(this);
//        videolist_aitewei.setOnClickListener(this);
//        videolist_xdf.setOnClickListener(this);
//
//        videolist_bestv.setOnKeyListener(this);
//        videolist_aitewei.setOnKeyListener(this);
//        videolist_xdf.setOnKeyListener(this);
    }

    private void initView() {
        videolist_school_scroll = (ScrollView) findViewById(R.id.videolist_school_scroll);
        videolist_bestv = (KuangTextView) findViewById(R.id.videolist_bestv);
        videolist_aitewei = (KuangTextView) findViewById(R.id.videolist_aitewei);
        videolist_xdf = (KuangTextView) findViewById(R.id.videolist_xdf);
        videolist_primary_school = (KuangRelativeLayout) findViewById(R.id.videolist_primary_school);
        videolist_gridview = (TvFocusGridView) findViewById(R.id.videolist_gridview);

    }

    private void ininGridView() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.videolist_bestv) {
            isFirst_catalog = !isFirst_catalog;
            setLastCatalog();
        } else {

        }
    }

    private void setLastCatalog() {
        if (isFirst_catalog) {
            videolist_school_scroll.setVisibility(View.GONE);
        } else {
            videolist_school_scroll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.videolist_bestv:
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        isFirst_catalog = !isFirst_catalog;
                        setLastCatalog();
                        videolist_primary_school.requestFocus();
                        return true;
                    }
                    break;
                case R.id.videolist_aitewei:
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        isFirst_catalog = !isFirst_catalog;
                        setLastCatalog();
                        videolist_primary_school.requestFocus();
                        return true;
                    }
                    break;
                case R.id.videolist_xdf:
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        isFirst_catalog = !isFirst_catalog;
                        setLastCatalog();
                        videolist_primary_school.requestFocus();
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
            subscribe=null;
        }
    }
}
