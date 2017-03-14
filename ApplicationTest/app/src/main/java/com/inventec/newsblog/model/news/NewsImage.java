package com.inventec.newsblog.model.news;

/**
 * 新闻图片实体类
 * Created by Test on 2017/3/1.
 */

public class NewsImage {
    private String height;// 高度，像素
    private String url;// 图片url,
    private String width;// 宽度，像素

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "{ height:" + height + ",width:" + width
                + ",url:" + url  + " }\n";
    }
}
