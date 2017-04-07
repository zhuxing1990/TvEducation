package com.vunke.education.base;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by zhuxi on 2017/3/29.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
    }
}
