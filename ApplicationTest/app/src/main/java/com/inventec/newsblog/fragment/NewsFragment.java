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
import com.inventec.newsblog.inter.INewsData;
import com.inventec.newsblog.inter.NetLoadImpl;
import com.inventec.newsblog.inter.OnNetRequestListener;
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

    private int pageNum = 1;
    private NewsListAdapter newsAdapter;
    protected RecyclerView recyclerView;
    private INewsData newsDataInterface;

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
        newsDataInterface = new NetLoadImpl();
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

    /**
     * 从网络加载数据列表
     * @param isRefresh 是否刷新
     */
    private void doLoadNewsList(final boolean isRefresh) {
//        viewDelegate.showLoading();
        if(isRefresh){
            pageNum = 1;
        }else {
            pageNum++;
        }
        newsDataInterface.doRequestNews(pageNum, INewsData.CHANNEL_ID, INewsData.CHANNEL_NAME, new OnNetRequestListener<List<NewsBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(List<NewsBean> list) {
                viewDelegate.showContent();
                if(isRefresh) {
                    if(!newsList.isEmpty()){
                        newsList.clear();
                    }
                }
                newsList.addAll(list);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                viewDelegate.showError(R.string.load_error, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doLoadNewsList(true);
                    }
                });
            }
        });
    }
}
