package com.inventec.newsblog.service;

import android.support.annotation.NonNull;

import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.inter.BizInterface;
import com.inventec.newsblog.utils.FileUtil;
import com.inventec.newsblog.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求客户端引擎类
 * Created by Administrator on 2017/3/7.
 */

public class RetrofitClient {
    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;
    private volatile static RetrofitClient instance = null;
    private volatile static BaiduAPI baiduAPI = null;
    private volatile static ShowAPI showAPI = null;

    private RetrofitClient(){
    }

    public static RetrofitClient getInstance(){
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     * 创建基于易源API的调用接口
     * @return
     */
    public static ShowAPI createShowAPI() {
        if (showAPI == null) {
            synchronized (RetrofitClient.class) {
                if (showAPI == null) {
                    initOkHttpClient();
                    showAPI = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.SHOW_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(ShowAPI.class);
                }
            }
        }
        return showAPI;
    }

    /**
     * 创建基于百度API的调用接口
     * @return
     */
    public static BaiduAPI createBaiduAPI() {
        if (baiduAPI == null) {
            synchronized (RetrofitClient.class) {
                if (baiduAPI == null) {
                    initOkHttpClient();
                    baiduAPI = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.BAIDU_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(BaiduAPI.class);
                }
            }
        }
        return baiduAPI;
    }

    /**
     * 配置OkHttpClient
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null){
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            // 指定缓存路径
            File cacheFile = FileUtil.getCacheDir("HttpCache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetworkUtil.checkNetworkConnected(BaseApplication.getContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE).build();
                    }
                    Response originalResponse = chain.proceed(request);
                    if (NetworkUtil.checkNetworkConnected(BaseApplication.getContext())) {
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma").build();
                    } else {
                        return originalResponse.newBuilder().header("Cache-Control",
                                "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };
            //okhttp 3
            mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                    .addNetworkInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(rewriteCacheControlInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS).build();
        }
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    public static String getCacheControl() {
        return NetworkUtil.checkNetworkConnected(BaseApplication.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }
}
