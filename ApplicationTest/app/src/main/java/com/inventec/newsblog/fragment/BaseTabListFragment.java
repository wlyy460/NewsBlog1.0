package com.inventec.newsblog.fragment;

import android.support.v7.widget.RecyclerView;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.BaseRecyclerViewDelegate;
import com.inventec.newsblog.inter.IRequestVo;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 *tab切换页的列表界面基类
 * Created by Test on 2017/3/6.
 */

public abstract class BaseTabListFragment<T> extends FragmentPresenter<BaseRecyclerViewDelegate>
        implements IRequestVo, SwipeRefreshAndLoadMoreCallBack, BaseRecyclerAdapter.OnItemClickListener {
    protected RecyclerView recyclerView;
    protected List<T> datas = new ArrayList<T>();
    protected BaseRecyclerAdapter<T> adapter;
    protected abstract BaseRecyclerAdapter<T> getAdapter();

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        recyclerView = viewDelegate.get(R.id.base_recyclerview);
        adapter = getAdapter();
        viewDelegate.setListAdapter(adapter);
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        //注册加载更多
        viewDelegate.registerLoadMoreCallBack(this, adapter);
        adapter.setOnItemClickListener(this);
        doRequest();
    }

    @Override
    public void refresh() {
        doRequest();
    }

    @Override
    public void loadMore() {
        doRequest();
    }
}
