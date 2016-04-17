package com.xybcoder.gank.ui.iView;

import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.model.entity.Meizi;

import java.util.List;

/**
 * Created by dell on 2016/4/16.
 */
public interface IListGirlsView extends IBaseView{

    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showNoMoreData();
    void showListView(List<Meizi> gankList);
}
