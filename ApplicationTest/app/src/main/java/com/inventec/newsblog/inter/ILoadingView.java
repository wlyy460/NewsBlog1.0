package com.inventec.newsblog.inter;

/**
 * 加载视图接口
 * Created by Test on 2017/2/23.
 */

public interface ILoadingView {

    void showLoading();
    void showContent();
    void showError(int messageId);
    /*Context getContext();*/
}
