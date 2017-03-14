package com.inventec.newsblog.inter;

import com.inventec.newsblog.model.pictures.PictureBean;

import java.util.List;

/**
 * 图片大全数据接口
 * Created by Administrator on 2017/3/4.
 */

public interface IPictureData {
    public String DEFAULT_TYPE = "4004";//类别

    /**
     * 请求加载图片数据
     * @param type 图片的类型
     * @param page 页数
     * @param listener 网络数据加载监听
     */
    void doRequestPictures(String type, int page, OnNetRequestListener<List<PictureBean>> listener);
}
