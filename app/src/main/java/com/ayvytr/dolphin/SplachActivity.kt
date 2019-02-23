package com.ayvytr.dolphin

import android.content.Intent
import android.os.Bundle
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.ayvytr.rxlifecycle.RxUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplachActivity : BaseMvpActivity<IPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        //放在这里因为：放在initData，从锁屏到解锁，会卡到这个页面，不会切换到主页
        Observable.timer(1, TimeUnit.SECONDS)
            .compose(RxUtils.ofDefault(null))
            .compose(RxUtils.bindUntilEvent(this, ActivityEvent.STOP))
            .subscribe {
                startActivity(Intent(context, MainActivity::class.java))
                finish()
            }
    }

    override fun getContentViewRes(): Int {
        return 0
    }
}
