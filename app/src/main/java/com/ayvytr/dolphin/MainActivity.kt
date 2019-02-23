package com.ayvytr.dolphin

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import cn.sharesdk.onekeyshare.OnekeyShare
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.constant.KnowledgeConstant
import com.ayvytr.easykotlin.bitmap.toBitmap
import com.ayvytr.easykotlin.context.getDrawable2
import com.ayvytr.easykotlin.ui.hide
import com.ayvytr.easykotlin.ui.show
import com.ayvytr.girl.view.fragment.GirlsFragment
import com.ayvytr.knowledge.view.fragment.AndroidFragment
import com.ayvytr.knowledge.view.fragment.ClassifyGankFragment
import com.ayvytr.knowledge.view.fragment.GankHistoryFragment
import com.ayvytr.mob.view.fragment.TodayInHistoryFragment
import com.ayvytr.mob.view.fragment.WechatCategoryFragment
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.ayvytr.settings.SettingsFragment
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseMvpActivity<IPresenter>(), NavigationView.OnNavigationItemSelectedListener {

    private var mFragments: Array<Fragment>? = arrayOf(AndroidFragment.newInstance(GankType.ANDROID), GirlsFragment(), SettingsFragment())

    private lateinit var toolbar: Toolbar

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun onBackPressed() {
        if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
            drawer_layout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.menu_search) {
            ARouter.getInstance().build(KnowledgeConstant.SEARCH).navigation(context)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        flContainer.show(id != R.id.nav_home)
        group.show(id == R.id.nav_home)

        if (id == R.id.nav_home) {
            flContainer.hide()
        } else if (id == R.id.nav_gank_history) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.flContainer, GankHistoryFragment())
            ft.commit()
        } else if (id == R.id.nav_gank_of_type) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.flContainer, ClassifyGankFragment())
            ft.commit()
        } else if (id == R.id.nav_today_in_history) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.flContainer, TodayInHistoryFragment())
            ft.commit()
        } else if (id == R.id.nav_wechat_selected) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.flContainer, WechatCategoryFragment())
            ft.commit()
        } else if (id == R.id.nav_share) {
            val oks = OnekeyShare()
            oks.disableSSOWhenAuthorize()
            // title标题，微信、QQ和QQ空间等平台使用
            oks.setTitle(getString(R.string.share_app))
            // titleUrl QQ和QQ空间跳转链接
//            oks.setTitleUrl();
            oks.setImageData(getDrawable2(R.mipmap.ic_launcher)?.toBitmap())
            oks.text = getString(R.string.share_app)
            oks.setUrl(getString(R.string.author_ayvytr_github_url))
            oks.show(getContext())
        }

        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()
        nav_view!!.setNavigationItemSelectedListener(this)

        vp!!.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments!![position]
            }

            override fun getCount(): Int {
                return mFragments!!.size
            }
        }

        bottom_navigation!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nv_android  -> {
                    vp!!.currentItem = 0
                    true
                }
                R.id.nv_girls    -> {
                    vp!!.currentItem = 1
                    true
                }
                R.id.nv_settings -> {
                    vp!!.currentItem = 2
                    true
                }
                else             -> false
            }
        }
        vp!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0    -> bottom_navigation!!.selectedItemId = R.id.nv_android
                    1    -> bottom_navigation!!.selectedItemId = R.id.nv_girls
                    else -> bottom_navigation!!.selectedItemId = R.id.nv_settings
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

//        llHeader = findViewById(R.id.llHeader)
//        llHeader.setOnClickListener {
//            ARouter.getInstance().build(WebConstant.WEB)
//                .withString(WebConstant.EXTRA_TITLE, getString(R.string.author_ayvytr_github))
//                .withString(WebConstant.EXTRA_URL, getString(R.string.author_ayvytr_github_url))
//                .navigation(getContext())
//        }
        requestStoragePermission()
    }

    private fun requestStoragePermission() {
        AndPermission.with(context)
            .runtime()
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .start()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_main
    }

    override fun onDestroy() {
        super.onDestroy()
        bottom_navigation!!.setOnNavigationItemSelectedListener(null)
        vp!!.adapter = null
        mFragments = null
    }
}
