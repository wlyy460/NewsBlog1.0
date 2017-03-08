package com.inventec.newsblog.utils;

import android.content.Context;
import android.webkit.WebView;

import com.inventec.newsblog.activity.BlogDetailActivity;
import com.inventec.newsblog.activity.BrowserActivity;
import com.inventec.newsblog.activity.MobileBrowserActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 链接分发调度器
 * 特殊链接,特殊处理
 * Created by Test on 2017/2/16.
 */

public class LinkDispatcher {
    /**
     * 分发已知适配手机页面的url,对于未知页面调用#BrowserActivity显示
     */
    public static void dispatchUrl(Context cxt, String url) {
        if ((getActionTitle(url, null)) != null) {
            MobileBrowserActivity.goinActivity(cxt, url, getActionTitle(url, ""));
        } else {
            BrowserActivity.goinActivity(cxt, url);
        }
    }

    /**
     * 对不同的链接调用不同的Activity去展示
     *
     * @param view 当前视图中的webview
     * @param url  链接
     */
    public static void dispatch(WebView view, String url) {
        String currentHost = getHost(view.getUrl());
        String remoteHost = getHost(url);
        if (url.contains("kymjs.com")) {
            BlogDetailActivity.goinActivity(view.getContext(), url, null);
        } else {
            if (currentHost.equals(remoteHost)) {
                view.loadUrl(url);
            } else {
                dispatchUrl(view.getContext(), url);
            }
        }
    }

    /**
     * 获取一个url的域名
     * @param url
     * @return
     */
    private static String getHost(String url) {
        Pattern pattern = Pattern.compile("(http|https|ftp|svn)://[a-zA-Z0-9].?]");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find())
            return matcher.group();
        else {
            return "";
        }
    }

    /**
     * 设置ActionBar的title
     * 特殊站点优待
     *
     * @param url      站点的url
     * @param defTitle 如果不在优待范围内
     */
    public static String getActionTitle(String url, String defTitle) {
        String title;
        //那些移动页面已经适配的网站
        if (url.contains("github.io")) {
            title = "GitHub博客";
        } else if (url.contains("github.com")) {
            title = "GitHub项目";
        } else if (url.contains("kymjs.com")) {
            title = "开源实验室";
        } else if (url.contains("droidyue")) {
            title = "技术小黑屋";
        } else if (url.contains("androidweekly")) {
            title = "Android开发技术周报";
        } else if (url.contains("mp.weixin.qq.com")) {
            title = "微信公众号";
        } else {
            title = defTitle;
        }
        return title;
    }
}