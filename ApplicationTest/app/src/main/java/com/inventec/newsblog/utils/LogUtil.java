package com.inventec.newsblog.utils;

import android.util.Log;

/**
 * 打印Log的工具类
 * Created by Administrator on 2017/3/11.
 */

public class LogUtil {

    private static boolean sEnable = true;
    //规定每段显示的长度(Android系统的单条日志打印长度是有限的，长度是固定的4*1024个字符长度)
    private static int LOG_MAXLENGTH = 2000;

    public static void setEnable(boolean enable) {
        sEnable = enable;
    }

    public static void d(String TAG, String msg) {
        if (sEnable) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.d(TAG + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(TAG, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
