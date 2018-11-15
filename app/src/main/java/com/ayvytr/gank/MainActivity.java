package com.ayvytr.gank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.constant.KnowledgeConstant;
import com.ayvytr.easykotlin.ui.ViewKt;
import com.ayvytr.girl.view.fragment.GirlsFragment;
import com.ayvytr.knowledge.view.fragment.AndroidFragment;
import com.ayvytr.knowledge.view.fragment.ClassifyGankFragment;
import com.ayvytr.knowledge.view.fragment.GankHistoryFragment;
import com.ayvytr.mvpbase.IPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpActivity;
import com.ayvytr.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.flContainer)
    FrameLayout mFlContainer;
    @BindView(R.id.group)
    Group mGroup;

    private Fragment[] mFragments = {
            AndroidFragment.newInstance(GankType.ANDROID),
            new GirlsFragment(),
            new SettingsFragment()
    };

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.menu_search) {
            ARouter.getInstance().build(KnowledgeConstant.SEARCH).navigation(getContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        ViewKt.show(mFlContainer, id != R.id.nav_home);
        ViewKt.show(mGroup, id == R.id.nav_home);

        if(id == R.id.nav_home) {
            ViewKt.hide(mFlContainer);
        } else if(id == R.id.nav_gank_history) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, new GankHistoryFragment());
            ft.commit();
        } else if(id == R.id.nav_gank_of_type) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, new ClassifyGankFragment());
            ft.commit();
        } else if(id == R.id.nav_manage) {

        } else if(id == R.id.nav_share) {

        } else if(id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        });

        mBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.nv_android:
                                mVp.setCurrentItem(0);
                                return true;
                            case R.id.nv_girls:
                                mVp.setCurrentItem(1);
                                return true;
                            case R.id.nv_settings:
                                mVp.setCurrentItem(2);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        mBottomNavigation.setSelectedItemId(R.id.nv_android);
                        break;
                    case 1:
                        mBottomNavigation.setSelectedItemId(R.id.nv_girls);
                        break;
                    default:
                        mBottomNavigation.setSelectedItemId(R.id.nv_settings);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBottomNavigation.setOnNavigationItemSelectedListener(null);
        mVp.setAdapter(null);
        mFragments = null;
    }
}
