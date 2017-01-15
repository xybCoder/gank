package com.xybcoder.gank.presenter;

import android.content.Context;

import com.xybcoder.gank.network.GankClient;
import com.xybcoder.gank.model.MeiziData;
import com.xybcoder.gank.ui.iView.IListGirlsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
        GankClient.getGankRetrofitInstance().getMeiziData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MeiziData>() {
                    @Override
                    public void accept(MeiziData meiziData) {
                        iView.hideProgressBar();
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showListView(meiziData.results);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iView.hideProgressBar();
                        iView.showErrorView();
                    }
                });
    }
}
