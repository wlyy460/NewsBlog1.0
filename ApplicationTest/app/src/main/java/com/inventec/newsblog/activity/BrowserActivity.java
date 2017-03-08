package com.inventec.newsblog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.inventec.frame.base.activity.BaseBackActivity;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.BrowserDelegate;
import com.inventec.newsblog.utils.CommonUtil;

/**
 * 全局浏览器界面类
 * Created by Test on 2017/2/16.
 */

public class BrowserActivity extends BaseBackActivity<BrowserDelegate> {
    public static final String KEY_URL = "browser_url";

    @Override
    protected Class<BrowserDelegate> getDelegateClass() {
        return BrowserDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);
        if (savedInstanceState != null){
            viewDelegate.webView.restoreState(savedInstanceState);
            Log.d("BrowserActivity", "restore state");
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
        viewDelegate.webView.saveState(outState);
        Log.d("BrowserActivity", "save state...");
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
                && viewDelegate != null && viewDelegate.webView != null) {
            CommonUtil.shareUrl(this, viewDelegate.webView.getUrl());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDelegate.webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewDelegate.webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate.webView.removeAllViews();
        viewDelegate.webView.destroy();
    }

    /**
     * 跳转到博客详情界面
     *
     * @param url 传递要显示的文章的地址
     */
    public static void goinActivity(Context cxt, @NonNull String url) {
        Intent intent = new Intent(cxt, BrowserActivity.class);
        intent.putExtra(KEY_URL, url);
        cxt.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && viewDelegate.webView.canGoBack()){
            //按返回键实现webView返回到上一浏览界面
            viewDelegate.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
