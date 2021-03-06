package com.inventec.newsblog.model.news;

import com.inventec.newsblog.model.entity.PageBean;

/**
 * 新闻列表实体类
 * Created by Test on 2017/3/1.
 */

public class ShowApiNews {
    private PageBean<NewsBean> pagebean;
    private int ret_code;//0为返回成功

    public int getResultCode() {
        return ret_code;
    }

    public void setResultCode(int resultCode) {
        this.ret_code = resultCode;
    }

    public PageBean<NewsBean> getPageBean() {
        return pagebean;
    }

    public void setPageBean(PageBean<NewsBean> pageBean) {
        this.pagebean = pageBean;
    }

    @Override
    public String toString() {
        return "{ ret_code:" + ret_code + ",pagebean:" + pagebean + " }";
    }
}
