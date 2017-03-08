package com.inventec.newsblog.delegate;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;

/**
 * 浏览器视图代理类
 * Created by Test on 2017/2/15.
 */

public class BrowserDelegate extends AppDelegate {
    public LinearLayout mLayoutBottom;
    public WebView webView;

    private String currentUrl;

    @Override
    public int getRootLayoutId() {
        return R.layout.layout_browser;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public void setContentUrl(String url) {
        webView.loadUrl(url);
        this.currentUrl = url;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        webView = get(R.id.webview);
        mLayoutBottom = (LinearLayout) View.inflate(getActivity(),
                R.layout.layout_browser_bottombar, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams
                .WRAP_CONTENT);
        params.leftMargin = 60;
        params.rightMargin = 60;
        params.bottomMargin = 30;
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ((RelativeLayout) get(R.id.browser_root_layout)).addView(mLayoutBottom, 1, params);
        mLayoutBottom.setVisibility(View.GONE);
        new BrowserDelegateOption(this).initWebView();
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_share;
    }
}
