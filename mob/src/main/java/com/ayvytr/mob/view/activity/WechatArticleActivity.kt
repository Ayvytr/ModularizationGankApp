package com.ayvytr.mob.view.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListActivity
import com.ayvytr.commonlibrary.bean.WechatArticle
import com.ayvytr.commonlibrary.constant.IntentConstant
import com.ayvytr.commonlibrary.constant.MobConstant
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.mob.R
import com.ayvytr.mob.adapter.WechatArticleAdapter
import com.ayvytr.mob.contract.WechatArticleContract
import com.ayvytr.mob.model.WechatArticleModel
import com.ayvytr.mob.presenter.WechatArticlePresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout

@Route(path = MobConstant.WECHAT_ARTICLE)
class WechatArticleActivity : BaseListActivity<WechatArticlePresenter, WechatArticle.ResultBean.ListBean>(),
    WechatArticleContract.View {
    private lateinit var mCid: String
    private lateinit var mTitle: String

    private lateinit var toolbar:Toolbar

    override fun getContentViewRes(): Int {
        return R.layout.activity_wechat_article
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestWechatArticle(mCid, currentPage, pageSize)
    }

    override fun getPresenter(): WechatArticlePresenter {
        return WechatArticlePresenter(WechatArticleModel(), this)
    }

    override fun initExtra() {
        mCid = intent.getStringExtra(IntentConstant.EXTRA_WECHAT_CID)
        mTitle = intent.getStringExtra(IntentConstant.EXTRA_TITLE)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.title = mTitle

        mSmartRefreshLayout.setEnableAutoLoadMore(false)
        mRvList.layoutManager = LinearLayoutManager(getContext())
        mAdapter = WechatArticleAdapter(context)
        mRvList.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, holder, position ->
            val bean = mAdapter.getItemAt(position)
            ARouter.getInstance().build(WebConstant.WEB)
                .withString(WebConstant.EXTRA_TITLE, bean.title)
                .withString(WebConstant.EXTRA_URL, bean.sourceUrl)
                .navigation(getContext())
        }
    }

    override fun showWechatArticle(t: WechatArticle) {
        currentPage = t.result?.curPage ?: 1
        updateList(t.result!!.list)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        mPresenter.requestWechatArticle(mCid, currentPage, pageSize)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        mPresenter.requestWechatArticle(mCid, currentPage + 1, pageSize)
    }
}
