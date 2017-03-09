package com.inventec.newsblog.server;

import com.inventec.newsblog.inter.BizInterface;
import com.inventec.newsblog.model.entity.ShowApiResponse;
import com.inventec.newsblog.model.news.ShowApiNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 易源api接口
 * Created by Test on 2017/3/8.
 */

public interface ShowAPI {

    /**
     * 新闻列表
     * @param cacheControl 缓存控制
     * @param appId 易源appid
     * @param key 易源密钥
     * @param page 页数
     * @param channelId 频道id
     * @param channelName 名称
     * @return
     */
    @GET(BizInterface.NEWS_URL)
    @Headers("apikey: " + BizInterface.API_KEY)
    Call<ShowApiResponse<ShowApiNews>> getNewsList(@Header("Cache-Control") String cacheControl,
                                                   @Query("showapi_appid") String appId,
                                                   @Query("showapi_sign") String key,
                                                   @Query("page") int page,
                                                   @Query("channelId") String channelId,//新闻频道id，必须精确匹配
                                                   @Query("channelName") String channelName);//新闻频道名称，可模糊匹配
}
