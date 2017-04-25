package com.vunke.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.education.R;
import com.vunke.education.adapter.InfoVideoQueryAdapter;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.base.Configs;
import com.vunke.education.log.WorkLog;
import com.vunke.education.model.InfoVideoQueryBean;
import com.vunke.education.model.VideoDetailsBean;
import com.vunke.education.network_request.NetWorkRequest;
import com.vunke.education.util.DataPosttingUtil;
import com.vunke.education.util.PicassoUtil;
import com.vunke.education.view.TvFocusGridView3;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


public class VideoDetailsActivity extends BaseActivity implements View.OnKeyListener,View.OnClickListener{
    private static final String TAG = "VideoDetailsActivity";
    //申明控件变量
    private TvFocusGridView3 gr_recommend;
    private Button btn_play;

    private DataPosttingUtil dataPosttingUtil;
    private InfoVideoQueryAdapter adapter;
    private  VideoDetailsBean videoDetailsBean;
    private InfoVideoQueryBean infoVideoQueryBean;
    private  Gson gson;
    private int infoId = -1;
    private TextView videodetails_classify;
    private TextView videodetails_videotime;
    private TextView videodetails_video_cp;
    private TextView videodetails_createtime;
    private TextView videodetails_synopslscontent;
    private TextView videodetails_videotitle;
    private ImageView videodetails_videoimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetails);
        getIntentData();
        initView();
        //初始化数据
        initData();
        initListener();
    }
    public void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")){
            infoId = intent.getIntExtra("infoId",-1);
            WorkLog.i(TAG, "getIntentData: infoId:"+infoId);
        }
    }
    private void initView() {
        //找到需要的控件
        gr_recommend = (TvFocusGridView3) findViewById(R.id.gr_recommend);
        btn_play = (Button) findViewById(R.id.btn_play);
        videodetails_classify = (TextView) findViewById(R.id.videodetails_classify);
        videodetails_videotime = (TextView) findViewById(R.id.videodetails_videotime);
        videodetails_video_cp = (TextView) findViewById(R.id.videodetails_video_cp);
        videodetails_createtime = (TextView) findViewById(R.id.videodetails_createtime);
        videodetails_synopslscontent = (TextView) findViewById(R.id.videodetails_synopslscontent);
        videodetails_videotitle = (TextView) findViewById(R.id.videodetails_videotitle);
        videodetails_videoimg = (ImageView) findViewById(R.id.videodetails_item_videoimg);
    }
    private void initListener() {
        //给按钮设置焦点监听事件
        btn_play.setOnFocusChangeListener(btnpalyListener);
        btn_play.setOnKeyListener(this);
        btn_play.setOnClickListener(this);
    }
    private void initGridView() {
        if (adapter !=null){
            //设置数据适配器
            gr_recommend.setAdapter(adapter);
            gr_recommend.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
            gr_recommend.setSelected(true);//支持选择
            gr_recommend.setSelection(0);// 选择当前下标为 0  第一个
            gr_recommend.setSelector(android.R.color.transparent);//设置选中后的透明效果
            gr_recommend.setMySelector(R.drawable.kuang3);//设置选中后的边框
            gr_recommend.setMyScaleValues(1.1f, 1.1f);//设置选中后 默认扩大倍数
        }
    }

   private View.OnFocusChangeListener btnpalyListener = new View.OnFocusChangeListener() {
       @Override
       public void onFocusChange(View v, boolean hasFocus) {

           if (hasFocus){

               btn_play.requestFocus();
               btn_play.setBackgroundResource(R.drawable.playbtnfocus);
           }else {

               btn_play.setBackgroundResource(R.drawable.play_2);
           }

       }
   };

    private void initData() {
        if (infoId==-1){
            showToast("未找到相关信息");
            return;
        }
        //json = {“versionCode”,”xx”,”userId”:”id”,”infoId”:”infoid”}
        try {
            JSONObject json = new JSONObject();
            json.put("versionCode","1");
            json.put("userId","test");
            json.put("infoId",infoId);
            WorkLog.i(TAG, "initData: json:"+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FIND_BYI_ID_INFO).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
                    try{
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")){
                            int code = js.getInt("code");
                            switch (code){
                                case 200:
                                    gson = new Gson();
                                    videoDetailsBean = gson.fromJson(s, VideoDetailsBean.class);
                                    WorkLog.i(TAG,"get data"+ videoDetailsBean.toString());
                                    break;
                                case 400:
                                case 500:
                                    break;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    WorkLog.i(TAG, "onAfter: ");
                    if (videoDetailsBean!=null && !TextUtils.isEmpty(videoDetailsBean.getCode())){
                        //初始化相关视频数据
                        initRelatedData();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
//        int[] recommendpicture ={R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,
//                R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq};
//        String[] recommendptext={"儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学",
//                "儿童趣味数学","儿童趣味数学"};
//        //获取数据适配器对象
//        List<Map<String, Object>> list = DataPosttingUtil.posttingList(recommendpicture, recommendptext);
//        if (list.size()!=0&& !list.isEmpty()){
//            adapter = new InfoVideoQueryAdapter(list,getApplicationContext());
//        }
    }
    private void initRelatedData() {
        if (videoDetailsBean.getMode()==null){
            showToast("未找到相关信息");
            return;
        }
        videodetails_synopslscontent.setText(videoDetailsBean.getMode().getContent());
        videodetails_video_cp.setText(videoDetailsBean.getMode().getString2());
        videodetails_createtime.setText(videoDetailsBean.getMode().getCreatetime());
        videodetails_videotitle.setText(videoDetailsBean.getMode().getTitle());
        videodetails_videotime.setText(videoDetailsBean.getMode().getString1());
        PicassoUtil.getInstantiation().onBigNetImage(mcontext,videoDetailsBean.getMode().getMainpicurl(),videodetails_videoimg);
        videodetails_classify.setText(videoDetailsBean.getType());
        if (TextUtils.isEmpty(videoDetailsBean.getMode().getIstop())){
            showToast("未找到相关信息");
            return;
        }
        try{
            //json={"istop":"1,2,3,4,5,6","userId":"admin","versionCode":"1.0"}

            JSONObject json = new JSONObject();
            json.put("istop",videoDetailsBean.getMode().getIstop());
            json.put("userId","1");
            json.put("versionCode","1");
            WorkLog.i(TAG,"json:"+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.INFO_VIDEO_QUERY).tag(this).params("json",json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if(js.has("code")){
                            int code = js.getInt("code");
                            switch (code){
                                case 200:
                                    gson = new Gson();
                                     infoVideoQueryBean = gson.fromJson(s, InfoVideoQueryBean.class);
                                    WorkLog.i(TAG, "onSuccess: infoVideoQueryBean"+infoVideoQueryBean.toString());
                                    break;
                            }
                        };
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    WorkLog.i(TAG, "onAfter: ");
                    if (infoVideoQueryBean!=null ){
                        if (null !=infoVideoQueryBean.getInfoVideo()&&infoVideoQueryBean.getInfoVideo().size()!=0)
                         adapter = new InfoVideoQueryAdapter(infoVideoQueryBean.getInfoVideo(),mcontext);
                        initGridView();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch(v.getId()){
            case R.id.btn_play:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        gr_recommend.requestFocus();
                        gr_recommend.setSelection(0);
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
     String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";
//    String videoPath = "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";
 //   String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                Configs.intent = new Intent(mcontext,VideoPlay2Activity.class);
                Configs.intent.putExtra("videoPath",videoPath);
                startActivity(Configs.intent);
                break;
        }
    }


}
