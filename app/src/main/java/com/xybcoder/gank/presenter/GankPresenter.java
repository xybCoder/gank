package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.http.GankClient;
import com.xybcoder.gank.model.GankData;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.ui.iView.IGankView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by xybcoder on 2016/3/1.
 */
public class GankPresenter extends BasePresenter<IGankView> {

    public GankPresenter(Context context, IGankView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
        subscription = null;
    }

    public void fetchGankData(int year, int month, int day) {
        iView.showProgressBar();
        subscription = GankClient.getGankRetrofitInstance().getDailyData(year, month, day)
                .map(new Func1<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData gankData) {
                        return addAllResults(gankData.results);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Gank>>() {
                    @Override
                    public void call(List<Gank> gankList) {
                        iView.showGankList(gankList);
                        iView.hideProgressBar();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
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
