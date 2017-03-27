package com.vunke.education.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vunke.education.util.DensityUtil;

/**
 * Created by zhuxi on 2017/3/11.
 */
@SuppressLint("NewApi")
public class TvFocusGridView3 extends GridView {
    float mMyScaleX = 1.0f;
    float mMyScaleY = 1.0f;
    protected Rect mMySelectedPaddingRect = new Rect();
    int mPlayIconMargin;
    private int position = 0;

    public TvFocusGridView3(Context contxt) {
        super(contxt);
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public TvFocusGridView3(Context contxt, AttributeSet attrs) {
        super(contxt, attrs);
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public TvFocusGridView3(Context contxt, AttributeSet attrs, int defStyle) {
        super(contxt, attrs, defStyle);
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipToPadding(false);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (mMySelectedDrawable == null)
                return;
            drawSelector(canvas);
    }

    /**
     *  设置焦点框的图片
     *  @param resId
     * @targetapi
     */

    public void setMySelector(int resId) {
        mMySelectedDrawable = getResources().getDrawable(resId);
        mMySelectedPaddingRect = new Rect();
        mMySelectedDrawable.getPadding(mMySelectedPaddingRect);//获取drawable所画区域的内边框
    }

    protected Drawable mMySelectedDrawable = null;
    protected View mMySelectedView = null;
    protected Rect mTmpSelectedRect = new Rect();
    protected Rect mTmpGridViewRect = new Rect();

    /**
     *  这是关键点
     *  @param canvas
     */
    protected void drawSelector(Canvas canvas) {
        View v = getSelectedView();
        bringChildToFront(v);
        if (isFocused() && v != null) {
            scaleCurrentView();
            if (v instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) v;
                ImageView tmepView = (ImageView) rl.getChildAt(0);
//                TextView tv = (TextView) rl.getChildAt(1);
//                tv.setTextColor(Color.WHITE);
//                v.setBackgroundResource(R.color.color_background_blue2);
                Rect r = mTmpSelectedRect;
                tmepView.getGlobalVisibleRect(r);//Rect(62, 152 - 398, 512) 计算出imageview在屏幕的坐标点
                getGlobalVisibleRect(mTmpGridViewRect);//计算出屏幕的坐标点 Rect(45, 141 - 1875, 1035)
                r.offset(-mTmpGridViewRect.left, -mTmpGridViewRect.top);//向左移动--mTmpGridViewRect.left就是向右滑动-mTmpGridViewRect.left
                r.top -= mMySelectedPaddingRect.top + DensityUtil.dip2px(getContext(), 5);
                r.left -= mMySelectedPaddingRect.left + DensityUtil.dip2px(getContext(),5);
                r.right += mMySelectedPaddingRect.right + DensityUtil.dip2px(getContext(), 5);
                r.bottom += mMySelectedPaddingRect.bottom + DensityUtil.dip2px(getContext(), 5);
                mMySelectedDrawable.setBounds(r);
                mMySelectedDrawable.draw(canvas);
            }
        }
    }

    public void setMyScaleValues(float scaleX, float scaleY) {
        mMyScaleX = scaleX;
        mMyScaleY = scaleY;
    }

    void scaleCurrentView() {
        View v = getSelectedView();
        unScalePrevView();
        if (v != null) {
            mMySelectedView = v;
            mMySelectedView.setScaleX(mMyScaleX);
            mMySelectedView.setScaleY(mMyScaleY);
        }
    }

    /**
     *  把上一次获取焦点 还原
     */
    void unScalePrevView() {
        if (mMySelectedView != null) {
            if (mMySelectedView instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) mMySelectedView;
//                rl.setBackgroundResource(R.color.color_background_blue1);
//                TextView tv = (TextView) rl.getChildAt(1);
//                tv.setTextColor(Color.WHITE);
            }
            mMySelectedView.setScaleX(1);
            mMySelectedView.setScaleY(1);
            mMySelectedView = null;
        }
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (!gainFocus) {
            unScalePrevView();
            requestLayout();
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    protected int mMyVerticalSpacing = 0;

    public void setMyVerticalSpacing(int verticalSpacing) {
        mMyVerticalSpacing = verticalSpacing;
    }

    public int getMyVerticalSpacing() {
        return mMyVerticalSpacing;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (position != -1) {
            if (i == childCount - 1)
                return position;
            if (i == position)
                return childCount - 1;
        }
        return i;
    }

    @Override
    public void bringChildToFront(View child) {
        position = indexOfChild(child);
        postInvalidate();
    }

}



