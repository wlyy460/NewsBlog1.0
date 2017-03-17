package com.inventec.newsblog.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.util.List;

/**
 *
 * 应用程序常用工具类（包含粘贴复制，启动app,判断app是否存在等功能）
 * Created by Test on 2017/2/16.
 */

public class CommonUtil {

    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            if(context.getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)!= null);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void openOtherApp(Context context, String packageName, String className) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        context.startActivity(intent);
    }

    public static void openOtherApp(Context context, String packageName) {
        PackageInfo pi;
        PackageManager pm = context.getPackageManager();
        try {
            pi = pm.getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
//               String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(Context context, String content) {
    // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //cmb.setText(content.trim());
        cmb.setPrimaryClip(ClipData.newPlainText(null, content.trim()));
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
    // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cmb.getPrimaryClip().getItemCount(); i++) {
            String text = cmb.getPrimaryClip().getItemAt(i).getText().toString().trim();
            sb.append(text);
        }
        return sb.toString();
    }

    public static int toInt(String num, int defualt) {
        try {
            return Integer.valueOf(num);
        } catch (NumberFormatException e) {
            return defualt;
        }
    }

    public static long toLong(String num, long defualt) {
        try {
            return Long.valueOf(num);
        } catch (NumberFormatException e) {
            return defualt;
        }
    }

    public static void shareUrl(Context context, String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "来自开源实验室的分享:" + url);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "发送到:"));
    }
}
