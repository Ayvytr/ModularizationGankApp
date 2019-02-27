package com.ayvytr.girl.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.constant.GirlsConstant
import com.ayvytr.easykotlin.context.getWallpaperManager
import com.ayvytr.easykotlin.ui.hideActionBar
import com.ayvytr.easykotlin.ui.showActionBar
import com.ayvytr.girl.R
import com.ayvytr.girl.contract.PhotoViewContract
import com.ayvytr.girl.presenter.PhotoViewPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.PhotoView
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_photo_view.*
import java.io.IOException

/**
 * 图片浏览Activity.
 *
 * @author admin
 */
@Route(path = GirlsConstant.PHOTO_VIEW)
class PhotoViewActivity : BaseMvpActivity<PhotoViewPresenter>(), PhotoViewContract.View {

    internal lateinit var toolbar: Toolbar

    private lateinit var vpAdapter: PagerAdapter

    private var mList: MutableList<Gank>? = null

    private var mPosition: Int = 0
    private var mBitmap: Bitmap? = null

    override fun getPresenter(): PhotoViewPresenter {
        return PhotoViewPresenter(this)
    }

    override fun initExtra() {
        mList = intent.getParcelableArrayListExtra(GirlsConstant.EXTRA_PHOTOS)
        mPosition = intent.getIntExtra(GirlsConstant.EXTRA_PHOTO_POSITION, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = GankType.GIRLS.toString()
        if (mList == null) {
            showError(R.string.no_photos)
            return
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }

        initViewPager()

        activity.hideActionBar()
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    private fun setToDesktop() {
        if (!AndPermission.hasPermissions(context, Permission.WRITE_EXTERNAL_STORAGE)) {
            requestStoragePermission(true)
        } else {
            mPresenter.savePhoto(mList!![mPosition].url!!, packageName, true)
        }
    }

    private fun sharePhoto() {
        mPresenter.getShareIntent(mList!![mPosition].url!!, packageName, context)
    }

    private fun savePhoto() {
        if (mBitmap == null) {
            showError(R.string.no_photo_loaded)
            return
        }
        if (!AndPermission.hasPermissions(context, Permission.WRITE_EXTERNAL_STORAGE)) {
            requestStoragePermission(false)
        } else {
            mPresenter.savePhoto(mList!![mPosition].url!!, packageName, false)
        }
    }


    private fun initViewPager() {
        vpAdapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return mList!!.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val photoView = PhotoView(context)
                photoView.setOnClickListener {
                    if (supportActionBar!!.isShowing) {
                        activity.hideActionBar()
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    } else {
                        activity.showActionBar()
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    }
                }
                photoView.setOnLongClickListener { v ->
                    openContextMenu(v)
                    true
                }
                Glide.with(context)
                    .asBitmap()
                    .load(mList!![position].url)
                    .into(object : BitmapImageViewTarget(photoView) {

                        override fun onResourceReady(resource: Bitmap,
                                                     transition: Transition<in Bitmap>?) {
                            mBitmap = resource
                            super.onResourceReady(resource, transition)
                        }
                    })
                container.addView(photoView)
                return photoView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        vp.adapter = vpAdapter
        vp.currentItem = mPosition
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                mPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        registerForContextMenu(vp)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_photo_view
    }

    private fun requestStoragePermission(isSettingWallpaper: Boolean) {
        AndPermission.with(context)
            .runtime()
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .onDenied {
                Toast.makeText(this@PhotoViewActivity, R.string.cannot_get_storage_permission,
                               Toast.LENGTH_SHORT).show()
            }
            .onGranted { mPresenter.savePhoto(mList!![mPosition].url!!, packageName, isSettingWallpaper) }.start()
    }

    override fun onGotShareIntent(intent: Intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.share_to)))
    }

    override fun onSettingWallpaper(bitmap: Bitmap) {
        val wallpaperManager = context.getWallpaperManager()
        try {
            wallpaperManager.setBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        showError(R.string.wallpaper_changed)
    }

    override fun onDestroy() {
        super.onDestroy()
        vp.adapter = null
        mList!!.clear()
        mList = null
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.setHeaderTitle(R.string.you_want_dot)
        menu?.setHeaderIcon(android.R.drawable.ic_menu_send)
        menu?.add(0, 1, 0, R.string.save_photo)
        menu?.add(0, 2, 0, R.string.set_to_desktop_background)
        menu?.add(0, 3, 0, R.string.share_to)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1    -> {
                savePhoto()
                return true
            }
            2    -> {
                setToDesktop()
                return true
            }
            3    -> {
                sharePhoto()
                return true
            }
            else -> {
            }
        }
        return super.onContextItemSelected(item)
    }
}
