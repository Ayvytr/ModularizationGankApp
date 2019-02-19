package com.ayvytr.mob.view.fragment

import android.os.Bundle
import com.ayvytr.mob.R
import com.ayvytr.mob.contract.WechatArticleContract
import com.ayvytr.mob.model.WechatArticleModel
import com.ayvytr.mob.presenter.WechatArticlePresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment

class WechatArticleFragment : BaseMvpFragment<WechatArticlePresenter>(), WechatArticleContract.View {

    override fun getPresenter(): WechatArticlePresenter {
        return WechatArticlePresenter(WechatArticleModel(), this)
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_wechat_article
    }
}
