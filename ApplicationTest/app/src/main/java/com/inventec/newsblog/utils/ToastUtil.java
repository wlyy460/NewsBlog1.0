package com.inventec.newsblog.utils;

import android.content.Context;
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

    private Toast toast;
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
    public  ToastUtil addView(View view,int postion) {
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
     */
    public  ToastUtil Short(Context context, CharSequence message){
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
    public ToastUtil Short(Context context, int message) {
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
     */
    public ToastUtil Long(Context context, CharSequence message){
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
    public ToastUtil Long(Context context, int message) {
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
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtil Indefinite(Context context, CharSequence message, int duration) {
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
    public ToastUtil Indefinite(Context context, int message, int duration) {
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
     * 显示Toast
     * @param context
     * @param text
     */
    public void showToast(Context context, String text) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    //设置toast显示位置
    public void setGravity(int gravity, int xOffset, int yOffset) {
        //toast.setGravity(Gravity.CENTER, 0, 0); //居中显示
        toast.setGravity(gravity, xOffset, yOffset);
    }

    //设置toast显示时间
    public void setDuration(int duration) {
        toast.setDuration(duration);
    }

    //设置toast显示时间(自定义时间)
    public void setLongTime(int duration) {
        //toast.setDuration(duration);
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

    /*public void show() {
        toast.show();
    }*/

    /*public static void cancel() {
        if(toast != null) {
            toast.cancel();
        }
    }*/


}