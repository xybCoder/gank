package com.xybcoder.gank.ui.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.adapter.ListGirlsAdapter;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.presenter.ListGirlsPresenter;
import com.xybcoder.gank.ui.iView.IListGirlsView;
import com.xybcoder.gank.ui.widget.LMRecyclerView;
import com.xybcoder.gank.util.TipUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class ListGirlsActivity extends ToolBarActivity<ListGirlsPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, IListGirlsView, LMRecyclerView.LoadMoreListener {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Meizi> girlslist;
    private ListGirlsAdapter adapter;
    private ListGirlsPresenter presenter;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list_girls;
    }

    @Override
    protected void initPresenter() {
        presenter = new ListGirlsPresenter(this, this);
        presenter.attachView();
    }


    @Override
    public void initView () {
        if (girlslist == null) girlslist = new ArrayList<>();
        adapter = new ListGirlsAdapter(this, girlslist);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadGirls(page);
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
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipUtil.showTipWithAction(container, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadGirls(page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipUtil.showSnackTip(container, getString(R.string.no_more_data));
    }

    @Override
    public void showListView(List<Meizi> gankList) {
        canLoading = true;
        page++;
        if (isRefresh) {
            girlslist.clear();
            girlslist.addAll(gankList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            girlslist.addAll(gankList);
            adapter.notifyDataSetChanged();
        }
    }

        @Override
        protected boolean canBack () {
            return true;
        }

        @Override
        public void loadMore () {
            if (canLoading) {
                presenter.loadGirls(page);
                canLoading = false;
            }
        }

        @Override
        public void onRefresh () {
            isRefresh = true;
            page = 1;
            presenter.loadGirls(page);
        }
    }
