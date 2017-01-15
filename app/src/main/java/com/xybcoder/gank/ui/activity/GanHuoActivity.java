package com.xybcoder.gank.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.adapter.GanHuoPagerAdapter;
import com.xybcoder.gank.presenter.GanHuoPresenter;
import com.xybcoder.gank.ui.iView.IBaseView;
import butterknife.BindView;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class GanHuoActivity extends ToolBarActivity<GanHuoPresenter> implements IBaseView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager container;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ganhuo;
    }

    @Override
    protected void initPresenter() {
        presenter = new GanHuoPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        GanHuoPagerAdapter pagerAdapter = new GanHuoPagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(5);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

}
