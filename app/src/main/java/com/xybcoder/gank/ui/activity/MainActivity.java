package com.xybcoder.gank.ui.activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.umeng.update.UmengUpdateAgent;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.adapter.MeiziAdapter;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.presenter.MainPresenter;
import com.xybcoder.gank.ui.iView.IMainView;
import com.xybcoder.gank.ui.widget.LMRecyclerView;
import com.xybcoder.gank.util.SPDataUtil;
import com.xybcoder.gank.util.TipUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xybcoder on 2016/3/1.
 */

public class MainActivity extends ToolBarActivity<MainPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, IMainView, LMRecyclerView.LoadMoreListener {
    private List<Meizi> meizis;
    private MeiziAdapter adapter;
    private MainPresenter presenter;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @OnClick(R.id.fab)
    void fabClick() {
        presenter.toGanHuoActivity();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        meizis = SPDataUtil.getFirstPageGirls(this);
        if (meizis == null) meizis = new ArrayList<>();
        adapter = new MeiziAdapter(this, meizis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.applyFloatingActionButton(fab);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.fetchMeiziData(page);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });


    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.fetchMeiziData(page);
    }

    @Override
    public void loadMore() {
        if (canLoading) {
            presenter.fetchMeiziData(page);
            canLoading = false;
        }
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipUtil.showTipWithAction(fab, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchMeiziData(page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipUtil.showSnackTip(fab, getString(R.string.no_more_data));

    }

    @Override
    public void showMeiziList(List<Meizi> meiziList) {
        canLoading = true;
        page++;
        if (isRefresh) {
            SPDataUtil.saveFirstPageGirls(this, meiziList);
            meizis.clear();
            meizis.addAll(meiziList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            meizis.addAll(meiziList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }


    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUmeng();
    }

    private void setupUmeng() {
        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setDeltaUpdate(false);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_show_girls:
                presenter.toListGirlsActivity();
                break;
            case R.id.action_about:
                presenter.toAboutActivity();
                break;
            case R.id.action_github_hot:
                String url = getString(R.string.url_github_trending);
                String title = getString(R.string.action_github_trending);
                presenter.openGitHubHot(title,url);
        }
        return super.onOptionsItemSelected(item);
    }


}
