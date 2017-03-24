package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.R;
import com.inventec.newsblog.adapter.NewsListAdapter;
import com.inventec.newsblog.delegate.NewsFragmentDelegate;
import com.inventec.newsblog.inter.INewsData;
import com.inventec.newsblog.inter.NetLoadImpl;
import com.inventec.newsblog.inter.OnNetRequestListener;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.model.news.NewsBean;
import com.inventec.newsblog.utils.LogUtil;
import com.inventec.newsblog.utils.NetworkUtil;
import com.inventec.newsblog.utils.SnackbarUtil;
import com.inventec.newsblog.widget.ProgressLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 新闻列表页fragment
 * Created by Test on 2017/3/2.
 */

public class NewsFragment extends FragmentPresenter<NewsFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack,
        BaseRecyclerAdapter.OnItemClickListener {

    private static final String TAG = NewsFragment.class.getSimpleName();
    private int pageNum = 1;
    private NewsListAdapter newsAdapter;
    protected RecyclerView recyclerView;
    private INewsData iNewsData;

    //新闻数据列表
    private List<NewsBean> newsData = new ArrayList<NewsBean>();
    private  boolean isFirstLoading = true;//是否首次加载

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
        doLoadNewsList(true);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        recyclerView = viewDelegate.get(R.id.base_recyclerview);
        iNewsData = new NetLoadImpl();
        newsAdapter = new NewsListAdapter(recyclerView, newsData);
        viewDelegate.setListAdapter(newsAdapter);
        bindEven();
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        //注册加载更多
        viewDelegate.registerLoadMoreCallBack(this, newsAdapter);
        newsAdapter.setOnItemClickListener(this);
    }

    private void bindEven() {
        ProgressLayout progressLayout =  viewDelegate.get(R.id.progress_layout);
        progressLayout.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadNewsList(true);
            }
        });
    }

    @Override
    public void refresh() {
        doLoadNewsList(true);
    }

    @Override
    public void loadMore() {
        doLoadNewsList(false);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    /**
     * 从网络加载数据列表
     * @param isRefresh 是否刷新
     */
    private void doLoadNewsList(final boolean isRefresh) {
        if(isRefresh){
            pageNum = 1;
        }else {
            pageNum++;
        }
        iNewsData.doRequestNews(pageNum, INewsData.CHANNEL_ID, INewsData.CHANNEL_NAME,
                new OnNetRequestListener<List<NewsBean>>() {
            @Override
            public void onStart() {
                //首次加载数据时显示正在加载提示
                if (isFirstLoading) {
                    isFirstLoading = false;
                    viewDelegate.showLoading();
                }
            }
            @Override
            public void onFinish() {
                if (viewDelegate != null) {
                    viewDelegate.showContent();
                }
            }
            @Override
            public void onSuccess(List<NewsBean> list) {
                if (list != null && !list.isEmpty()) {
                    if (isRefresh) {
                        if (newsData != null && !newsData.isEmpty()) {
                            //去重
                            for (NewsBean data : list) {
                                if (!newsData.contains(data)) {
                                    newsData.add(data);
                                }
                            }
                            //刷新获取的新闻列表按发布日期降序排序
                            Collections.sort(newsData);
                            Collections.reverse(newsData);
                        }else {
                            newsData.addAll(list);
                        }
                    } else {
                        newsData.addAll(list);
                    }
                    newsAdapter.notifyDataSetChanged();
                }else if (newsData == null || newsData.isEmpty()
                        || newsAdapter == null || newsAdapter.getItemCount() < 1) {
                    viewDelegate.showError(R.string.load_error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtil.d(TAG ,"====网络请求异常," +  t.getMessage());
                if (viewDelegate != null && newsAdapter != null) {
                    //有可能界面已经关闭网络请求仍然返回
                    if (newsAdapter.getItemCount() > 1) {
                        viewDelegate.showContent();
                    } else {
                        viewDelegate.showError(R.string.load_error);
                    }
                    if (!NetworkUtil.checkNetworkConnected(BaseApplication.getContext())){
                        SnackbarUtil.showSnackbar(viewDelegate.getRootView(),
                                R.string.check_network_connet_setting,
                                Snackbar.LENGTH_LONG);
                    }
                }
            }
        });
    }
}
