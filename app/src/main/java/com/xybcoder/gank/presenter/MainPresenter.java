package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;

import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.network.GankClient;
import com.xybcoder.gank.model.FunnyData;
import com.xybcoder.gank.model.MeiziData;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.ui.activity.AboutActivity;
import com.xybcoder.gank.ui.activity.GanHuoActivity;
import com.xybcoder.gank.ui.activity.ListGirlsActivity;
import com.xybcoder.gank.ui.activity.WebActivity;
import com.xybcoder.gank.ui.iView.IMainView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xybcoder on 2016/3/1.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    public void fetchMeiziData(int page) {
        iView.showProgress();
         Observable.zip(GankClient.getGankRetrofitInstance().getMeiziData(page),
                 GankClient.getGankRetrofitInstance().getFunnyData(page),
                 new BiFunction<MeiziData, FunnyData, MeiziData>() {
                    @Override
                    public MeiziData apply(MeiziData meiziData, FunnyData funnyData) {
                        return createMeiziDataWith休息视频Desc(meiziData, funnyData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MeiziData>() {
                    @Override
                    public void accept(MeiziData meiziData) {
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showMeiziList(meiziData.results);
                        }
                        iView.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
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
