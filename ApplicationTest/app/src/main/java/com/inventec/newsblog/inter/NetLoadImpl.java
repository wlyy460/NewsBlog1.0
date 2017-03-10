package com.inventec.newsblog.inter;


import android.util.Log;

import com.inventec.newsblog.model.entity.ShowApiResponse;
import com.inventec.newsblog.model.news.NewsBean;
import com.inventec.newsblog.model.news.ShowApiNews;
import com.inventec.newsblog.model.pictures.PictureBean;
import com.inventec.newsblog.model.pictures.ShowApiPictures;
import com.inventec.newsblog.model.weather.WeatherBean;
import com.inventec.newsblog.server.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 网络请求接口的统一实现类（新闻，图片和天气）
 * Created by Administrator on 2017/3/7.
 */

public class NetLoadImpl implements INewsData, IPictureData, IWeatherData {

    @Override
    public void doRequestNews(int page, String channelId, String channelName,
                              final OnNetRequestListener<List<NewsBean>> listener) {
        //此处采用Retrofit的官方响应方式，天气预报和图片请求采用RxJava
        Call<ShowApiResponse<ShowApiNews>> call = RetrofitService.getInstance()
                .createShowAPI()
                .getNewsList(RetrofitService.getCacheControl(), BizInterface.SHOW_API_APPID,
                        BizInterface.SHOW_API_KEY, page, channelId, channelName);
        listener.onStart();
        call.enqueue(new Callback<ShowApiResponse<ShowApiNews>>() {

            @Override
            public void onResponse(Call<ShowApiResponse<ShowApiNews>> call, Response<ShowApiResponse<ShowApiNews>> response) {
                if (response.body() != null) {
                    Log.d("doRequestNews", "message: " + response.message() + " code:" + response.code()
                            + "\nshowapi_body: " + response.body().showapi_res_body.toString()
                            + "\nshowapi_code:" + response.body().showapi_res_code
                            + " showapi_error:" + response.body().showapi_res_error);
                    listener.onSuccess(response.body().showapi_res_body.getPageBean().getContentList());
                } else {
                    listener.onFailure(new Exception());
                }
            }
            @Override
            public void onFailure(Call<ShowApiResponse<ShowApiNews>> call, Throwable t) {
                call.request();
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void doRequestPictures(String type, int page, final OnNetRequestListener<List<PictureBean>>
            listener) {
        Observable<ShowApiResponse<ShowApiPictures>> observable = RetrofitService.getInstance()
                .createBaiduAPI().getPictures(RetrofitService.getCacheControl(), type, page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribe(new Subscriber<ShowApiResponse<ShowApiPictures>>() {
                    @Override
                    public void onCompleted() {
                        listener.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                        listener.onFinish();
                    }

                    @Override
                    public void onNext(ShowApiResponse<ShowApiPictures> response) {
                        Log.d("doRequestPictures", "showapi_body: "
                                + response.showapi_res_body
                                + "\nshowapi_code:" + response.showapi_res_code
                                + " showapi_error:" + response.showapi_res_error);
                        if (response.showapi_res_body != null) {
                            listener.onSuccess(response.showapi_res_body.getPageBean().getContentList());
                        } else {
                            listener.onFailure(new Exception());
                        }
                    }
                });
    }

    @Override
    public void doRequestWeatherWithLocation(String area, String needMoreDay, String needIndex,
                                             String needAlarm, String need3HourForcast,
                                             final OnNetRequestListener<WeatherBean> listener) {
        //使用RxJava响应Retrofit
        Observable<ShowApiResponse<WeatherBean>> observable = RetrofitService.getInstance().
                createBaiduAPI().getWeather(RetrofitService.getCacheControl(), area, needMoreDay,
                needIndex, needAlarm, need3HourForcast);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribe(new Subscriber<ShowApiResponse<WeatherBean>>() {
                    @Override
                    public void onCompleted() {
                        //仅成功后会回调
                        listener.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                        listener.onFinish();
                    }

                    @Override
                    public void onNext(ShowApiResponse<WeatherBean> response) {
                        Log.d("doRequestWeather", "showapi_body: "
                                + response.showapi_res_body.toString()
                                + "\nshowapi_code:" + response.showapi_res_code
                                + " showapi_error:" + response.showapi_res_error);
                        if (response.showapi_res_body.getNowWeather() == null) {
                            listener.onFailure(new Exception(response.showapi_res_code));
                        } else {
                            listener.onSuccess(response.showapi_res_body);
                        }
                    }
                });
    }
}
