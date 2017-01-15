package com.xybcoder.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.xybcoder.gank.R;
import com.xybcoder.gank.ui.iView.IMeiZhiView;
import com.xybcoder.gank.util.FileUtil;
import com.xybcoder.gank.util.ShareUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MeizhiPresenter extends BasePresenter<IMeiZhiView> {

    public MeizhiPresenter(Context context, IMeiZhiView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }

    public void saveMeizhiImage(final Bitmap bitmap, final String title) {
        Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(ObservableEmitter<Uri> e) throws Exception {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    e.onError(new Exception(context.getString(R.string.girl_reject_your_request)));
                } else {
                    e.onNext(uri);
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        iView.showSaveGirlResult(context.getString(R.string.save_girl_successfully));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iView.showSaveGirlResult(context.getString(R.string.girl_reject_your_request));
                    }
                });
    }

    public void shareGirlImage(final Bitmap bitmap, final String title) {
        Observable.create(new ObservableOnSubscribe<Uri>() {

            @Override
            public void subscribe(ObservableEmitter<Uri> e) throws Exception {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    e.onError(new Exception(context.getString(R.string.girl_reject_your_request)));
                } else {
                    e.onNext(uri);
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uri) {
                        ShareUtil.shareImage(context, uri, context.getString(R.string.share_girl_to));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iView.showSaveGirlResult(context.getString(R.string.girl_reject_your_request));
                    }
                });
    }

}
