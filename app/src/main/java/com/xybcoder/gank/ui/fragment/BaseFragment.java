package com.xybcoder.gank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xybcoder.gank.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by xybcoder on 2016/3/1.
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {

    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(getActivity()).unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // App.getRefWatcher(getContext()).watch(this);
    }
}
