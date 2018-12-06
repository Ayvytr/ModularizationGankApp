package com.ayvytr.commonlibrary;


import com.ayvytr.mvp.BasePresenter;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author admin
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private BasePresenter basePresenter;

    public BaseObserver() {
    }

    /**
     * 委托BasePresenter来处理错误，比如显示错误等.
     * 如果使用无参构造，并且又想处理错误，请重写 {@link #onError(Throwable)} 来自行处理错误
     *
     * @param basePresenter {@link BasePresenter}
     */
    public BaseObserver(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
        if(basePresenter != null) {
            basePresenter.handlerErrorMessage(getStringError(e));
        }
    }

    /**
     * 统一错误处理 -> 汉化了提示，以下错误出现的情况 (ps:不一定百分百按我注释的情况，可能其他情况)
     */
    public static String getStringError(Throwable e) {
        String msg = null;
        if(e instanceof UnknownHostException) {
            //无网络的情况，或者主机挂掉了。返回，对应消息  Unable to resolve host "m.app.haosou.com": No address associated with hostname
            msg = "网络不可用/主机无法识别";
        } else if(e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof SocketException) {
            //连接超时等
            msg = "连接超时";
        } else if(e instanceof HttpException) {
            //HTTP 504 Unsatisfiable Request (only-if-cached)
            //这个错误是没有网络又没有缓存报的异常
            //TODO: 尝试在没有网络的时候就判断是否有缓存，没有缓存直接抛异常终止请求.
            if(((HttpException) e).code() == 504
                    && e.getMessage().equals("HTTP 504 Unsatisfiable Request (only-if-cached)")){
                msg = "网络不可用/主机无法识别";
            }
        } else {
            msg = e.getMessage();
        }
        return msg;
    }

    @Override
    public void onComplete() {
    }
}
