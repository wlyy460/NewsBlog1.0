package com.inventec.newsblog.fragment;

import android.view.View;

import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.WeatherFragmentDelegate;

/**
 * 天气预报界面
 * Created by Test on 2017/3/2.
 */

public class WeatherFragment extends FragmentPresenter<WeatherFragmentDelegate> implements View.OnClickListener{
    public static final String NEED_MORE_DAY = "1";
    public static final String NEED_INDEX = "1";
    public static final String NEED_ALARM = "1";
    public static final String NEED_3_HOUR_FORCAST = "1";

    @Override
    protected Class<WeatherFragmentDelegate> getDelegateClass() {
        return WeatherFragmentDelegate.class;
    }

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_weather);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_weather){

        }
    }


}
