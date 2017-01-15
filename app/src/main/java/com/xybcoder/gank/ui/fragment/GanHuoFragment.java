package com.xybcoder.gank.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.adapter.GanHuoAdapter;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.presenter.GanHuoFragmentPresenter;
import com.xybcoder.gank.ui.iView.IGanHuoView;
import com.xybcoder.gank.ui.widget.LMRecyclerView;
import com.xybcoder.gank.util.TipUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class GanHuoFragment extends BaseFragment<GanHuoFragmentPresenter> implements IGanHuoView,
        LMRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TYPE = "type";
    private String type;
    private GanHuoAdapter adapter;
    private List<Gank> gankList;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;


    @BindView(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public GanHuoFragment() {
    }

    public static GanHuoFragment newInstance(String type) {
        GanHuoFragment fragment = new GanHuoFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_ganhuo;
    }

    @Override
    protected void initPresenter() {
        presenter = new GanHuoFragmentPresenter(getActivity(),this);
        presenter.init();
    }

    @Override
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipUtil.showTipWithAction(recyclerView, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipUtil.showSnackTip(recyclerView, getString(R.string.no_more_data));
    }

    @Override
    public void showListView(List<Gank> gankList) {
        canLoading = true;
        page++;
        if (isRefresh) {
            this.gankList.clear();
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initView() {
        gankList = new ArrayList<>();
        adapter = new GanHuoAdapter(gankList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void loadMore() {
        if (canLoading){
            presenter.loadGank(type,page);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadGank(type,page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.release();
    }
}
