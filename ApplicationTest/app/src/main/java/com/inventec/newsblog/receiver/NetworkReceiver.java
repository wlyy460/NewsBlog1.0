package com.inventec.newsblog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.inventec.newsblog.utils.NetworkUtil;

/**
 * 检查手机网络状态切换的广播接收器
 */
public class NetworkReceiver extends BroadcastReceiver {
    private netEventHandler eventHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            boolean isNetworkConnected = NetworkUtil.checkNetworkConnected(context);
            if (eventHandler != null)
                eventHandler.onNetChange(isNetworkConnected);
        }
    }

    public interface netEventHandler {
       void onNetChange(boolean networkState);
    }

    /**
     * 设置网络状态变化事件的回调处理
     * @param netHandler
     */
    public void setNetEventHandler(netEventHandler netHandler){
        this.eventHandler = netHandler;
    }

}
