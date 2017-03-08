package com.inventec.newsblog.delegate;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;

/**
 * Created by Test on 2017/3/2.
 */

public class PicturesFragmentDelegate extends BaseRecyclerViewDelegate implements ILoadingView{

    private static final int PRELOAD_SIZE = 6;
    private StaggeredGridLayoutManager gridViewLayoutManager; //recycleview网格样式布局管理器

    @Override
    void initRecyclerView() {
        gridViewLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridViewLayoutManager);
    }

    @Override
    boolean setFloatingActionMenuVisible() {
        return true;
    }

    @Override
    public void registerLoadMoreCallBack(SwipeRefreshAndLoadMoreCallBack callBack, BaseRecyclerAdapter adapter) {

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
     * 设置悬浮菜单项目点击事件
     */
    public void setFloatingActionButtonListener(final FloatingActionButtonListener listener){
        for(int i = 0; i < floatingActionButtons.size(); i++){
            String id = "";
            switch (i) {
                case 0 : id = "4001"; break;
                case 1 : id = "4002"; break;
                case 2 : id = "4003"; break;
                case 3 : id = "4004"; break;
                default: id = "4001"; break;
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
