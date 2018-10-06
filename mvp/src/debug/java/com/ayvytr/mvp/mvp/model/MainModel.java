package com.ayvytr.mvp.mvp.model;

import com.ayvytr.mvp.WeatherBeseEntity;
import com.ayvytr.mvp.mvp.contract.MainContract;
import com.ayvytr.mvp.mvp.model.bean.WeatherServer;
import com.ayvytr.network.ApiClient;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class MainModel implements MainContract.Model {
    private final WeatherServer weatherServer;

    public MainModel() {
        weatherServer = ApiClient.getInstance().create(WeatherServer.class);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<Long> getData() {
        return Observable.interval(0, 1, TimeUnit.SECONDS);
    }

    @Override
    public Observable<WeatherBeseEntity> getWeather() {
        return weatherServer.getCityWeather("1c9dccb9a2434", "深圳", "广东");
    }
}
