package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.http.GankClient;
import com.xybcoder.gank.model.GanHuoData;
import com.xybcoder.gank.ui.iView.IGanHuoView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class GanHuoFragmentPresenter extends BasePresenter<IGanHuoView> {
    public GanHuoFragmentPresenter(Context context, IGanHuoView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadGank(String type,int page){
        iView.showProgressBar();
        subscription = GankClient.getGankRetrofitInstance().getGanHuoData(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GanHuoData>() {
                    @Override
                    public void call(GanHuoData ganHuoData) {
                        iView.hideProgressBar();
                        if (ganHuoData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showListView(ganHuoData.results);
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
