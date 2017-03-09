package com.inventec.newsblog;

import android.app.Application;
import android.content.Context;

import com.inventec.frame.crash.CustomActivityOnCrash;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.http.RequestQueue;
import com.kymjs.rxvolley.toolbox.Loger;

import java.io.File;


/**
 * 自定义Application，做一些初始化配置
 * Created by Test on 2017/2/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    public static String cacheDir = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Loger.setEnable(BuildConfig.DEBUG);
        //LeakCanary检测OOM
        CustomActivityOnCrash.install(this);
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
        //设置volley网络请求的缓存目录
        RxVolley.setRequestQueue(RequestQueue.newRequestQueue(new File(cacheDir)));
    }

    private boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
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
