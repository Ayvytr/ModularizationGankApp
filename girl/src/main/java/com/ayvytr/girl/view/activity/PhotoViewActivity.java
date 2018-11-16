package com.ayvytr.girl.view.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.GirlsConstant;
import com.ayvytr.easykotlin.context.ManagerKt;
import com.ayvytr.easykotlin.ui.ActivityKt;
import com.ayvytr.girl.R;
import com.ayvytr.girl.R2;
import com.ayvytr.girl.contract.PhotoViewContract;
import com.ayvytr.girl.presenter.PhotoViewPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片浏览Activity.
 *
 * @author admin
 */
@Route(path = GirlsConstant.PHOTO_VIEW)
public class PhotoViewActivity extends BaseMvpActivity<PhotoViewPresenter> implements PhotoViewContract.View {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.vp)
    ViewPager mVp;

    private List<Gank> mList;

    private int mPosition;
    private Bitmap mBitmap;

    @Override
    protected PhotoViewPresenter getPresenter() {
        return new PhotoViewPresenter(this);
    }

    @Override
    public void initExtra() {
        mList = getIntent().getParcelableArrayListExtra(GirlsConstant.EXTRA_PHOTOS);
        mPosition = getIntent().getIntExtra(GirlsConstant.EXTRA_PHOTO_POSITION, 0);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(GankType.GIRLS.toString());
        if(mList == null) {
            showError(R.string.no_photos);
            return;
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViewPager();

        ActivityKt.hideActionBar(getActivity());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void setToDesktop() {
        if(!AndPermission.hasPermissions(getContext(), Permission.WRITE_EXTERNAL_STORAGE)) {
            requestStoragePermission(true);
        } else {
            mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), true);
        }
    }

    private void sharePhoto() {
        mPresenter.getShareIntent(mList.get(mPosition).getUrl(), getPackageName(), getContext());
    }

    private void savePhoto() {
        if(mBitmap == null) {
            showError(R.string.no_photo_loaded);
            return;
        }
        if(!AndPermission.hasPermissions(getContext(), Permission.WRITE_EXTERNAL_STORAGE)) {
            requestStoragePermission(false);
        } else {
            mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), false);
        }
    }

    private void initViewPager() {
        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(getContext());
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getSupportActionBar().isShowing()) {
                            ActivityKt.hideActionBar(getActivity());
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        } else {
                            ActivityKt.showActionBar(getActivity());
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        }
                    }
                });
                photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        openContextMenu(v);
                        return true;
                    }
                });
                Glide.with(getContext())
                     .asBitmap()
                     .load(mList.get(position).getUrl())
                     .into(new BitmapImageViewTarget(photoView) {

                         @Override
                         public void onResourceReady(@NonNull Bitmap resource,
                                                     @Nullable Transition<? super Bitmap> transition) {
                             mBitmap = resource;
                             super.onResourceReady(resource, transition);
                         }
                     });
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        mVp.setCurrentItem(mPosition);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        registerForContextMenu(mVp);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_photo_view;
    }

    private void requestStoragePermission(final boolean isSettingWallpaper) {
        AndPermission.with(getContext())
                     .runtime()
                     .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                     .onDenied(new Action<List<String>>() {
                         @Override
                         public void onAction(List<String> data) {
                             Toast.makeText(PhotoViewActivity.this, R.string.cannot_get_storage_permission,
                                     Toast.LENGTH_SHORT).show();
                         }
                     })
                     .onGranted(new Action<List<String>>() {
                         @Override
                         public void onAction(List<String> data) {
                             mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), isSettingWallpaper);
                         }
                     }).start();
    }

    @Override
    public void onGotShareIntent(Intent intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.share_to)));
    }

    @Override
    public void onSettingWallpaper(final Bitmap bitmap) {
        WallpaperManager wallpaperManager = ManagerKt.getWallpaperManager(getContext());
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch(IOException e) {
            e.printStackTrace();
        }

        showError(R.string.wallpaper_changed);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVp.setAdapter(null);
        mList.clear();
        mList = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.you_want_dot);
        menu.setHeaderIcon(android.R.drawable.ic_menu_send);
        menu.add(0, 1, 0, R.string.save_photo);
        menu.add(0, 2, 0, R.string.set_to_desktop_background);
        menu.add(0, 3, 0, R.string.share_to);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                savePhoto();
                return true;
            case 2:
                setToDesktop();
                return true;
            case 3:
                sharePhoto();
                return true;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
