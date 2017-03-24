package com.inventec.newsblog.model.pictures;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * 图片大全实体类
 * Created by Test on 2017/3/2.
 */

public class PictureBean implements Comparable<PictureBean>{
    private String ct;// 2016-03-10 04;12;6.606,创建时间
    private String itemId;// 相册id
    private String title;// 相册标题
    private String type;// 相册类型
    private String typeName;// 相册类型名称
    private List<ImgType> list;//图片数组

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

    public List<ImgType> getList() {
        return list;
    }

    public void setList(List<ImgType> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ct:").append(ct)
                .append(",itemId:" ).append(itemId)
                .append(",title:").append(title)
                .append(",type:").append(type)
                .append(",typeName:").append(typeName)
                .append(",\nlist:").append(list).append("}\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  PictureBean){
            PictureBean data = (PictureBean) obj;
            //通过比较相册标题或者创建时间进行判断两个对象是否相等
            if (data.title != null && (data.ct != null)){
                return (data.title.equals(title) || ((data.ct.equals(this.ct))));
            }else{
               return this.title == null || (this.ct == null);
            }
        }else{
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }

    @Override
    public int compareTo(@NonNull PictureBean obj) {
        return this.ct.compareTo(obj.getTime().trim());
    }
}
