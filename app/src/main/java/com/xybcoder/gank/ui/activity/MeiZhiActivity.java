package com.xybcoder.gank.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ShareElement;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.presenter.MeizhiPresenter;
import com.xybcoder.gank.ui.iView.IMeiZhiView;
import com.xybcoder.gank.util.DateUtil;
import com.xybcoder.gank.util.FileUtil;
import com.xybcoder.gank.util.TipUtil;
import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MeiZhiActivity extends ToolBarActivity<MeizhiPresenter> implements IMeiZhiView {

    Meizi meizi;
    PhotoViewAttacher attacher;
    MeizhiPresenter presenter;
    Bitmap girl;

    @BindView(R.id.iv_meizhi)
    ImageView ivMeizhi;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizhi;
    }


    @Override
    protected void initPresenter() {
        presenter = new MeizhiPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        appBar.setAlpha(0.6f);
        getIntentData();
        initMeizhiView();
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(GankConfig.MEIZI);
    }

    private void initMeizhiView() {
        setTitle(DateUtil.toDateTimeStr(meizi.publishedAt));
        ivMeizhi.setImageDrawable(ShareElement.shareDrawable);
        ViewCompat.setTransitionName(ivMeizhi, GankConfig.TRANSLATE_GIRL_VIEW);
        attacher = new PhotoViewAttacher(ivMeizhi);
        Glide.with(this).load(meizi.url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivMeizhi.setImageBitmap(resource);
                attacher.update();
                girl = resource;
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ivMeizhi.setImageDrawable(errorDrawable);
            }
        });
        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {
                hideOrShowToolBar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!FileUtil.isSDCardEnable() || girl == null) {
                    TipUtil.showSnackTip(ivMeizhi, getString(R.string.girl_reject_your_request));
                } else {
                    presenter.saveMeizhiImage(girl, DateUtil.toDateString(meizi.publishedAt).toString());
                }
                break;
            case R.id.action_share:
                presenter.shareGirlImage(girl, DateUtil.toDateString(meizi.publishedAt).toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showSaveGirlResult(String result) {
        TipUtil.showSnackTip(ivMeizhi, result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareElement.shareDrawable = null;
        presenter.release();
        attacher.cleanup();
    }


}

