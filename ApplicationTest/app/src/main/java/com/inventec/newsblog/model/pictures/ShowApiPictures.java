package com.inventec.newsblog.model.pictures;

import com.inventec.newsblog.model.entity.PageBean;

/**
 * 美图大全列表的实体类
 * Created by Administrator on 2017/3/4.
 */

public class ShowApiPictures {
    private PageBean<PictureBean> pageBean;//分页数
    private String resultCode;

    public PageBean<PictureBean> getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean<PictureBean> pageBean) {
        this.pageBean = pageBean;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
