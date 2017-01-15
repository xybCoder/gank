package com.xybcoder.gank.presenter;

import android.content.Context;


import com.xybcoder.gank.ui.iView.IBaseView;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;


/**
 * 基础presenter
 * Created by xybcoder on 2016/3/1.
 */
public abstract class BasePresenter<T extends IBaseView>  {

    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.initView();
    }

    public abstract void release();
}
