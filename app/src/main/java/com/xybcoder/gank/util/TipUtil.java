package com.xybcoder.gank.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 用于显示提示信息用于显示提示信息
 * Created by dell on 2016/4/15.
 */
public class TipUtil {

    private TipUtil() {

    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener,int duration){
        Snackbar.make(view, tipText, duration).setAction(actionText, listener).show();
    }

    public static void showSnackTip(View view, String tipText) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show();
    }
}
