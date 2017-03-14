package com.inventec.newsblog.delegate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;

/**
 * Created by Test on 2017/3/2.
 */

public class PicturesFragmentDelegate extends BaseRecyclerViewDelegate implements ILoadingView{

    private static final int PRELOAD_SIZE = 4;
    private StaggeredGridLayoutManager gridViewLayoutManager; //recycleview网格样式布局管理器
    private RecyclerView recyclerView;
    private int previousVisibleItem;
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新控件

    @Override
    void initRecyclerView() {
        gridViewLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        swipeRefreshLayout = get(R.id.base_swipe_refresh_layout);
        recyclerView = get(R.id.base_recyclerview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(gridViewLayoutManager);
    }


    @Override
    boolean setFloatingActionMenuVisible() {
        return true;
    }

    @Override
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final BaseRecyclerAdapter adapter) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = gridViewLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                        >= adapter.getItemCount() - PRELOAD_SIZE;
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        &&!swipeRefreshLayout.isRefreshing() && isBottom) {
                    callBack.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //根据滑动的位置切换FloatingActionMenu的显示与隐藏
                int firstVisibleItem = gridViewLayoutManager.findFirstVisibleItemPositions(new int[2])[0];
                if (firstVisibleItem > previousVisibleItem) {
                    //fabMenu.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_zoom_in));
                    fabMenu.showMenu(false);
                } else if (firstVisibleItem <  previousVisibleItem) {
                    //fabMenu.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_zoom_out));
                    fabMenu.hideMenu(false);
                }
                previousVisibleItem = firstVisibleItem;
            }
        });

    }

    public void showRefreshLayout() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
            //RxSwipeRefreshLayout.refreshing(swipeRefreshLayout).call(true);
        }
    }

    /**
     * FloatingActionButton点击事件的监听接口
     */
    public interface FloatingActionButtonListener{
        void onClick(String id);
    }

    /**
     * 设置悬浮菜单项目的点击事件
     */
    public void setFloatingActionButtonListener(final FloatingActionButtonListener listener){
        for(int i = 0; i < floatingActionButtons.size(); i++){
            String id = "";
            switch (i) {
                case 0 : id = "4004"; break;
                case 1 : id = "4006"; break;
                case 2 : id = "4007"; break;
                case 3 : id = "4010"; break;
                default: id = "4004"; break;
            }
            final String finalId = id;
            floatingActionButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(finalId);
                    hideMenu(true);
                }
            });
        }
    }
}
