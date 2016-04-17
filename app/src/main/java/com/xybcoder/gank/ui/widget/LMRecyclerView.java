package com.xybcoder.gank.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 *
 * 添加加载更多功能
 * Created by xybcoder on 2016/3/1.
 */
public class LMRecyclerView extends RecyclerView {

    private boolean isScrollingToBottom = true;
    private FloatingActionButton floatingActionButton;
    private LoadMoreListener listener;

    public LMRecyclerView(Context context) {
        super(context);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void applyFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
    }


    @Override
    public void onScrolled(int dx, int dy) {
        isScrollingToBottom = dy > 0;
        if (floatingActionButton != null) {
            if (isScrollingToBottom) {
                if (floatingActionButton.isShown())
                    floatingActionButton.hide();
            } else {
                if (!floatingActionButton.isShown())
                    floatingActionButton.show();
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
       /* LinearLayoutManager mLayoutManager = (LinearLayoutManager) getLayoutManager();
        int lastVisibleItemPosition = 0;
        int totalItemCount = 0;
        if (mLayoutManager instanceof LinearLayoutManager) {
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItemPosition == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }
            }

        } else if (mLayoutManager instanceof GridLayoutManager) {
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItemPosition == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }
            }
        }
*/
       LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();
            if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                if (listener != null)
                    listener.loadMore();
            }
        }
    }

    public interface LoadMoreListener {
        void loadMore();
    }
}
