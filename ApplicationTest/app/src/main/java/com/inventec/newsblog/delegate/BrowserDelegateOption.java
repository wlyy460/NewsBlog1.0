package com.inventec.newsblog.delegate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.ZoomButtonsController;

import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.R;
import com.inventec.newsblog.inter.OnWebViewImageListener;
import com.inventec.newsblog.utils.LinkDispatcher;
import com.inventec.newsblog.widget.EmptyLayout;
import com.kymjs.core.bitmap.toolbox.DensityUtils;
import com.kymjs.gallery.KJGalleryActivity;

/**
 *
 * 浏览器视图的参数设置类
 * Created by Test on 2017/2/16.
 */

public class BrowserDelegateOption {
    private AppDelegate viewDelegate;
    private Context context;

    public BrowserDelegateOption(AppDelegate viewDelegate) {
        this.viewDelegate = viewDelegate;
        this.context = BaseApplication.getContext();
    }

    public void initWebView(){
        WebView webView  = viewDelegate.get(R.id.webview);
        initWebView(webView);
        webView.setDownloadListener(new MyDownLoadListener());//设置webView包含下载文件时的监听
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        EmptyLayout emptyLayout = viewDelegate.get(R.id.emptylayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) emptyLayout.getLayoutParams();
        params.height = DensityUtils.getScreenH(viewDelegate.getActivity());
        emptyLayout.setLayoutParams(params);

    }

    private class MyDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(15);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);////设置缓存模式
        //settings.setDomStorageEnabled(true);//开启DOM storage API功能
       // settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);// 设置图片可任意比例缩放

        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        addWebImageShow(webView.getContext(), webView);
    }

    /**
     * 添加网页图片的点击展示支持
     * @param context
     * @param webView
     */
    @JavascriptInterface
    public void addWebImageShow(final Context context, WebView webView) {
        webView.addJavascriptInterface(new OnWebViewImageListener() {
            @Override
            @JavascriptInterface
            public void showImagePreview(String url) {
                if(!TextUtils.isEmpty(url)){
                    KJGalleryActivity.toGallery(context, url);
                }
            }
        }, "mWebViewImageListener");

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            EmptyLayout emptyLayout = viewDelegate.get(R.id.emptylayout);
            emptyLayout.dismiss();
            WebView webView = viewDelegate.get(R.id.webview);
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            final EmptyLayout emptyLayout = viewDelegate.get(R.id.emptylayout);
            emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.loadUrl(view.getUrl());
                    emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                }
            });
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LinkDispatcher.dispatch(view, url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            //处理https请求,接受信任所有网站的证书
            handler.proceed();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress>60){
                EmptyLayout emptyLayout = viewDelegate.get(R.id.emptylayout);
                emptyLayout.dismiss();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            viewDelegate.getActivity().setTitle(LinkDispatcher.getActionTitle(view.getUrl(), title));
        }
    }

    /**
     * 设置html中图片支持点击预览
     * @param body html内容
     * @return  修改后的内容
     */
    public static String setImagePreview(String body){
        // 过滤掉 img标签的width,height属性
        body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
        body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
        // 添加点击图片放大支持
        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                "$1$2\" onClick=\"mWebViewImageListener.showImagePreview('$2')\"");
        return body;
    }


}
