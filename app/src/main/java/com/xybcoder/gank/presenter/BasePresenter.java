package com.xybcoder.gank.presenter;

import android.content.Context;


import com.xybcoder.gank.ui.iView.IBaseView;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 基础presenter
 * Created by xybcoder on 2016/3/1.
 */
public class BasePresenter<T extends IBaseView> {

    protected Context context;
    protected T iView;
    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void attachView(){
        if(iView!=null){
            iView.initView();
        }
    }


    public void detachView() {
        if(iView!=null) {
            iView = null;
        }
    }

}
