package com.vunke.education.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vunke.education.R;
import com.vunke.education.util.DensityUtil;

//gridView.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
   //         gridView.setSelected(true);//支持选择
   //         gridView.setSelection(0);// 选择当前下标为 0  第一个
   //         gridView.setSelector(android.R.color.transparent);//设置选中后的透明效果
   //         gridView.setMySelector(R.drawable.frame);//设置选中后的边框
   //         gridView.setMyScaleValues(1.1f, 1.1f);//设置选中后 默认扩大倍数

/**
 * Created by zhuxi on 2017/3/11.
 */
@SuppressLint("NewApi")
public class TvFocusGridView2 extends GridView implements AdapterView.OnItemClickListener{
    float mMyScaleX = 1.0f;
    float mMyScaleY = 1.0f;
    protected Rect mMySelectedPaddingRect = new Rect();
    int mPlayIconMargin;
    private int position = 0;

    public TvFocusGridView2(Context contxt) {
        super(contxt);
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public TvFocusGridView2(Context contxt, AttributeSet attrs) {
        super(contxt, attrs);
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public TvFocusGridView2(Context contxt, AttributeSet attrs, int defStyle) {
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
                r.top -= mMySelectedPaddingRect.top + DensityUtil.dip2px(getContext(),-4);
                r.left -= mMySelectedPaddingRect.left +  DensityUtil.dip2px(getContext(),1);
                r.right += mMySelectedPaddingRect.right + DensityUtil.dip2px(getContext(),1);
                r.bottom += mMySelectedPaddingRect.bottom +  DensityUtil.dip2px(getContext(),-1);
//                r.top -= mMySelectedPaddingRect.top + DensityUtil.dip2px(getContext(), 4);
//                r.left -= mMySelectedPaddingRect.left +  DensityUtil.dip2px(getContext(),6);
//                r.right += mMySelectedPaddingRect.right + DensityUtil.dip2px(getContext(), 6);
//                r.bottom += mMySelectedPaddingRect.bottom +  DensityUtil.dip2px(getContext(),4);
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
//                rl.setBackgroundResource(R.drawable.bg_border2);
                ImageView img = (ImageView) rl.getChildAt(0);
                img.setBackgroundResource(R.drawable.bg_border2);
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
            invalidate();
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
    ICoallBack icallBack = null;  
    
    /** 
     * 自定义控件的自定义事件 
     * @param iBack 接口类型 
     */  
    public void setonClick(ICoallBack iBack)  
    {  
        icallBack = iBack;  
    }  

    public interface ICoallBack{  
        public void onClickButton(View arg1, int arg2, long arg3);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		icallBack.onClickButton(view, position, id);
	}
    
}