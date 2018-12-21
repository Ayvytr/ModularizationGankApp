package com.ayvytr.knowledge.view.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baseadapter.MultiItemTypeAdapter
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.bean.GankHistoryContent
import com.ayvytr.commonlibrary.constant.KnowledgeConstant
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.easykotlin.context.getScreenWidth
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.GankHistoryContentAdapter
import com.ayvytr.knowledge.contract.GankHistoryContentContract
import com.ayvytr.knowledge.presenter.GankHistoryContentPresenter
import com.ayvytr.logger.L
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.ayvytr.rxlifecycle.RxUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_gank_history_content.*
import java.util.*

@Route(path = KnowledgeConstant.GANK_HISTORY_CONTENT)
class GankHistoryContentActivity : BaseMvpActivity<GankHistoryContentPresenter>(), GankHistoryContentContract.View {

    private lateinit var toolbar: Toolbar

    private var date: String? = null
    private lateinit var mContentAdapter: GankHistoryContentAdapter

    override fun getPresenter(): GankHistoryContentPresenter {
        return GankHistoryContentPresenter(this)
    }

    override fun initExtra() {
        date = intent.getStringExtra(KnowledgeConstant.EXTRA_DATE)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
            val lp = toolbar.layoutParams as ViewGroup.MarginLayoutParams

            var statusBarHeight = 0
            //获取status_bar_height资源的ID
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = resources.getDimensionPixelSize(resourceId)
            }
            lp.topMargin = statusBarHeight
        }
        ct_layout!!.title = date
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }
        rv_list.layoutManager = LinearLayoutManager(context)
        mContentAdapter = GankHistoryContentAdapter(context)
        mContentAdapter.setOnItemClickListener(MultiItemTypeAdapter.OnItemClickListener { view, holder, position ->
            val gank = mContentAdapter.getItemAt(position)
            if (gank.isHeader) {
                return@OnItemClickListener
            }

            ARouter.getInstance().build(WebConstant.WEBVIEW)
                .withString(WebConstant.EXTRA_URL, gank.url)
                .withString(WebConstant.EXTRA_TITLE, gank.desc)
                .navigation(context)
        })
        rv_list!!.adapter = mContentAdapter

        status_view!!.showLoading()
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(date)) {
            showError(R.string.no_gank_date)
        }
        mPresenter.requestGankByDate(date!!)
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_gank_history_content
    }

    override fun showGankByDate(gankHistoryContent: GankHistoryContent) {
        status_view!!.showContent()
        val results = gankHistoryContent.results
        L.e(results)
        val girls = results!![GankType.GIRLS.toString()]
        showHeaderImage(girls!!.toMutableList())
        results.remove(GankType.GIRLS.toString())
        showContent(results)
    }

    private fun showContent(results: HashMap<String, List<Gank>>) {
        Observable.just(results)
            .compose(RxUtils.ofDefault(this))
            .compose(RxUtils.bindToLifecycle(this))
            .map { map ->
                val iterator = map.keys.iterator()
                val list = ArrayList<Gank>()
                while (iterator.hasNext()) {
                    val ganks = map[iterator.next()]
                    if (ganks!!.isNotEmpty()) {
                        list.add(Gank(ganks.get(0).type!!, true))
                        list.addAll(ganks)
                    }
                }

                list
            }
            .subscribe { ganks -> mContentAdapter.updateList(ganks) }
    }

    private fun showHeaderImage(girls: List<Gank>) {
        Glide.with(context)
            .asBitmap()
            .load(girls[0].url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>,
                                          isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>,
                                             dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    val height = context.getScreenWidth() * resource.height / resource
                        .width
                    val lp = iv_header!!.layoutParams
                    lp.height = height
                    return false
                }
            })
            .into(iv_header!!)
    }
}
