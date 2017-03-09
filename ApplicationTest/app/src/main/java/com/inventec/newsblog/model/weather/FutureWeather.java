package com.inventec.newsblog.model.weather;

/**
 * 未来一天的天气预报实体类
 * Created by Test on 2017/3/8.
 */

public class FutureWeather {
    public String day;//日期
    public String air_press;//气压
    public String sun_begin_end;//白天持续时间
    public String weekday;//星期几
    //白天
    public String day_air_temperature;//气温
    public String day_weather;//天气“晴雨”
    public String day_weather_code;//天气代码
    public String day_weather_pic;//天气图片
    public String day_wind_direction;//风向
    public String day_wind_power;//风力
    //晚上
    public String night_air_temperature;
    public String night_weather;
    public String night_weather_code;
    public String night_weather_pic;
    public String night_wind_direction;
    public String night_wind_power;
}
