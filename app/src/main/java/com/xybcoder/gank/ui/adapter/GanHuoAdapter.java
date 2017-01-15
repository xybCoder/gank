package com.xybcoder.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xybcoder.gank.R;
import com.xybcoder.gank.model.entity.Gank;
import com.xybcoder.gank.ui.activity.WebActivity;
import com.xybcoder.gank.util.StringStyleUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xybcoder on 16/3/1.
 */
public class GanHuoAdapter extends RecyclerView.Adapter<GanHuoAdapter.GanHuoHolder> {

    List<Gank> gankList;
    Context context;

    public GanHuoAdapter(List<Gank> gankList, Context context) {
        this.gankList = gankList;
        this.context = context;
    }

    @Override
    public GanHuoAdapter.GanHuoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganhuo, parent, false);
        return new GanHuoHolder(view);
    }

    @Override
    public void onBindViewHolder(GanHuoAdapter.GanHuoHolder holder, int position) {
        Gank gank = gankList.get(position);
        holder.tvDescWithWho.setTag(gank);
        holder.tvDescWithWho.setText(StringStyleUtil.getGankStyleStr(gank));
    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    class GanHuoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc_with_who)
        TextView tvDescWithWho;

        public GanHuoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_ganhuo)
        void toWebClick() {
            WebActivity.loadWebViewActivity(context, (Gank) tvDescWithWho.getTag());
        }
    }
}

