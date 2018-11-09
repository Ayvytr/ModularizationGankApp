package com.ayvytr.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ayvytr.mvpbase.IPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpActivity;
import com.ayvytr.mvprxlifecycle.RxUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplachActivity extends BaseMvpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        //放在这里因为：放在initData，从锁屏到解锁，会卡到这个页面，不会切换到主页
        Observable.timer(1, TimeUnit.SECONDS)
                  .compose(RxUtils.<Long>subscribeIo(null))
                  .compose(RxUtils.<Long>bindUntilEvent(this, ActivityEvent.STOP))
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(Long aLong) {
                          startActivity(new Intent(getContext(), MainActivity.class));
                          finish();
                      }
                  });
    }

    @Override
    public int getContentViewRes() {
        return 0;
    }
}
