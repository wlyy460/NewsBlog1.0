package com.inventec.newsblog.model;

import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.rx.Result;

/**
 * @author kymjs (http://www.kymjs.com/) on 11/27/15.
 */
public class Event extends Result{
    private String action;
    private Object object;
    public int arg;

    public Event(String url, VolleyError error, int errorCode) {
        super(url, error, errorCode);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public <T extends Object> T getObject() {
        return (T) object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }
}
