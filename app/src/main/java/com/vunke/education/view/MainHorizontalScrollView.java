package com.vunke.education.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by zhuxi on 2017/3/23.
 */
public class MainHorizontalScrollView extends HorizontalScrollView {
    public MainHorizontalScrollView(Context context) {
        super(context);
    }

    public MainHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isHorizontalScrollBarEnabled() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
