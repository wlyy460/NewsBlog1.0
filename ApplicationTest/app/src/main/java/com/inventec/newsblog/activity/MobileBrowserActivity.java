package com.inventec.newsblog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.inventec.frame.base.activity.BaseBackActivity;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.BlogDetailDelegate;
import com.inventec.newsblog.utils.CommonUtil;
import com.inventec.newsblog.utils.LinkDispatcher;

/**
 * 适配手机页面的网页浏览界面
 * Created by Test on 2017/2/16.
 */

public class MobileBrowserActivity extends BaseBackActivity<BlogDetailDelegate>  {
    public static final String KEY_BLOG_URL = "blog_url_key";
    public static final String KEY_BLOG_TITLE = "blog_title_key";

    @Override
    protected Class<BlogDetailDelegate> getDelegateClass() {
        return BlogDetailDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_BLOG_URL);
        String title = intent.getStringExtra(KEY_BLOG_TITLE);
        CollapsingToolbarLayout collapsingToolbar = viewDelegate.get(R.id.collapsing_toolbar);
        title = LinkDispatcher.getActionTitle(url, title);
        if (title != null) {
            collapsingToolbar.setTitle(title);
        } else {
            collapsingToolbar.setTitle(getString(R.string.app_name));
        }
        if (savedInstanceState != null){
            viewDelegate.getWebView().restoreState(savedInstanceState);
            Log.d("MobileBrowserActivity", "restore state");
        }else{
            viewDelegate.setContentUrl(url);
        }
    }

    /**
     * 保持webView的状态
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewDelegate.getWebView().saveState(outState);
        Log.d("MobileBrowserActivity", "save state...");
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
        if (item.getItemId() == R.id.action_share
                && viewDelegate != null && viewDelegate.getWebView() != null) {
            CommonUtil.shareUrl(this, viewDelegate.getWebView().getUrl());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 跳转到博客详情界面
     *
     * @param url   传递要显示的博客的地址
     * @param title 传递要显示的博客的标题
     */
    public static void goinActivity(Context cxt, @NonNull String url, @Nullable String title) {
        Intent intent = new Intent(cxt, MobileBrowserActivity.class);
        intent.putExtra(KEY_BLOG_URL, url);
        intent.putExtra(KEY_BLOG_TITLE, title);
        cxt.startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && viewDelegate.getWebView().canGoBack()){
            //按返回键实现webView返回到上一浏览界面
            viewDelegate.getWebView().goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
