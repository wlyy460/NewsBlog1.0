package com.inventec.newsblog.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * app的包信息相关工具类
 * Created by Test on 2017/3/10.
 */

public class AppPkgInfoUtil {

    public static int buildVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getPackgeName(Context context) {
        String packgeName = context.getApplicationContext().getPackageName();
        return packgeName;
    }
}
