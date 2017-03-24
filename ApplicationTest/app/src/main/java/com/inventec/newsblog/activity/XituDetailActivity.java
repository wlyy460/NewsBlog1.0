package com.inventec.newsblog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.inventec.newsblog.R;
import com.inventec.newsblog.base.activity.BaseBackActivity;
import com.inventec.newsblog.delegate.BlogDetailDelegate;
import com.inventec.newsblog.delegate.BrowserDelegateOption;
import com.inventec.newsblog.inter.IRequestVo;
import com.inventec.newsblog.model.XituBlog;
import com.inventec.newsblog.utils.CommonUtil;
import com.inventec.newsblog.utils.LinkDispatcher;
import com.inventec.newsblog.widget.EmptyLayout;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.rx.Result;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 有关稀土掘金数据的博客详情界面
 * Created by Test on 2017/2/17.
 */

public class XituDetailActivity extends BaseBackActivity<BlogDetailDelegate> implements IRequestVo {
    public static final String KEY_XITU_DATA = "xitu_data_key";

    private XituBlog data;

    private EmptyLayout emptyLayout;
    private String contentUrl;
    protected WebView webView;

    private Subscription cacheSubscript;
    private Subscription requestSubscript;
    @Override
    protected Class<BlogDetailDelegate> getDelegateClass() {
        return BlogDetailDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        webView = viewDelegate.get(R.id.webview);
        emptyLayout = viewDelegate.get(R.id.emptylayout);
        emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
                emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        data = intent.getParcelableExtra(KEY_XITU_DATA);
        String title = data.getTitle();
        CollapsingToolbarLayout collapsingToolbar = viewDelegate.get(R.id.collapsing_toolbar);
        if (title != null) {
            collapsingToolbar.setTitle(title);
        } else {
            collapsingToolbar.setTitle(getString(R.string.xitu_community_name));
        }
        if (savedInstanceState != null){
            webView.restoreState(savedInstanceState);
            Log.d("XituDetailActivity", "restore state");
        }else{
            if (data != null) {
                readCache();
                doRequest();
            }
        }
    }

    /**
     * 读取缓存内容
     */
    private void readCache() {
        cacheSubscript = Observable.just(RxVolley.getCache(data.getLink()))
                .map(new Func1<byte[], String>() {
                    @Override
                    public String call(byte[] bytes) {
                        return new String(bytes);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return contentUrl = parserHtml(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        viewDelegate.setContentUrl(contentUrl);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        contentUrl = "";
                    }
                });
    }

    /**
     * 保持webView的状态
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
        Log.d("XituDetailActivity", "save state...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewDelegate != null && webView != null){
            webView.removeAllViews();
            webView.destroy();
        }
        if (cacheSubscript != null && cacheSubscript.isUnsubscribed()) {
            cacheSubscript.unsubscribe();
        }
        if (requestSubscript != null && requestSubscript.isUnsubscribed()) {
            requestSubscript.unsubscribe();
        }
    }

    @Override
    public void doRequest() {
        requestSubscript = new RxVolley.Builder().url(data.getLink()).getResult()
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return contentUrl = parserHtml(new String(result.data));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s != null && viewDelegate != null) {
                            if (!TextUtils.isEmpty(contentUrl)) {
                                viewDelegate.setContent(contentUrl);
                            }else{
                                viewDelegate.setContentUrl(data.getLink());
                            }
                        } else {
                            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                    }
                });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_share && !TextUtils.isEmpty(contentUrl)) {
            CommonUtil.shareUrl(this, contentUrl);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 对博客数据加工,适应手机浏览
     * @param html
     * @return
     */
    public String parserHtml(String html) {
        String title = data.getTitle();
        int sub = title.indexOf(']');
        if (sub < 0) return "";
        title = title.substring(sub + 1).trim();

        int index = html.indexOf(title + "</h2>");
        if (index < 0) return "";
        html = html.substring(0, index).trim();

        int star = html.lastIndexOf("<a href=\"");
        if (star < 0) return "";
        html = html.substring(star + 9);

        int end = html.indexOf("\"");
        if (end < 0) return "";
        html = html.substring(0, end);
        return html;
    }

    /**
     * 跳转到稀土掘金博客详情界面
     * @param cxt
     * @param data 传递要显示的博客信息
     */
    public static void goinActivity(Context cxt, XituBlog data) {
        Intent intent = new Intent(cxt, XituDetailActivity.class);
        intent.putExtra(KEY_XITU_DATA, data);
        cxt.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            //按返回键实现webView返回到上一浏览界面
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
