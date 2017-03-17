package com.inventec.newsblog.model.weather;

/**
 * 天气实体类
 * Created by Test on 2017/3/8.
 */

public class WeatherBean {
    private NowWeather now;
    private FutureWeather f1;
    private CityInfoBean cityInfo;
    private int ret_code;// 0为应用级正常，-1失败
    private String time; // 20151023180000

    public NowWeather getNowWeather() {
        return now;
    }

    public void setNowWeather(NowWeather nowWeather) {
        this.now = nowWeather;
    }

    public FutureWeather getFutureWeather() {
        return f1;
    }

    public void setFutureWeather(FutureWeather futureWeather) {
        this.f1 = futureWeather;
    }

    public int getRetCode() {
        return ret_code;
    }

    public void setRetCode(int retCode) {
        this.ret_code = retCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{ now:" + now + ",f1:" + f1 + ",ret_code:" + ret_code
                + ",time:" + time + " }";
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }
}
