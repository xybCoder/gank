package com.xybcoder.gank.ui.widget;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

public class GoodAppBarLayout extends AppBarLayout {

    public int offset;
    OnOffsetChangedListener mOnOffsetChangedListener;


    public GoodAppBarLayout(Context context) {
        this(context, null);
    }


    public GoodAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void notifyRemoveOffsetListener() {
        this.removeOnOffsetChangedListener(mOnOffsetChangedListener);
    }


    public void notifyAddOffsetListener() {
        this.addOnOffsetChangedListener(mOnOffsetChangedListener);
    }
}
