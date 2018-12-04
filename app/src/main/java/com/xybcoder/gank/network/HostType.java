package com.xybcoder.gank.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//请求数据host的类型
public class HostType {
    //多少种Host类型
    public static final int TYPE_COUNT = 3;

    //网易新闻视频的host
    @HostTypeChecker
    public static final int GANK_TYPE = 1;

    //新浪图片的HOST
    @HostTypeChecker
    public static final int NEWS_TYPE = 2;


    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({GANK_TYPE, NEWS_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker { }
}
