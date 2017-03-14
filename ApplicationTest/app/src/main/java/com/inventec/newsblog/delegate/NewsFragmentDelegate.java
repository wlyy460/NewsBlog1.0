package com.inventec.newsblog.delegate;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.newsblog.adapter.NewsListAdapter;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.widget.ListItemDecoration;

/**
 * 新闻页面视图代理类
 * Created by Test on 2017/2/28.
 */

public class NewsFragmentDelegate extends BaseRecyclerViewDelegate {
    /**
     * 用于加载更多的列表布局管理器
     */
    private LinearLayoutManager recycleViewLayoutManager;

    @Override
    void initRecyclerView() {
        //设置分割线
        recyclerView.addItemDecoration(new ListItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recycleViewLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(recycleViewLayoutManager);
    }

    @Override
    boolean setFloatingActionMenuVisible() {
        return false;
    }

    @Override
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack,
                                         final BaseRecyclerAdapter adapter) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                NewsListAdapter newsListAdapter = (NewsListAdapter) adapter;
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == newsListAdapter.getItemCount()&&
                        newsListAdapter.isShowFooter()) {
                    callBack.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = recycleViewLayoutManager.findLastVisibleItemPosition();
            }

        });
    }

}
