package com.inventec.newsblog.fragment;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.WeatherFragmentDelegate;
import com.inventec.newsblog.inter.IWeatherData;
import com.inventec.newsblog.inter.NetLoadImpl;
import com.inventec.newsblog.inter.OnNetRequestListener;
import com.inventec.newsblog.model.weather.WeatherBean;
import com.inventec.newsblog.utils.LogUtil;
import com.inventec.newsblog.utils.SnackbarUtil;
import com.inventec.newsblog.utils.ToastUtil;

/**
 * 天气预报界面
 * Created by Test on 2017/3/2.
 */

public class WeatherFragment extends FragmentPresenter<WeatherFragmentDelegate> implements View.OnClickListener{
    private static final String TAG = WeatherFragment.class.getSimpleName();
    public static final String NEED_MORE_DAY = "1";
    public static final String NEED_INDEX = "1";
    public static final String NEED_ALARM = "1";
    public static final String NEED_3_HOUR_FORCAST = "1";
    private IWeatherData mIWeatherData;

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
        mIWeatherData = new NetLoadImpl();
        viewDelegate.setOnClickListener(this, R.id.btn_weather);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_weather){
            doLoadWeather();
        }
    }

    /**
     * 网络加载获取天气信息
     */
    private void doLoadWeather(){
        if(TextUtils.isEmpty(viewDelegate.getInputLocation())){
            ToastUtil.showToast(getActivity(), getString(R.string.input_empty_prompt),
                    Toast.LENGTH_SHORT);
            return;
        }
        mIWeatherData.doRequestWeatherWithLocation(viewDelegate.getInputLocation(), NEED_MORE_DAY,
                NEED_INDEX, NEED_ALARM, NEED_3_HOUR_FORCAST, new OnNetRequestListener<WeatherBean>() {
                    @Override
                    public void onStart() {
                        if(viewDelegate != null){
                            viewDelegate.showLoading();
                        }
                    }
                    @Override
                    public void onFinish() {
                        if(viewDelegate != null){
                            viewDelegate.showContent();
                        }
                    }

                    @Override
                    public void onSuccess(WeatherBean data) {
                        LogUtil.d(TAG, "onSuccess");
                        if (data.getRetCode() == 0){
                            viewDelegate.showNowWeatherDialog(data);
                        }else{
                            SnackbarUtil.showSnackbar(viewDelegate.getRootView(),
                                    R.string.error_input, Snackbar.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        LogUtil.d(TAG, "====网络请求异常," + t.getMessage());
                        SnackbarUtil.showSnackbar(viewDelegate.getRootView(),
                                R.string.error_prompt, Snackbar.LENGTH_SHORT);
                        /*SnackbarUtil.shortSnackbar(viewDelegate.getRootView(),
                                getString(R.string.error_prompt), Color.WHITE, viewDelegate.getActivity()
                                        .getResources().getColor(R.color.main_gray) )
                        .show();*/
                    }
                });

    }


}
