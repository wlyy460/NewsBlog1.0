package com.inventec.frame.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.inventec.frame.adapter.widget.FooterView;

import java.util.Collection;

/**
 * 让RecyclerView滚动到底部具有自动加载更多的功能
 * Created by Test on 2017/2/13.
 */

public abstract class BasePullUpRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    public static final int TYPE_FOOTER = 0;
    public static final int TYPE_ITEM = 1;

    public static final int STATE_INVISIBLE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;

    protected int state = STATE_LOADING;
    protected FooterView footerView;
    private OnPullUpListener pullUpListener;

    public BasePullUpRecyclerAdapter(RecyclerView v, Collection<T> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
        v.addOnScrollListener(new BasePullUpScrollListener());
        footerView = new FooterView(cxt);
    }

    /**
     * 获取底部的FooterView
     * @return
     */
    public FooterView getFooterView() {
        return this.footerView;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
        refreshFooterView();
    }

    /**
     * 滚动到底部时的监听器
     */
    public interface OnPullUpListener {
        void onBottom(int state);
    }

    /**
     * 设置滚动到底部时的监听器
     *
     * @param l
     */
    public void setPullUpListener(OnPullUpListener l) {
        this.pullUpListener = l;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }
    
    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return super.onCreateViewHolder(parent, viewType);
        }else if (viewType == TYPE_FOOTER){
            refreshFooterView();
            return new RecyclerHolder(footerView);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        if (position == getItemCount() - 1) {
            //因为已经在footerview写死了，所以这里就不用再去设置
            //没有数据的时候也不显示footer
            if (position == 0) {
                getFooterView().setVisibility(View.GONE);
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    private void refreshFooterView() {
        switch (state){
            case STATE_INVISIBLE:
                footerView.setInVisibleState();
                break;
            case STATE_LOADING:
                footerView.setLoadingState();
                break;
            case STATE_NO_MORE:
                footerView.setNoMoreState();
                break;
            case STATE_NO_DATA:
                footerView.setNoDataState();
                break;
        }
    }

    public class BasePullUpScrollListener extends RecyclerView.OnScrollListener {
        public static final int LINEAR = 0;
        public static final int GRID = 1;
        public static final int STAGGERED_GRID = 2;//交错网格

        //标识RecyclerView的LayoutManager是哪种
        protected int layoutManagerType;
        // 瀑布流的最后一个的位置
        protected int[] lastPositions;
        // 最后一个的位置
        protected int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                    getItemCount() && pullUpListener != null) {
                pullUpListener.onBottom(state);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LINEAR;
            }else if (layoutManager instanceof GridLayoutManager){
                layoutManagerType = GRID;
            }else if (layoutManager instanceof StaggeredGridLayoutManager){
                layoutManagerType = STAGGERED_GRID;
            }else{
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are " +
                                "LinearLayoutManager, GridLayoutManager and " +
                                "StaggeredGridLayoutManager");
            }
            switch (layoutManagerType){
                case LINEAR:
                    lastVisibleItem = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItem = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItem = findMax(lastPositions);
                    break;
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
