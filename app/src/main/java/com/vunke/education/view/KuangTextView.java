package com.vunke.education.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.vunke.education.R;

/**
 * Created by zhuxi on 2017/3/24.
 */
public class KuangTextView extends TextView {

    public KuangTextView(Context context) {
        super(context);
        init();
    }

    public KuangTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KuangTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected  void init(){
        setFocusableInTouchMode(true);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }
    @SuppressLint("NewApi")
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            bringToFront();
            setTextColor(getResources().getColor(android.R.color.white));
            getRootView().requestLayout();
            getRootView().invalidate();
            zoomOut();
        } else {
            setTextColor(getResources().getColor(R.color.color_text_white3));
            zoomIn();
        }
    }
    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;
    private void zoomIn() {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_small2);
        }
        startAnimation(scaleSmallAnimation);

    }

    private void zoomOut() {

        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_big2);
        }
        startAnimation(scaleBigAnimation);
    }
}
