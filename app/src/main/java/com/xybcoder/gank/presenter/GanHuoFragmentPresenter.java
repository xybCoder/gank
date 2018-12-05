package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.model.GanHuoData;
import com.xybcoder.gank.network.HostType;
import com.xybcoder.gank.network.RetrofitManager;
import com.xybcoder.gank.ui.iView.IGanHuoView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xybcoder on 2016/3/1.
 */
public class GanHuoFragmentPresenter extends BasePresenter<IGanHuoView> {
    public GanHuoFragmentPresenter(Context context, IGanHuoView iView) {
        super(context, iView);
    }

    public void loadGank(String type, int page) {
        iView.showProgressBar();
        RetrofitManager.getInstance(HostType.GANK_TYPE).getGankService()
                .getGanHuoData(type, page)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GanHuoData>() {
                    @Override
                    public void accept(GanHuoData ganHuoData) {
                        iView.hideProgressBar();
                        if (ganHuoData.results.size() == 0) {
                            iView.showNoMoreData();
                        } else {
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
