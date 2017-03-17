package com.inventec.newsblog.model.weather;

/**
 * 城市信息实体类
 * Created by Test on 2017/3/17.
 */

public class CityInfoBean {
    private String c1;//区域id
    private String c3; //城市中文名
    private String c5; //城市所在市中文名
    private String c7; //城市所在省中文名
    private String c9; //城市所在国家中文名
    private String c10; //城市级别
    private String c11; //城市区号
    private String c12; //邮编
    private float longitude; //经度
    private float latitude; //纬度


    public String getAreaCodeId() {
        return c1;
    }

    public void setAreaCodeId(String areaCodeId) {
        this.c1 = areaCodeId;
    }

    public String getCityName() {
        return c3;
    }

    public void setCityName(String cityName) {
        this.c3 = cityName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
