package com.xybcoder.gank.ui.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ShareElement;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.ui.activity.GankActivity;
import com.xybcoder.gank.ui.activity.MeiZhiActivity;
import com.xybcoder.gank.ui.widget.RatioImageView;
import com.xybcoder.gank.util.DateUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziHolder> {

    List<Meizi> list;
    Context context;
    int lastPosition = 0;


    public MeiziAdapter(Context context, List<Meizi> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MeiziHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
        return new MeiziHolder(view);
    }

    @Override
    public void onBindViewHolder(MeiziHolder holder, int position) {
        Meizi meizi = list.get(position);
        holder.card.setTag(meizi);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        holder.ivMeizi.setBackgroundColor(Color.argb(204, red, green, blue));
        if(meizi!=null) {
            Glide.with(context)
                    .load(meizi.url)
                    .crossFade()
                    .into(holder.ivMeizi);

            holder.tvWho.setText(meizi.who);
            holder.tvAvatar.setText(TextUtils.isEmpty(meizi.who)?"":meizi.who.substring(0, 1).toUpperCase());
            holder.tvDesc.setText(meizi.desc);
            if(meizi.publishedAt!=null) {
                holder.tvTime.setText(DateUtil.toDateTimeStr(meizi.publishedAt));
            }
            holder.tvAvatar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private void showItemAnimation(MeiziHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }

    class MeiziHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_meizi)
        RatioImageView ivMeizi;
        @BindView(R.id.tv_who)
        TextView tvWho;
        @BindView(R.id.tv_avatar)
        TextView tvAvatar;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_time)
        TextView tvTime;

        @OnClick(R.id.iv_meizi)
        void meiziClick() {
            ShareElement.shareDrawable = ivMeizi.getDrawable();
            Intent intent = new Intent(context, MeiZhiActivity.class);
            intent.putExtra(GankConfig.MEIZI, (Serializable) card.getTag());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, ivMeizi, GankConfig.TRANSLATE_GIRL_VIEW);
            ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
        }

        @OnClick(R.id.rl_gank)
        void gankClick() {
            ShareElement.shareDrawable = ivMeizi.getDrawable();
            Intent intent = new Intent(context, GankActivity.class);
            intent.putExtra(GankConfig.MEIZI, (Serializable) card.getTag());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, ivMeizi, GankConfig.TRANSLATE_GIRL_VIEW);
            ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
        }

        View card;

        public MeiziHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
            ivMeizi.setOriginalSize(300,150);
        }

    }
}
