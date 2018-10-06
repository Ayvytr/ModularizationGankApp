package com.ayvytr.mvp.mvp.contract;

import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;
import com.ayvytr.mvp.WeatherBeseEntity;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class MainContract {
    public interface View extends IView {
        void showInterval(Long aLong);

        void showWeather(WeatherBeseEntity weatherBeseEntity);
    }

    public interface Model extends IModel {
        Observable<Long> getData();

        Observable<WeatherBeseEntity> getWeather();
    }
}
