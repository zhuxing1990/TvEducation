package com.vunke.education.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zhuxi on 2017/4/21.
 */
public class TestGridView extends GridView {
    public TestGridView(Context context) {
        super(context);
    }

    public TestGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public boolean isInTouchMode() {
        return !(hasFocus() && !super.isInTouchMode());
    }

    private void init(Context context, AttributeSet attrs) {
       setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (getSelectedItemPosition() != -1) {
            if (i + getFirstVisiblePosition() ==getSelectedItemPosition()) {// 这是原本要在最后一个刷新的item
                return childCount - 1;
            }
            if (i == childCount - 1) {// 这是最后一个需要刷新的item
                return getSelectedItemPosition() - getFirstVisiblePosition();
            }
        }
        return i;
    }

    public void setDefualtSelect(int pos) {
        requestFocusFromTouch();
        setSelection(pos);
    }

}
