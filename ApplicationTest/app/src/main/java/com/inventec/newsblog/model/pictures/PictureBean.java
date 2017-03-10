package com.inventec.newsblog.model.pictures;

import java.util.List;

/**
 * 图片大全实体类
 * Created by Test on 2017/3/2.
 */

public class PictureBean {
    private String ct;// 2016-03-10 04;12;6.606,创建时间
    private String itemId;// 相册id
    private String title;// 相册标题
    private String type;// 相册类型
    private String typeName;// 相册类型名称
    public List<ImgType> list;//图片数组

    public String getTime() {
        return ct;
    }

    public void setTime(String time) {
        this.ct = time;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "\n{ ct:" + ct + ",itemId:" + itemId
                + ",title:" + title + ",type:" + type + ",typeName:" + typeName
                + ",list:" + list  + " }";
    }
}
