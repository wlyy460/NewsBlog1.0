package com.inventec.newsblog.inter;

/**
 * webview上的图片监听接口
 * Created by Test on 2017/2/13.
 */

public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     * @param url 图片地址
     */
    void showImagePreview(String url);

}
