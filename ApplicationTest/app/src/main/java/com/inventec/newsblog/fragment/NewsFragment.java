package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.adapter.NewsListAdapter;
import com.inventec.newsblog.delegate.NewsFragmentDelegate;
import com.inventec.newsblog.inter.INews;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.model.news.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表页fragment
 * Created by Test on 2017/3/2.
 */

public class NewsFragment extends FragmentPresenter<NewsFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack,
        BaseRecyclerAdapter.OnItemClickListener {

    private INews iNews;
    private int pageNum = 1;
    private NewsListAdapter newsAdapter;
    protected RecyclerView recyclerView;

    //新闻数据列表
    private List<NewsBean> newsList = new ArrayList<>();

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected Class<NewsFragmentDelegate> getDelegateClass() {
        return NewsFragmentDelegate.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        recyclerView = viewDelegate.get(R.id.base_recyclerview);
        newsAdapter = new NewsListAdapter(recyclerView, newsList, R.layout.item_news);
        viewDelegate.setListAdapter(newsAdapter);
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        //注册加载更多
        viewDelegate.registerLoadMoreCallBack(this, newsAdapter);
        newsAdapter.setOnItemClickListener(this);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }
}
