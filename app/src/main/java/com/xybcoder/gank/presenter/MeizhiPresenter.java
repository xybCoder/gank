package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.iView.IMeiZhiView;
import com.xybcoder.gank.util.FileUtil;
import com.xybcoder.gank.util.ShareUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MeizhiPresenter extends BasePresenter<IMeiZhiView> {

    public MeizhiPresenter(Context context, IMeiZhiView iView) {
        super(context, iView);
    }
    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public void saveMeizhiImage(final Bitmap bitmap, final String title) {
        subscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception(context.getString(R.string.girl_reject_your_request)));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        iView.showSaveGirlResult(context.getString(R.string.save_girl_successfully));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showSaveGirlResult(context.getString(R.string.girl_reject_your_request));
                    }
                });
    }

    public void shareGirlImage(final Bitmap bitmap, final String title){
        Observable.create(new Observable.OnSubscribe<Uri>(){

            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception(context.getString(R.string.girl_reject_your_request)));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        ShareUtil.shareImage(context, uri, context.getString(R.string.share_girl_to));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showSaveGirlResult(context.getString(R.string.girl_reject_your_request));
                    }
                });
    }

}
