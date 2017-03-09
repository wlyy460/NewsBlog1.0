package com.inventec.newsblog.model.weather;

/**
 * 天气实体类
 * Created by Test on 2017/3/8.
 */

public class WeatherBean {
    private NowWeather now;
    private FutureWeather f1;

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
}
