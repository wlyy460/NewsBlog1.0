package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inventec.frame.adapter.BasePullUpRecyclerAdapter;
import com.inventec.frame.adapter.RecyclerHolder;
import com.inventec.newsblog.R;
import com.inventec.newsblog.activity.XituDetailActivity;
import com.inventec.newsblog.model.XituBlog;
import com.inventec.newsblog.model.XituBlogList;
import com.inventec.newsblog.utils.Api;
import com.inventec.newsblog.utils.XmlUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 稀土的数据rss列表界面
 * Created by Test on 2017/2/14.
 */

public class XituListFragment extends MainListFragment<XituBlog> {
    private Subscription cacheSubscript;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cacheSubscript = Observable.just(RxVolley.getCache(Api.XITU_BLOG_LIST))
                .filter(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] bytes) {
                        return bytes != null && bytes.length != 0;
                    }
                })
                .map(new Func1<byte[], ArrayList<XituBlog>>() {
                    @Override
                    public ArrayList<XituBlog> call(byte[] bytes) {
                        return parserInAsync(bytes);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<XituBlog>>() {
                    @Override
                    public void call(ArrayList<XituBlog> xituBlogs) {
                        datas = xituBlogs;
                        adapter.refresh(datas);
                        viewDelegate.mEmptyLayout.dismiss();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected BasePullUpRecyclerAdapter<XituBlog> getAdapter() {
        return new BasePullUpRecyclerAdapter<XituBlog>(recyclerView, datas, R.layout.item_blog) {
            @Override
            public void convert(RecyclerHolder holder, XituBlog item, int position) {
                holder.setText(R.id.item_blog_tv_title, item.getTitle());
                holder.setText(R.id.item_blog_tv_description, item.getDescription());
                holder.setText(R.id.item_blog_tv_date, item.getPubDate());
                holder.setText(R.id.item_blog_tv_author, item.getAuthor());
            }
        };
    }

    @Override
    protected ArrayList parserInAsync(byte[] t) {
        ArrayList<XituBlog> datas = XmlUtil.toBean(XituBlogList.class, t).getChannel()
                .getItemArray();
        for (XituBlog data : datas) {
            data.format();
        }
        return datas;
    }

    @Override
    public void doRequest() {
        new RxVolley.Builder().url(Api.XITU_BLOG_LIST)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(10)
                .callback(callBack)
                .doTask();
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        XituBlog blog = (XituBlog) data;
        XituDetailActivity.goinActivity(getActivity(), blog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cacheSubscript != null && cacheSubscript.isUnsubscribed()) {
            cacheSubscript.unsubscribe();
        }
    }
}
