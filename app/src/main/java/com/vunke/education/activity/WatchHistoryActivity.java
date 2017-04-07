package com.vunke.education.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.view.KuangRelativeLayout2;

/**
 * Created by zhuxi on 2017/3/25.
 */
public class WatchHistoryActivity extends BaseActivity implements View.OnClickListener, View.OnKeyListener {
    private KuangRelativeLayout2 wh_recentlylist_rl1;
    private KuangRelativeLayout2 wh_recentlylist_rl2;
    private KuangRelativeLayout2 wh_recentlylist_rl3;
    private KuangRelativeLayout2 wh_recentlylist_rl4;
    private KuangRelativeLayout2 wh_todaylist_rl1;
    private KuangRelativeLayout2 wh_todaylist_rl2;
    private KuangRelativeLayout2 wh_todaylist_rl3;
    private KuangRelativeLayout2 wh_todaylist_rl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchhistory);
        initView();
        initListener();
    }


    private void initView() {
        wh_recentlylist_rl1 = (KuangRelativeLayout2) findViewById(R.id.wh_recentlylist_rl1);
        wh_recentlylist_rl2 = (KuangRelativeLayout2) findViewById(R.id.wh_recentlylist_rl2);
        wh_recentlylist_rl3 = (KuangRelativeLayout2) findViewById(R.id.wh_recentlylist_rl3);
        wh_recentlylist_rl4 = (KuangRelativeLayout2) findViewById(R.id.wh_recentlylist_rl4);

        wh_todaylist_rl1 = (KuangRelativeLayout2) findViewById(R.id.wh_todaylist_rl1);
        wh_todaylist_rl2 = (KuangRelativeLayout2) findViewById(R.id.wh_todaylist_rl2);
        wh_todaylist_rl3 = (KuangRelativeLayout2) findViewById(R.id.wh_todaylist_rl3);
        wh_todaylist_rl4 = (KuangRelativeLayout2) findViewById(R.id.wh_todaylist_rl4);

    }

    private void initListener() {
        wh_recentlylist_rl1.setOnClickListener(this);
        wh_recentlylist_rl1.setOnKeyListener(this);

        wh_recentlylist_rl2.setOnClickListener(this);
        wh_recentlylist_rl2.setOnKeyListener(this);

        wh_recentlylist_rl3.setOnClickListener(this);
        wh_recentlylist_rl3.setOnKeyListener(this);

        wh_recentlylist_rl4.setOnClickListener(this);
        wh_recentlylist_rl4.setOnKeyListener(this);

        wh_todaylist_rl1.setOnClickListener(this);
        wh_todaylist_rl1.setOnKeyListener(this);

        wh_todaylist_rl2.setOnClickListener(this);
        wh_todaylist_rl2.setOnKeyListener(this);

        wh_todaylist_rl3.setOnClickListener(this);
        wh_todaylist_rl3.setOnKeyListener(this);

        wh_todaylist_rl4.setOnClickListener(this);
        wh_todaylist_rl4.setOnKeyListener(this);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {
//            KEYCODE_DPAD_UP=19;
//
//            KEYCODE_DPAD_DOWN=20;
//
//            KEYCODE_DPAD_LEFT=21;
//
//            KEYCODE_DPAD_RIGHT=22;

            case R.id.wh_todaylist_rl1:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        wh_recentlylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_todaylist_rl2:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        wh_recentlylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_todaylist_rl3:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        wh_recentlylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_todaylist_rl4:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        wh_recentlylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_recentlylist_rl1:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        wh_todaylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_recentlylist_rl2:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        wh_todaylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_recentlylist_rl3:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        wh_todaylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;
            case R.id.wh_recentlylist_rl4:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        wh_todaylist_rl1.requestFocus();
                        return true;
                    }
                }
                break;

        }
        return false;
    }
}
