package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.network.GankClient;
import com.xybcoder.gank.model.GanHuoData;
import com.xybcoder.gank.ui.iView.IGanHuoView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xybcoder on 2016/3/1.
 */
public class GanHuoFragmentPresenter extends BasePresenter<IGanHuoView> {
    public GanHuoFragmentPresenter(Context context, IGanHuoView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }

    public void loadGank(String type,int page){
        iView.showProgressBar();
         GankClient.getGankRetrofitInstance()
                .getGanHuoData(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GanHuoData>() {
                    @Override
                    public void accept(GanHuoData ganHuoData) {
                        iView.hideProgressBar();
                        if (ganHuoData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showListView(ganHuoData.results);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iView.hideProgressBar();
                        iView.showErrorView();
                    }
                });
    }
}
