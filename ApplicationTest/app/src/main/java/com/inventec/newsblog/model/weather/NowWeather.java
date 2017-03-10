package com.inventec.newsblog.model.weather;

/**
 * 当天的天气实体类
 * Created by Test on 2017/3/8.
 */

public class NowWeather {
    private String aqi;//污染指数
    private String sd;//湿度
    private String temperature;//气温
    private String temperature_time;//气温时间
    private String weather;//天气“晴雨”
    private String weather_code;//天气编码
    private String weather_pic;//天气图片地址
    private String wind_direction;//风向
    private String wind_power;//风力

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
        return temperature_time;
    }

    public void setTempTime(String tempTime) {
        this.temperature_time = tempTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherCode() {
        return weather_code;
    }

    public void setWeatherCode(String weatherCode) {
        this.weather_code = weatherCode;
    }

    public String getWeatherPic() {
        return weather_pic;
    }

    public void setWeatherPic(String weatherPic) {
        this.weather_pic = weatherPic;
    }

    public String getWindDirection() {
        return wind_direction;
    }

    public void setWindDirection(String windDirection) {
        this.wind_direction = windDirection;
    }

    public String getWindPower() {
        return wind_power;
    }

    public void setWindPower(String windPower) {
        this.wind_power = windPower;
    }

    @Override
    public String toString() {
        return "\n{ aqi:" + aqi + ",sd:" + sd + ",temperature:" + temperature
                + ",temperature_time:" + temperature_time + ",weather:" + weather
                + ",weather_code:" + weather_code + ",weather_pic:" + weather_pic
                + ",wind_direction:" + wind_direction + ",wind_power" + wind_power + " }";
    }
}
