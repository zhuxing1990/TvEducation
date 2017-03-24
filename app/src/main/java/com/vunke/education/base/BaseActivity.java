package com.vunke.education.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vunke.education.manage.AppManager;


/**
 * Created by zhuxi on 2017/3/9.
 */
public class BaseActivity extends AppCompatActivity {
    public BaseActivity mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = this;
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
    /**
     * 吐司
     * */
    public void showToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT)
                .show();
    }

}
