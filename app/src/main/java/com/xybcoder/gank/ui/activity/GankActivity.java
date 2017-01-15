package com.xybcoder.gank.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ShareElement;
import com.xybcoder.gank.ui.adapter.GankAdapter;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.presenter.GankPresenter;
import com.xybcoder.gank.ui.iView.IGankView;
import com.xybcoder.gank.util.DateUtil;
import com.xybcoder.gank.util.NetworkUtils;
import com.xybcoder.gank.util.ShareUtil;
import com.xybcoder.gank.util.TipUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class GankActivity extends ToolBarActivity<GankPresenter> implements IGankView {

    private Meizi meizi;
    private List<Gank> list;
    private GankAdapter adapter;
    private Calendar calendar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;
    @BindView(R.id.progressbar)
    SmoothProgressBar progressbar;

    @OnClick(R.id.fab)
    void fabClick() {
        if (!NetworkUtils.isWIFIConnected(this)) {
            TipUtil.showTipWithAction(fab, "你使用的不是wifi网络，要继续吗？", "继续", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() > 0 && list.get(0).type.equals("休息视频")) {
                        Intent intent = new Intent(GankActivity.this, WebVideoActivity.class);
                        intent.putExtra(GankConfig.GANK, list.get(0));
                        startActivity(intent);
                    } else {
                        TipUtil.showSnackTip(fab, getString(R.string.something_wrong));
                    }
                }
            }, Snackbar.LENGTH_LONG);
        }else {
            if (list.size() > 0 && list.get(0).type.equals("休息视频")) {
                Intent intent = new Intent(GankActivity.this, WebVideoActivity.class);
                intent.putExtra(GankConfig.GANK, list.get(0));
                startActivity(intent);
            } else {
                TipUtil.showSnackTip(fab, getString(R.string.something_wrong));
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initPresenter() {
        presenter = new GankPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        getIntentData();
        initGankView();
    }

    private void initGankView() {
        list = new ArrayList<>();
        adapter = new GankAdapter(list, this);
        rvGank.setLayoutManager(new LinearLayoutManager(this));
        rvGank.setItemAnimator(new DefaultItemAnimator());
        rvGank.setAdapter(adapter);
        setTitle(DateUtil.toDateString(meizi.publishedAt));
        ivHead.setImageDrawable(ShareElement.shareDrawable);
        ViewCompat.setTransitionName(ivHead, GankConfig.TRANSLATE_GIRL_VIEW);
        fab.setClickable(false);
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(GankConfig.MEIZI);
        calendar = Calendar.getInstance();
        calendar.setTime(meizi.publishedAt);
        presenter.fetchGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void showGankList(List<Gank> gankList) {
        list.addAll(gankList);
        adapter.notifyDataSetChanged();
        fab.setClickable(true);
    }

    @Override
    public void showProgressBar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        TipUtil.showTipWithAction(fab, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchGankData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ShareUtil.shareApp(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareElement.shareDrawable = null;
        list = null;
        presenter.release();
        presenter = null;
    }
}
