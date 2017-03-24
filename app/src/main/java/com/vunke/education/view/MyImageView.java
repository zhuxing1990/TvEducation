package com.vunke.education.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by zhuxi on 2017/3/23.
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
