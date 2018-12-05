package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.model.GankData;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.network.HostType;
import com.xybcoder.gank.network.RetrofitManager;
import com.xybcoder.gank.ui.iView.IGankView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class GankPresenter extends BasePresenter<IGankView> {

    public GankPresenter(Context context, IGankView iView) {
        super(context, iView);
    }
    
    public void fetchGankData(int year, int month, int day) {
        iView.showProgressBar();
        RetrofitManager.getInstance(HostType.GANK_TYPE).getGankService().getDailyData(year, month, day)
                .map(new Function<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> apply(GankData gankData) throws Exception {
                        return addAllResults(gankData.results);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(List<Gank> gankList) {
                        iView.showGankList(gankList);
                        iView.hideProgressBar();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iView.hideProgressBar();
                        iView.showErrorView();
                    }
                });
    }

    private List<Gank> addAllResults(GankData.Result results) {
        List<Gank> mGankList = new ArrayList<>();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.前端List != null) mGankList.addAll(results.前端List);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }


}
