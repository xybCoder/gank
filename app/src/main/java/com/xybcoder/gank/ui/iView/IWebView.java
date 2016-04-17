package com.xybcoder.gank.ui.iView;

/**
 * webView
 * Created by xybcoder on 2016/3/16.
 */
public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();

}
