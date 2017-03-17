package com.inventec.newsblog.model.pictures;

import com.inventec.newsblog.model.entity.PageBean;

/**
 * 美图大全列表的实体类
 * Created by Administrator on 2017/3/4.
 */

public class ShowApiPictures {
    private PageBean<PictureBean> pagebean;//分页数
    private int ret_code;//0为返回成功

    public PageBean<PictureBean> getPageBean() {
        return pagebean;
    }

    public void setPageBean(PageBean<PictureBean> pageBean) {
        this.pagebean = pageBean;
    }

    public int getResultCode() {
        return ret_code;
    }

    public void setResultCode(int resultCode) {
        this.ret_code = resultCode;
    }

    @Override
    public String toString() {
        return "{ ret_code:" + ret_code + ",pagebean:" + pagebean + " }";
    }
}
