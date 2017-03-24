package com.inventec.newsblog.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Toast设置的相关工具类
 * Created by Test on 2017/2/8.
 */

public class ToastUtil {
    private static Toast toast;
    private int time;
    private Timer timer;
    private LinearLayout toastView;

    /**
     * 修改原布局的Toast
     */
    public ToastUtil() {
    }

    /**
     * 完全自定义布局Toast
     * @param context
     * @param view
     */
    public ToastUtil(Context context, View view, int duration){
        toast=new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }

    /**
     * 向Toast中添加自定义view
     * @param view
     * @param postion
     * @return
     */
    public ToastUtil addView(View view,int postion) {
        toastView = (LinearLayout) toast.getView();
        toastView.addView(view, postion);

        return this;
    }

    /**
     * 设置Toast字体及背景颜色
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public ToastUtil setToastColor(int messageColor, int backgroundColor) {
        View view = toast.getView();
        if(view!=null){
            TextView message=((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 设置Toast字体及背景
     * @param messageColor
     * @param background
     * @return
     */
    public ToastUtil setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        if(view!=null){
            TextView message=((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundResource(background);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 短时间显示Toast
     * @param context
     * @param message
     * @return
     */
    public ToastUtil shortToast(Context context, CharSequence message){
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 短时间显示Toast
     */
    public ToastUtil shortToast(Context context, int message) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 长时间显示Toast
     * @param context
     * @param message
     * @return
     */
    public ToastUtil longToast(Context context, CharSequence message){
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public void longToast(Context context, int message) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtil indefiniteToast(Context context, CharSequence message, int duration) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message,duration);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtil indefiniteToast(Context context, int message, int duration) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message,duration);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 显示Toast
     * @return
     */
    public ToastUtil show (){
        toast.show();
        return this;
    }
    /**
     * 获取Toast
     * @return
     */
    public Toast getToast(){
        return toast;
    }

    /**
     * 取消显示Toast
     */
    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 显示中心位置的Toast
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text, int duration) {
        if(toast == null) {
            toast = Toast.makeText(context, text, duration);
        }else{
            toast.setGravity(Gravity.TOP, 0,0);
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 设置toast显示位置
     */
    public ToastUtil setGravity(int gravity, int xOffset, int yOffset) {
        //toast.setGravity(Gravity.CENTER, 0, 0); //居中显示
        toast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    /**
     * 设置toast显示时间
     */
    public void setDuration(int duration) {
        toast.setDuration(duration);
    }

    /**
     * 设置toast定时显示时的时间
     */
    public void setToastTime(int duration) {
        time = duration;
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {

                if(time-1000 >= 0) {
                    show();
                    time= time - 1000;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }


}
