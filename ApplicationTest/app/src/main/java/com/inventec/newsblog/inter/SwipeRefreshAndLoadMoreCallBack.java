package com.inventec.newsblog.inter;

/**
 * 下拉刷新与加载更多接口，用于presenter与view解耦
 * Created by Test on 2017/2/23.
 */

public interface SwipeRefreshAndLoadMoreCallBack {
    /**
     * 下拉刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();

}
