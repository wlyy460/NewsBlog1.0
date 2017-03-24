package com.inventec.newsblog.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inventec.frame.themvp.presenter.ActivityPresenter;
import com.inventec.frame.themvp.view.IDelegate;
import com.inventec.newsblog.R;
import com.inventec.newsblog.receiver.NetworkReceiver;
import com.inventec.newsblog.utils.ToastUtil;
import com.inventec.newsblog.utils.UIUtil;

/**
 * Activity基类
 *
 * @author kymjs (http://www.kymjs.com/) on 11/19/15.
 */
public abstract class BaseFrameActivity<T extends IDelegate> extends ActivityPresenter<T> implements
NetworkReceiver.netEventHandler{
    protected LinearLayout rootView;
    private NetworkReceiver networkReceiver;
    private int titleBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        super.onCreate(savedInstanceState);
        super.setContentView(rootView);
        registerNetworkRecevier();
        networkReceiver.setNetEventHandler(this);
    }

    /**
     * 注册网络状态变化的广播接收器
     */
    private void registerNetworkRecevier(){
        networkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    public void onNetChange(boolean networkState) {
        if (!networkState){
            ToastUtil toastUtil = new ToastUtil();
            toastUtil.longToast(this, getString(R.string.unable_to_connect_to_the_network_prompt))
                    .setGravity(Gravity.TOP, 0, UIUtil.getStatusBarHeight() + titleBarHeight).show();
        }
    }

    @Override
    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (toolbar == null) {
            toolbar = (Toolbar) View.inflate(this, R.layout.base_toolbar, null);
            rootView.addView(toolbar, 0);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootView.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 弹窗--新手指引
     * @param cxt
     * @param id 资源编号
     * @create_time 2011-7-27 下午05:12:49
     */
    public static void displayWindow(Context cxt, int id) {
        final TextView imgTextView = new TextView(cxt.getApplicationContext());
        imgTextView.setBackgroundDrawable(cxt.getResources().getDrawable(id));//设置背景
        imgTextView.setText("无法连接到网络，请检查网络设置");
        final WindowManager wm = (WindowManager) cxt.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        wmParams.format = 1;
        wmParams.flags = 40;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.addView(imgTextView, wmParams);
        imgTextView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(imgTextView);//点击，将该窗口消失掉
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        titleBarHeight = UIUtil.getTitleBarHeight(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null){
            unregisterReceiver(networkReceiver);
        }
    }
}
