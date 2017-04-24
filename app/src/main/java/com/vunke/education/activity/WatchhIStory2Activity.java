package com.vunke.education.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.androidtvwidget.bridge.EffectNoDrawBridge;
import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;
import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.log.WorkLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuxi on 2017/4/20.
 */
public class WatchHistory2Activity extends BaseActivity implements View.OnKeyListener{
    private static final String TAG = "WatchHistory2Activity";
    private GridViewTV watchhistory_gridview;
    private GridViewTV watchhistory_gridview2;
    private MainUpView watch_history_mainUpView;
    private List<Map<String,Object>> list;
    private Map<String,Object> map;
    private View mOldView;
    private WatchHistoryAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchhistory2);
        initView();
        initListener();
        setMainUpView();
        getData();
        UpdateMyAdapter();
    }

    private void initView() {
        watchhistory_gridview = (GridViewTV) findViewById(R.id.watchhistory_gridview);
        watchhistory_gridview2 = (GridViewTV) findViewById(R.id.watchhistory_gridview2);
        watch_history_mainUpView = (MainUpView) findViewById(R.id.watch_history_mainUpView);
    }

    private void initListener() {
        watchhistory_gridview.setOnKeyListener(this);
        watchhistory_gridview2.setOnKeyListener(this);
    }

    private void setMainUpView() {
        watch_history_mainUpView.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridge = (EffectNoDrawBridge) watch_history_mainUpView.getEffectBridge();
        bridge.setTranDurAnimTime(300);
        watch_history_mainUpView.setUpRectResource(R.drawable.kuang2);
        // 移动方框缩小的距离.
        watch_history_mainUpView.setDrawUpRectPadding(new Rect(-34,24, -36, 24));
    }

    public void getData() {
        list = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            map = new HashMap<>();
            map.put("tupian","");
            map.put("ziti","测试 "+i);
            list.add(map);
        }
    }

    private void UpdateMyAdapter() {
        watchhistory_gridview.setNumColumns(5);
        watchhistory_gridview2.setNumColumns(5);

        adapter = new WatchHistoryAdapter(mcontext,list);

        watchhistory_gridview.setAdapter(adapter);
        watchhistory_gridview2.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        watchhistory_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        watchhistory_gridview2.setSelector(new ColorDrawable(Color.TRANSPARENT));

        watchhistory_gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view!=null){
                    watch_history_mainUpView.setFocusView(view, mOldView, 1.15f);
                }
                mOldView = view;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                WorkLog.i(TAG, "onNothingSelected-----------------------------1");
            }
        });
        watchhistory_gridview2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view!=null){
                    watch_history_mainUpView.setFocusView(view, mOldView, 1.15f);
                }
                mOldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                WorkLog.i(TAG, "onNothingSelected-----------------------------2");
            }
        });
        mFirstHandler.sendEmptyMessageDelayed(0x11, 188);
    }

    // 延时请求初始位置的item.
    Handler mFirstHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    watchhistory_gridview.setDefualtSelect(0);
                    adapter.notifyDataSetChanged();
                    break;
                case 0x12:
                    watchhistory_gridview2.setDefualtSelect(0);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.watchhistory_gridview:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            WorkLog.i(TAG, "down-----------------------------");
                            mFirstHandler.sendEmptyMessageDelayed(0x12, 400);
                            return true;
                        }
                    }
                    break;
                case R.id.watchhistory_gridview2:
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            WorkLog.i(TAG, "up-----------------------------");
                            mFirstHandler.sendEmptyMessageDelayed(0x11, 400);
                            return true;
                        }
                    }
                    break;
            }
        return false;
    }

    public class WatchHistoryAdapter extends BaseAdapter {
        private Context context;
        private  List<Map<String,Object>> list;
        public WatchHistoryAdapter(Context context, List<Map<String,Object>> list){
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHodler hodler;
            if (convertView==null){
                hodler = new MyViewHodler();
                convertView = View.inflate(context,R.layout.recycleview_item_watchhistory,null);
                hodler.textview = (TextView) convertView.findViewById(R.id.watchhistory_item_tv);
                hodler.imageView = (ImageView) convertView.findViewById(R.id.watchhistory_item_img);
                hodler.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.watchhistory_item_layout);
                hodler.relativeLayout.requestFocus();
                convertView.setTag(hodler);
            }else{
                hodler = (MyViewHodler) convertView.getTag();
            }

            return convertView;
        }
        public class MyViewHodler {
            TextView textview;
            ImageView imageView;
            RelativeLayout relativeLayout;
        }
    }

}
