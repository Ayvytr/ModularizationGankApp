package com.ayvytr.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author admin
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        requestWeather();
    }

    private void initView() {
        tv = findViewById(R.id.tv);
    }

    private void requestWeather() {
        boolean available = NetworkUtils.isAvailable(this);
        Toast.makeText(MainActivity.this, available ? "网络可用" : "网络不可用", Toast.LENGTH_SHORT).show();
        if(available) {
            ApiClient.getInstance().init();
            WeatherServer weatherServer = ApiClient.getInstance().create(WeatherServer.class);
            weatherServer.getCityWeather("1c9dccb9a2434", "深圳", "广东")
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(new ApiObserver<WeatherBeseEntity>() {
                             @Override
                             public void onNext(WeatherBeseEntity weatherBeseEntity) {
                                 WeatherBeseEntity.WeatherBean weather = weatherBeseEntity.getResult().get(0);
                                 tv.setText(weather.getCity() + "天气：" + weather.getWeather());
                             }
                         });
        }
    }
}
