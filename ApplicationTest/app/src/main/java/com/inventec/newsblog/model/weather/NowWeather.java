package com.inventec.newsblog.model.weather;

/**
 * 当天的天气实体类
 * Created by Test on 2017/3/8.
 */

public class NowWeather {
    private String aqi;//污染指数
    private String sd;//湿度
    private String temperature;//气温
    private String tempTime;//气温时间
    private String weather;//天气“晴雨”
    private String weatherCode;//天气编码
    private String weatherPic;//天气图片
    private String windDirection;//风向
    private String windPower;//风力

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTempTime() {
        return tempTime;
    }

    public void setTempTime(String tempTime) {
        this.tempTime = tempTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherPic() {
        return weatherPic;
    }

    public void setWeatherPic(String weatherPic) {
        this.weatherPic = weatherPic;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }
}
