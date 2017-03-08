package com.inventec.newsblog.inter;


import com.inventec.newsblog.model.news.NewsBean;
import com.inventec.newsblog.model.pictures.PictureBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class NetLoadImpl implements INewsData, IPictureData {

    @Override
    public void doRequestPictures(String type, int page, OnNetRequestListener<List<PictureBean>> listener) {

    }

    @Override
    public void doRequestNews(int page, String channelId, String channelName, OnNetRequestListener<List<NewsBean>> listListener) {

    }
}
