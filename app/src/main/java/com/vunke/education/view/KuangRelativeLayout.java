package com.vunke.education.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.vunke.education.R;

/**
 * Created by zhuxi on 2017/3/23.
 */
@SuppressLint("NewApi")
public class KuangRelativeLayout extends RelativeLayout {
    private static final String TAG = "KuangLinearLayout";
    private Rect mBound;
    private Drawable mDrawable;
    private Rect mRect;



    public KuangRelativeLayout(Context context) {
        super(context);
        init();
    }

    public KuangRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KuangRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected void init() {
        setWillNotDraw(false);
        mRect = new Rect();
        mBound = new Rect();
        mDrawable = getResources().getDrawable(R.drawable.kuang2);
        setChildrenDrawingOrderEnabled(true);
        setChildrenDrawingOrderEnabled(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hasFocus()) {
//            Log.d(TAG, "draw: focus true");
            super.getDrawingRect(mRect);
//            Log.d(TAG, "onDraw: "+"left"+mRect.left+""+"top"+mRect.top+"right"+mRect.right+"bottom"+mRect.bottom);
            mBound.set(mRect.left-3, mRect.top-3,mRect.right+3,mRect.bottom+3);
            mDrawable.setBounds(mBound);
            canvas.save();
            mDrawable.draw(canvas);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            bringToFront();
            getRootView().requestLayout();
            getRootView().invalidate();
            zoomOut();
        } else {
            zoomIn();
        }
    }
    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;
    private void zoomIn() {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_small);
        }
        startAnimation(scaleSmallAnimation);

    }

    private void zoomOut() {

        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_big);
        }
        startAnimation(scaleBigAnimation);
    }
}
