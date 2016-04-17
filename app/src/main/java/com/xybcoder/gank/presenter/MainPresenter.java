package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;

import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.http.GankClient;
import com.xybcoder.gank.model.FunnyData;
import com.xybcoder.gank.model.MeiziData;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.ui.activity.AboutActivity;
import com.xybcoder.gank.ui.activity.GanHuoActivity;
import com.xybcoder.gank.ui.activity.ListGirlsActivity;
import com.xybcoder.gank.ui.activity.WebActivity;
import com.xybcoder.gank.ui.iView.IMainView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public void fetchMeiziData(int page) {
        iView.showProgress();
        subscription = Observable.zip(GankClient.getGankRetrofitInstance().getMeiziData(page),
                GankClient.getGankRetrofitInstance().getFunnyData(page),
                new Func2<MeiziData, FunnyData, MeiziData>() {
                    @Override
                    public MeiziData call(MeiziData meiziData, FunnyData funnyData) {
                        return createMeiziDataWith休息视频Desc(meiziData, funnyData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showMeiziList(meiziData.results);
                        }
                        iView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });

    }

    private MeiziData createMeiziDataWith休息视频Desc(MeiziData meiziData,FunnyData funnyData) {
        int size = Math.min(meiziData.results.size(),funnyData.results.size());
        for (int i = 0; i < size; i++) {
            meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，" + funnyData.results.get(i).desc;
            meiziData.results.get(i).who = funnyData.results.get(i).who;
        }
        return meiziData;
    }

    public void toGanHuoActivity(){
        Intent intent = new Intent(context, GanHuoActivity.class);
        context.startActivity(intent);
    }

    public void toAboutActivity(){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public void openGitHubHot(String desc,String url){
        Gank gank=new Gank();
        gank.setDesc(desc);
        gank.setUrl(url);
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(GankConfig.GANK, gank);
        context.startActivity(intent);
    }

    public void toListGirlsActivity(){
        Intent intent = new Intent(context, ListGirlsActivity.class);
        context.startActivity(intent);
    }


}
