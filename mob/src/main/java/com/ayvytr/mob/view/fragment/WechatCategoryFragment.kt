package com.ayvytr.mob.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.commonlibrary.callback.WechatCategoryCallback
import com.ayvytr.commonlibrary.constant.IntentConstant
import com.ayvytr.commonlibrary.constant.MobConstant
import com.ayvytr.mob.R
import com.ayvytr.mob.adapter.WechatCategoryAdapter
import com.ayvytr.mob.contract.WechatCategoryContract
import com.ayvytr.mob.model.WechatCategoryModel
import com.ayvytr.mob.presenter.WechatCategoryPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout

class WechatCategoryFragment : BaseListFragment<WechatCategoryPresenter, WechatCategory.ResultBean>(), WechatCategoryContract.View {

    override fun showWechatCategory(wechatCategory: WechatCategory) {
        updateList(wechatCategory.result, WechatCategoryCallback(mAdapter.datas, wechatCategory.result!!))
    }

    override fun showWechatCategoryFailed(msg: String?) {
        if(msg != null) {
            showMessage(msg)
        }
    }

    override fun getPresenter(): WechatCategoryPresenter {
        return WechatCategoryPresenter(WechatCategoryModel(), this)
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSmartRefreshLayout.setEnableLoadMore(false)
        mSmartRefreshLayout.setEnableAutoLoadMore(false)
        mRvList.layoutManager = LinearLayoutManager(context)
        mAdapter = WechatCategoryAdapter(context!!)
        mRvList.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, holder, position ->
            ARouter.getInstance().build(MobConstant.WECHAT_ARTICLE)
                .withString(IntentConstant.EXTRA_WECHAT_CID, mAdapter.getItemAt(position).cid)
                .withString(IntentConstant.EXTRA_TITLE, mAdapter.getItemAt(position).name)
                .navigation(context)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestWechatCategory()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.requestWechatCategory()
    }

    override fun getContentViewRes(): Int {
        return R.layout.layout_list
    }

}
