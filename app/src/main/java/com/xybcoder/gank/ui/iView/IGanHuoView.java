package com.xybcoder.gank.ui.iView;

import com.xybcoder.gank.model.entity.Gank;

import java.util.List;

/**
 * Created by xybcoder on 2016/3/1.
 */
public interface IGanHuoView extends IBaseView {

    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showNoMoreData();
    void showListView(List<Gank> gankList);
}
