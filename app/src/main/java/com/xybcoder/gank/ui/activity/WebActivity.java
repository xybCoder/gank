package com.xybcoder.gank.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.presenter.WebViewPresenter;
import com.xybcoder.gank.ui.iView.IWebView;
import com.xybcoder.gank.util.TipUtil;
import butterknife.BindView;

/**
 * Created by xybcoder on 2016/3/16.
 */
public class WebActivity  extends ToolBarActivity<WebViewPresenter> implements IWebView {

    private Gank gank;
    WebViewPresenter presenter;
    LinearLayout contentView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progressbar)
    NumberProgressBar progressbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initPresenter() {
        presenter = new WebViewPresenter(this, this);
        presenter.init();
    }
    public static void loadWebViewActivity(Context from, Gank gank) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra(GankConfig.GANK, gank);
        from.startActivity(intent);
    }
    @Override
    public void showProgressBar(int progress) {
        if (progressbar == null) return;
        progressbar.setProgress(progress);
        if (progress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setWebTitle(String title) {
        setTitle(title);
    }

    @Override
    public void openFailed() {
        TipUtil.showSnackTip(webView, getString(R.string.open_url_failed));
    }

    @Override
    public void initView() {
        gank = (Gank) getIntent().getSerializableExtra(GankConfig.GANK);
        setTitle(gank.desc);
        presenter.setWebViewSettings(webView, gank.url);
        contentView = (LinearLayout) findViewById(R.id.web_content);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.refresh(webView);
                break;
            case R.id.action_copy_url:
                presenter.copyUrl(webView.getUrl());
                break;
            case R.id.action_open_in_browser:
                presenter.openInBrowser(webView.getUrl());
                break;
            case R.id.action_share_gank:
                presenter.moreOperation(gank);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if (webView != null) webView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            contentView.removeView(webView);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        presenter.release();
    }
}
