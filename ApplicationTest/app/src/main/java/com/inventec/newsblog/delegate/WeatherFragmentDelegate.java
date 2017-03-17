package com.inventec.newsblog.delegate;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.model.weather.WeatherBean;
import com.inventec.newsblog.utils.GlideUtil;
import com.inventec.newsblog.widget.ProgressLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.rey.material.widget.EditText;


/**
 * 天气预报界面视图代理类
 * Created by Test on 2017/3/2.
 */

public class WeatherFragmentDelegate extends AppDelegate implements ILoadingView{

    private ImageView weatherImageView;
    private TextView locationTextView, weatherTextView, aqiTextView,sdTextView,
            windDirectionTextView, windPowerTextView, tempTimeTextView, tempTextView;

    private View dialogHolderView;

    private ProgressLayout mProgressLayout;
    private EditText locationEditText;

    @Override
    public void initWidget() {
        super.initWidget();
        initView();
    }

    private void initView() {
        mProgressLayout = get(R.id.progress_layout);
        locationEditText = get(R.id.et_location_name);

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_weather;
    }

    /**
     * 获取输入的地名
     * @return
     */
    public String getInputLocation(){
        return locationEditText.getText().toString().trim();
    }

    /**
     * 查找弹窗holderView的子控件
     *
     * @param holder
     */
    private void findHolderChildView(Holder holder) {
        locationTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_location);
        weatherImageView = (ImageView) holder.getInflatedView().findViewById(R.id.iv_weather);
        weatherTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_weather);
        tempTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature);
        tempTimeTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature_time);
        aqiTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_aqi);
        sdTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_sd);
        windDirectionTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_direction);
        windPowerTextView = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_power);
    }

    /**
     * 显示当前天气弹窗
     */
    public void showNowWeatherDialog(WeatherBean weather) {
        dialogHolderView = getActivity().getLayoutInflater().inflate(R.layout.dialog_weather, null);
        Holder holder = new ViewHolder(dialogHolderView);
        findHolderChildView(holder);
        GlideUtil.loadImage(getActivity(), weather.getNowWeather().getWeatherPic().trim(), weatherImageView);
        locationTextView.setText(weather.getCityInfo().getCityName().trim());
        weatherTextView.setText(weather.getNowWeather().getWeather());
        tempTextView.setText(weather.getNowWeather().getTemperature() + "℃");
        tempTimeTextView.setText(weather.getNowWeather().getTempTime());
        aqiTextView.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_aqi),
                weather.getNowWeather().getAqi()));
        sdTextView.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_sd),
                weather.getNowWeather().getSd()));
        windDirectionTextView.setText(String.format(getActivity().getResources()
                        .getString(R.string.weather_dialog_wind_direction),
                weather.getNowWeather().getWindDirection()));
        windPowerTextView.setText(String.format(getActivity().getResources()
                        .getString(R.string.weather_dialog_wind_power),
                weather.getNowWeather().getWindPower()));
        showContentDialog(holder, Gravity.BOTTOM, false);
    }

    /**
     * 仅显示内容的dialog
     * @param holder
     * @param bottom 显示位置（居中，底部，顶部）
     * @param expanded 是否支持展开（有列表时适用）
     */
    private void showContentDialog(Holder holder, int bottom, boolean expanded) {
        DialogPlus dialog = DialogPlus.newDialog(this.getActivity())
                .setContentHolder(holder)
                .setGravity(bottom)
                .setExpanded(expanded)
                .setCancelable(true)
                .create();
        dialog.show();


    }


    @Override
    public void showLoading() {
        if (!mProgressLayout.isLoading()){
            mProgressLayout.showLoading();
        }
    }

    @Override
    public void showContent() {
        if (!mProgressLayout.isContent()) {
            mProgressLayout.showContent();
        }
    }

    @Override
    public void showError(int messageId) {
    }

}
