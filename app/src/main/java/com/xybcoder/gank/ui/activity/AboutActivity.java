package com.xybcoder.gank.ui.activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.xybcoder.gank.BuildConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.presenter.AboutPresenter;
import com.xybcoder.gank.ui.iView.IAboutView;
import com.xybcoder.gank.util.ShareUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xybcoder on 2016/3/1.
 */

public class AboutActivity extends ToolBarActivity<AboutPresenter> implements IAboutView{

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @OnClick(R.id.fab)
    void fabClick(){
        presenter.starInMarket();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {
        presenter = new AboutPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        toolbarLayout.setTitle(getString(R.string.about_app));
        tvAppVersion.setText(String.format(getString(R.string.version), BuildConfig.VERSION_NAME));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                ShareUtil.shareApp(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
