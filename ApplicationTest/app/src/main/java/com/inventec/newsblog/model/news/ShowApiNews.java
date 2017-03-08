package com.inventec.newsblog.model.news;

import com.inventec.newsblog.model.entity.PageBean;

/**
 * 新闻列表实体类
 * Created by Test on 2017/3/1.
 */

public class ShowApiNews {
    private PageBean<NewsBean> pageBean;
    private String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public PageBean<NewsBean> getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean<NewsBean> pageBean) {
        this.pageBean = pageBean;
    }
}
