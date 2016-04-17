package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.http.GankClient;
import com.xybcoder.gank.model.GanHuoData;
import com.xybcoder.gank.model.MeiziData;
import com.xybcoder.gank.ui.iView.IListGirlsView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2016/4/16.
 */
public class ListGirlsPresenter extends BasePresenter<IListGirlsView> {

    public ListGirlsPresenter(Context context, IListGirlsView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }

    public void loadGirls(int page){
        iView.showProgressBar();
        subscription = GankClient.getGankRetrofitInstance().getMeiziData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        iView.hideProgressBar();
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showListView(meiziData.results);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.hideProgressBar();
                        iView.showErrorView();
                    }
                });
    }
}
