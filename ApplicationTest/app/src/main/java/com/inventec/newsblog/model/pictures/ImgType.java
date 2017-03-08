package com.inventec.newsblog.model.pictures;

/**
 * 不同大小类型的图片实体类
 * Created by Administrator on 2017/3/4.
 */

public class ImgType {
    private String bigUrl;// 大图路径
    private String middleUrl;// 中图路径
    private String smallUrl;// 小图路径

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public String getMiddleUrl() {
        return middleUrl;
    }

    public void setMiddleUrl(String middleUrl) {
        this.middleUrl = middleUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
