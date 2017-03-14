package com.inventec.newsblog.delegate;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.widget.ProgressLayout;
import com.rey.material.widget.EditText;

import static com.inventec.newsblog.R.id.progress_layout;

/**
 * 天气预报界面视图代理类
 * Created by Test on 2017/3/2.
 */

public class WeatherFragmentDelegate extends AppDelegate implements ILoadingView{

    private ImageView weatherImageView;
    private TextView weatherTextView, aqiTextView,sdTextView, windDirectionTextView, windPowerTextView, tempTimeTextView,
            tempTextView;

    private LinearLayout dialog_holderView;

    ProgressLayout progressLayout;
    EditText locationEditText;

    @Override
    public void initWidget() {
        super.initWidget();
        initView();
    }

    private void initView() {
        progressLayout = get(progress_layout);
        locationEditText = get(R.id.et_location);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_weather;
    }

    /**
     * 关闭软键盘
     */
    public void closeSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(locationEditText.getWindowToken(), 0);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(int messageId) {
    }

    @Override
    public Context getContext() {
        return null;
    }

}
