package com.inventec.newsblog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.inventec.frame.base.activity.BaseBackActivity;
import com.inventec.newsblog.AppConfig;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.BlogDetailDelegate;
import com.inventec.newsblog.delegate.BrowserDelegateOption;
import com.inventec.newsblog.inter.IRequestVo;
import com.inventec.newsblog.utils.CommonUtil;
import com.inventec.newsblog.widget.EmptyLayout;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.rx.Result;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 技术博客详情界面
 * Created by Test on 2017/2/15.
 */

public class BlogDetailActivity extends BaseBackActivity<BlogDetailDelegate> implements IRequestVo/*,
        AppBarLayout.OnOffsetChangedListener*/ {
    public static final String KEY_BLOG_URL = "blog_url_key";
    public static final String KEY_BLOG_TITLE = "blog_title_key";
    protected String url;

    protected EmptyLayout emptyLayout;
    protected WebView webView;
    protected String contentHtml = null;
    protected byte[] httpCache = null;

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
        Log.d("BlogDetailActivity", "onCreate");
        Intent intent = getIntent();
        url = intent.getStringExtra(KEY_BLOG_URL);
        String title = intent.getStringExtra(KEY_BLOG_TITLE);
        CollapsingToolbarLayout collapsingToolbar = viewDelegate.get(R.id.collapsing_toolbar);
        if (title != null) {
            collapsingToolbar.setTitle(title);
        } else {
            collapsingToolbar.setTitle(getString(R.string.kymjs_blog_name));
        }
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
            Log.d("BlogDetailActivity", "restore state");
        } else {
            readCache();
            doRequest();
        }
    }

    /**
     * 保持webView的状态
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
        Log.d("BlogDetailActivity", "save state...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BlogDetailActivity", "onRestart");
        //可能在点击完webview图片后,再返回,webview内容就没了,只好再设置一次
        viewDelegate.setContent(contentHtml);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BlogDetailActivity", "onPause");
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BlogDetailActivity", "onResume");
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BlogDetailActivity", "onDestroy");
        webView.removeAllViews();
        webView.destroy();
        if (cacheSubscript != null && cacheSubscript.isUnsubscribed())
            cacheSubscript.unsubscribe();
        if (requestSubscript != null && requestSubscript.isUnsubscribed())
            requestSubscript.unsubscribe();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_share && !TextUtils.isEmpty(url)) {
            CommonUtil.shareUrl(this, url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 读取缓存内容
     */
    private void readCache() {
        cacheSubscript = Observable.just(RxVolley.getCache(url))
                .filter(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] cache) {
                        httpCache = cache;
                        return cache != null && cache.length != 0;
                    }
                })
                .map(new Func1<byte[], String>() {
                    @Override
                    public String call(byte[] bytes) {
                        return parserHtml(new String(bytes));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String content) {
                        contentHtml = content;
                        emptyLayout.dismiss();
                        viewDelegate.setContent(content);
                        viewDelegate.setCurrentUrl(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    public void doRequest() {
        if (TextUtils.isEmpty(url)) return;
        requestSubscript = new RxVolley.Builder().url(url).getResult()
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return contentHtml = parserHtml(new String(result.data));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                    }

                    @Override
                    public void onNext(String s) {
                        if (!new String(httpCache).equals(s)
                                && viewDelegate != null
                                && contentHtml != null) {
                            viewDelegate.setContent(contentHtml);
                        }
                        emptyLayout.dismiss();
                    }
                });
    }

    public static final String regEx_script =
            "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
    public static final String regEx_header =
            "<[\\s]*?header[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?header[\\s]*?>";
    public static final String regEx_footer =
            "<[\\s]*?footer[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?footer[\\s]*?>";

    /**
     * 对博客数据加工,适应手机浏览
     *
     * @param html
     * @return
     */
    public static String parserHtml(String html) {
        html = html.replaceAll(regEx_script, "");
        html = html.replaceAll(regEx_header, "");
        html = html.replaceAll(regEx_footer, "");
        html = html.replaceAll("<img src=\"/", "<img src=\"http://kymjs.com/");
        html = html.replaceAll("<a href=\"/donate\">", "<a href=\"http://kymjs.com/donate\">");
        html = BrowserDelegateOption.setImagePreview(html);
        String commonStyle = "<link rel=\"stylesheet\" type=\"text/css\" " +
                "href=\"file:///android_asset/template/common.css\">";
        html = commonStyle + html;
        return html;
    }

    public static void goinActivity(Context cxt, @NonNull String url, @Nullable String title) {
        if (url.toLowerCase().contains(AppConfig.DONATE_STR)
                && CommonUtil.checkApkExist(cxt, AppConfig.ALIPAY_PKGNAME)) {
            CommonUtil.copy(cxt, AppConfig.ALIPAY_ID);
            CommonUtil.openOtherApp(cxt, AppConfig.ALIPAY_PKGNAME, AppConfig.ALIPAY_MAIN_NAME);
        } else {
            Intent intent = new Intent(cxt, BlogDetailActivity.class);
            intent.putExtra(KEY_BLOG_URL, url);
            if (TextUtils.isEmpty(title))
                title = cxt.getString(R.string.kymjs_blog_name);
            intent.putExtra(KEY_BLOG_TITLE, title);
            cxt.startActivity(intent);
        }
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

    /*@Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float titlePercentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        Toolbar toolbar = viewDelegate.get(R.id.toolbar);
        toolbar.getBackground().mutate().setAlpha((int) (255 * titlePercentage));
    }*/
}
