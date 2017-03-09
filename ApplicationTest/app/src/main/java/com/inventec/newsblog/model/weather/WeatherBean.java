package com.inventec.newsblog.model.weather;

/**
 * 天气实体类
 * Created by Test on 2017/3/8.
 */

public class WeatherBean {
    private NowWeather nowWeather;
    private FutureWeather futureWeather;

    public NowWeather getNowWeather() {
        return nowWeather;
    }

    public void setNowWeather(NowWeather nowWeather) {
        this.nowWeather = nowWeather;
    }

    public FutureWeather getFutureWeather() {
        return futureWeather;
    }

    public void setFutureWeather(FutureWeather futureWeather) {
        this.futureWeather = futureWeather;
    }
}
