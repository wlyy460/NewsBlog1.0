package com.inventec.newsblog.model.pictures;

/**
 * 不同大小类型的图片实体类
 * Created by Administrator on 2017/3/4.
 */

public class ImgType {
    private String big;// 大图路径
    private String middle;// 中图路径
    private String small;// 小图路径

    public String getBigUrl() {
        return big;
    }

    public void setBigUrl(String bigUrl) {
        this.big = bigUrl;
    }

    public String getMiddleUrl() {
        return middle;
    }

    public void setMiddleUrl(String middleUrl) {
        this.middle = middleUrl;
    }

    public String getSmallUrl() {
        return small;
    }

    public void setSmallUrl(String smallUrl) {
        this.small = smallUrl;
    }
}
