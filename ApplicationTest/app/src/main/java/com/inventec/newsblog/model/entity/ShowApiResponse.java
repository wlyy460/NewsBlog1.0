package com.inventec.newsblog.model.entity;

/**
 * 易源API通用响应数据
 * Created by Test on 2017/3/1.
 */

public class ShowApiResponse<T> {
    public String showapi_res_code;
    public String showapi_res_error;
    public T showapi_res_body;
}
