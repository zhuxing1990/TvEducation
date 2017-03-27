package com.vunke.education.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.vunke.education.R;
import com.vunke.education.adapter.GrdViewAdapter;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.util.DataPosttingUtil;
import com.vunke.education.view.TvFocusGridView3;

import java.util.List;
import java.util.Map;


public class VideodetailsActivity extends BaseActivity implements View.OnKeyListener{
    //申明控件变量
    private TvFocusGridView3 gr_recommend;
    private Button btn_play;

    private DataPosttingUtil dataPosttingUtil;
    private GrdViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetails);

        //初始化数据
        initData();

        //找到需要的控件
        gr_recommend = (TvFocusGridView3) findViewById(R.id.gr_recommend);
        btn_play = (Button) findViewById(R.id.btn_play);

        //给按钮设置焦点监听事件
        btn_play.setOnFocusChangeListener(btnpalyListener);
        btn_play.setOnKeyListener(this);


        if (adapter !=null){
            //设置数据适配器
            gr_recommend.setAdapter(adapter);
            gr_recommend.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
            gr_recommend.setSelected(true);//支持选择
            gr_recommend.setSelection(0);// 选择当前下标为 0  第一个
            gr_recommend.setSelector(android.R.color.transparent);//设置选中后的透明效果
            gr_recommend.setMySelector(R.drawable.kuang2);//设置选中后的边框
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
        int[] recommendpicture ={R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,
                R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq,R.drawable.chanpinxq};
        String[] recommendptext={"儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学","儿童趣味数学",
                "儿童趣味数学","儿童趣味数学"};
        //获取数据适配器对象
        List<Map<String, Object>> list = DataPosttingUtil.posttingList(recommendpicture, recommendptext);
        if (list.size()!=0&& !list.isEmpty()){
            adapter = new GrdViewAdapter(list,getApplicationContext());
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch(v.getId()){
            case R.id.btn_play:
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                        gr_recommend.requestFocus();
                        gr_recommend.setSelection(0);
                        return true;
                    }
                break;
            default:
                break;
        }
        return false;
    }
}
