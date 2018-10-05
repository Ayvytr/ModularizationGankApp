package com.ayvytr.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author admin
 */
public interface WeatherServer {
    //获取天气信息
    @Headers("Cache-Control: public, max-age=300")
    @GET("http://apicloud.mob.com/v1/weather/query")
    Observable<WeatherBeseEntity> getCityWeather(@Query("key") String appkey,
                                                 @Query("city") String city,
                                                 @Query("province") String province
    );
}
