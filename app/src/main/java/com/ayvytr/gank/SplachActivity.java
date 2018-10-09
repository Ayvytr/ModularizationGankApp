package com.ayvytr.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ayvytr.mvp.BaseMvpActivity;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.mvp.RxUtils;
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
        Observable.timer(1, TimeUnit.SECONDS)
                  .compose(RxUtils.<Long>applySchedulers(null))
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
