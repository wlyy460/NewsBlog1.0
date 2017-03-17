package com.inventec.newsblog;

import android.app.Application;
import android.content.Context;

import com.inventec.frame.crash.CustomActivityOnCrash;
import com.inventec.newsblog.utils.FileUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.http.RequestQueue;
import com.kymjs.rxvolley.toolbox.Loger;


/**
 * 自定义Application，做一些初始化配置
 * Created by Test on 2017/2/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    //public static String cacheDir = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Loger.setEnable(BuildConfig.DEBUG);
        //LeakCanary检测OOM

        //app crash的提示
        CustomActivityOnCrash.install(this);
        //设置volley网络请求的缓存目录
        RxVolley.setRequestQueue(RequestQueue.newRequestQueue(FileUtil.getCacheDir("RxVolley")));
    }

    public static BaseApplication getInstance(){
        return instance;
    }

    /**
     * 获取ApplicationContext
     * @return context
     */
    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
