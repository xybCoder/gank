package com.xybcoder.gank.ui.iView;
import com.xybcoder.gank.model.entity.Gank;

import java.util.List;

/**
 * 干货view
 * Created by xybcoder on 2016/3/1.
 */
public interface IGankView extends IBaseView {
    void showGankList(List<Gank> gankList);
    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
}
