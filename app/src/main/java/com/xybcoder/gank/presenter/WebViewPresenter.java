package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xybcoder.gank.R;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.ui.iView.IWebView;
import com.xybcoder.gank.util.CommonUtil;
import com.xybcoder.gank.util.ShareUtil;

/**
 * Created by xybcoder on 2016/3/16.
 */
public class WebViewPresenter extends BasePresenter<IWebView> {

    public WebViewPresenter(Context context, IWebView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }

    public void setWebViewSettings(WebView webView, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new GankClient());
        webView.loadUrl(url);
    }

    public void refresh(WebView webView) {
        webView.reload();
    }

    public void copyUrl(String text) {
        CommonUtil.copyToClipBoard(context, text, context.getString(R.string.copy_success));
    }

    public void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            iView.openFailed();
        }
    }

    public void moreOperation(Gank gank) {
        if (gank != null)
            ShareUtil.shareGank(context, gank);
    }


    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            iView.showProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            iView.setWebTitle(title);
        }
    }

    private class GankClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
}
