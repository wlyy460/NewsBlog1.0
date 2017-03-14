package com.inventec.newsblog.utils;

import android.util.Log;

/**
 * 日志工具类
 * Created by Administrator on 2017/3/11.
 */

public class Loger {

    private static boolean sEnable = true;

    public static void setEnable(boolean enable) {
        sEnable = enable;
    }

    public static void debug(String tag,String msg) {
        if (sEnable) {
            Log.d(tag, "" + msg);
        }
    }
}
