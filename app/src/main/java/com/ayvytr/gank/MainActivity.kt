package com.ayvytr.gank

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
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.constant.KnowledgeConstant
import com.ayvytr.easykotlin.ui.hide
import com.ayvytr.easykotlin.ui.show
import com.ayvytr.girl.view.fragment.GirlsFragment
import com.ayvytr.knowledge.view.fragment.AndroidFragment
import com.ayvytr.knowledge.view.fragment.ClassifyGankFragment
import com.ayvytr.knowledge.view.fragment.GankHistoryFragment
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.ayvytr.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseMvpActivity<IPresenter>(), NavigationView.OnNavigationItemSelectedListener {

    private var mFragments: Array<Fragment>? = arrayOf(AndroidFragment.newInstance(GankType.ANDROID), GirlsFragment(), SettingsFragment())

    private lateinit var toolbar:Toolbar

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
        } else if (id == R.id.nav_more) {
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
