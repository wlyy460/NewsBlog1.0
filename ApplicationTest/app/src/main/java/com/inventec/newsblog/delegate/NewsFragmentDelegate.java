package com.inventec.newsblog.delegate;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.adapter.NewsListAdapter;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.widget.ListItemDecoration;

/**
 * 新闻页面视图代理类
 * Created by Test on 2017/2/28.
 */

public class NewsFragmentDelegate extends BaseRecyclerViewDelegate implements ILoadingView {
    /**
     * 用于加载更多的列表布局管理器
     */
    private LinearLayoutManager recycleViewLayoutManager;
    private int previousVisibleItem;

    @Override
    void initRecyclerView() {
        //设置分割线
        recyclerView.addItemDecoration(new ListItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recycleViewLayoutManager = new LinearLayoutManager(BaseApplication.getContext());
        recyclerView.setLayoutManager(recycleViewLayoutManager);
       /* fabMenu.hideMenu(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabMenu.show(true);
                fabMenu.setShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
                fabMenu.setHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));
            }
        }, 300);*/
    }

    @Override
    boolean setFloatingActionMenuVisible() {
        return false;
    }

    @Override
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final BaseRecyclerAdapter adapter) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                NewsListAdapter newsListAdapter = null;
                if (adapter instanceof NewsListAdapter){
                    newsListAdapter = (NewsListAdapter) adapter;
                }else {
                    return;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == adapter.getItemCount()&&
                        newsListAdapter.isShowFooter()) {
                    callBack.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*int firstVisibleItem = recycleViewLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem > previousVisibleItem) {
                    fabMenu.hideMenu(true);
                } else if (firstVisibleItem < previousVisibleItem) {
                    fabMenu.showMenu(true);
                }
                previousVisibleItem = firstVisibleItem;*/
                lastVisibleItem = recycleViewLayoutManager.findLastVisibleItemPosition();
            }

        });
    }

}
