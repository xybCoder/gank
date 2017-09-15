package com.xybcoder.gank.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.xybcoder.gank.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by xybcoder on 2016/3/1.
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity{

    protected String TAG = this.getClass().getSimpleName();
    protected T presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initPresenter();
        Log.i(TAG, "onCreate");
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //在Action Bar的最左边，就是Home icon和标题的区域
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        MobclickAgent.onResume(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        MobclickAgent.onResume(this);
        super.onPause();

    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        ButterKnife.bind(this).unbind();
        if(presenter!=null){
            presenter.detachView();
            presenter=null;
        }
        super.onDestroy();

    }

}
