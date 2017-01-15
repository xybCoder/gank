package com.xybcoder.gank.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xybcoder.gank.GankConfig;
import com.xybcoder.gank.R;
import com.xybcoder.gank.ShareElement;
import com.xybcoder.gank.model.entity.Meizi;
import com.xybcoder.gank.ui.activity.MeiZhiActivity;
import com.xybcoder.gank.ui.widget.RatioImageView;
import com.xybcoder.gank.util.DateUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2016/4/16.
 */
public class ListGirlsAdapter extends RecyclerView.Adapter<ListGirlsAdapter.ListGirlsHolder> {

    List<Meizi> list;
    Context context;

    public ListGirlsAdapter(Context context, List<Meizi> list) {
        this.context = context;
        this.list = list;


    }


    @Override
    public ListGirlsAdapter.ListGirlsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girl, parent, false);
        return new ListGirlsHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListGirlsAdapter.ListGirlsHolder holder, int position) {

        Meizi meizi = list.get(position);
        holder.card.setTag(meizi);
        Glide.with(context)
                .load(meizi.url)
                .centerCrop()
                .into(holder.mIvIndexPhoto);
        holder.mTvTime.setText(DateUtil.toDateString(meizi.publishedAt));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListGirlsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_index_photo)
        RatioImageView mIvIndexPhoto;
        @BindView(R.id.tv_time)
        TextView mTvTime;


        @OnClick(R.id.iv_index_photo)
        void meiziClick() {
            ShareElement.shareDrawable = mIvIndexPhoto.getDrawable();
            Intent intent = new Intent(context, MeiZhiActivity.class);
            intent.putExtra(GankConfig.MEIZI, (Serializable) card.getTag());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, mIvIndexPhoto, GankConfig.TRANSLATE_GIRL_VIEW);
            ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
        }


        View card;

        public ListGirlsHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
            mIvIndexPhoto.setOriginalSize(50, 50);
        }
    }
}
