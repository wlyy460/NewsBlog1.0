package com.inventec.newsblog.inter;

import com.inventec.newsblog.model.news.NewsBean;

import java.util.List;

/**
 * 新闻数据接口
 * Created by Test on 2017/3/1.
 */

public interface INews {

    public static final String CHANNEL_ID = "5572a109b3cdc86cf39001db";//频道id 来自api指定
    public static final String CHANNEL_NAME = "国内最新";//频道名称 来自api指定
    /**
     * 加载新闻列表
     * @param page 页数
     * @param channelId 频道id 来自api
     * @param channelName 频道名称
     */
    void netLoadNewsList(int page, String channelId, String channelName, OnNetRequestListener<List<NewsBean>> listListener);
}
