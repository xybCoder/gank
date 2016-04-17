package com.xybcoder.gank.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.xybcoder.gank.model.entity.Gank;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class StringStyleUtil {
    private StringStyleUtil(){

    }
    public static SpannableString getGankStyleStr(Gank gank){
        String gankStr = gank.desc + " @" + gank.who;
        SpannableString spannableString = new SpannableString(gankStr);
        spannableString.setSpan(new RelativeSizeSpan(0.8f),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
