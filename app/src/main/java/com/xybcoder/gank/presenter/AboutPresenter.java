package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.iView.IAboutView;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class AboutPresenter extends BasePresenter<IAboutView> {

    public AboutPresenter(Context context, IAboutView iView) {
        super(context, iView);
    }


    public void starInMarket() {
        Uri uri = Uri.parse(String.format(context.getString(R.string.market), context.getPackageName()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(intent);
    }

    @Override
    public void release() {

    }
}
