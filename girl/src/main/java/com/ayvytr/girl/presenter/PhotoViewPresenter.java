package com.ayvytr.girl.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.IntentUtil;
import com.ayvytr.girl.contract.PhotoViewContract;
import com.ayvytr.girl.model.PhotoViewModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @author admin
 */
public class PhotoViewPresenter extends BasePresenter<PhotoViewContract.Model, PhotoViewContract.View> {
    public PhotoViewPresenter(PhotoViewContract.View view) {
        super(new PhotoViewModel(), view);
    }

    public void savePhoto(final String url, final String packageName, final boolean isSettingWallpaper) {
        mModel.getImage(url)
              .compose(RxUtils.<ResponseBody>subscribeIo(mView))
              .compose(RxUtils.<ResponseBody>bindToLifecycle(mView))
              .subscribe(new BaseObserver<ResponseBody>() {
                  @Override
                  public void onNext(ResponseBody responseBody) {
                      performSavePhoto(url, responseBody, packageName, isSettingWallpaper);
                  }
              });
    }

    private void performSavePhoto(final String url, final ResponseBody responseBody, String packageName,
                                  final boolean isSettingWallpaper) {
        final File path = getPhotoSavePath(packageName);
        Observable.just(path)
                  .compose(RxUtils.<File>subscribeIo(mView))
                  .compose(RxUtils.<File>bindToLifecycle(mView))
                  .map(new Function<File, File>() {
                      @Override
                      public File apply(File file) throws Exception {
                          if(!file.exists()) {
                              file.mkdirs();
                          }

                          File photo = getPhotoFile(file, url);
                          if(photo.exists() && !isSettingWallpaper) {
                              throw new Exception("文件已存在，无需保存");
                          } else {
                              photo.createNewFile();
                          }

                          return photo;
                      }
                  })
                  .map(new Function<File, Bitmap>() {
                      @Override
                      public Bitmap apply(File file) throws Exception {
                          FileOutputStream fos = null;
                          try {
                              fos = new FileOutputStream(file);
                          } catch(FileNotFoundException e) {
                              e.printStackTrace();
                          }
                          Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                          boolean succeed = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                          if(!succeed) {
                              throw new Exception("读取图片错误");
                          }

                          fos.close();
                          responseBody.close();
                          return bitmap;
                      }
                  })
                  .subscribe(new BaseObserver<Bitmap>(this) {
                      @Override
                      public void onNext(Bitmap bitmap) {
                          if(isSettingWallpaper) {
                              mView.onSettingWallpaper(bitmap);
                          } else {
                              mView.showError(path.getAbsolutePath());
                          }
                      }
                  });
    }

    @NonNull
    private File getPhotoFile(File file, String url) {
        return new File(file, url.substring(url.lastIndexOf("/")));
    }

    @NonNull
    private File getPhotoFile(String url, String packageName) {
        return new File(new File(Environment.getExternalStorageDirectory(), packageName + "/photos"),
                url.substring(url.lastIndexOf("/")));
    }

    @NonNull
    private File getPhotoSavePath(String packageName) {
        return new File(Environment.getExternalStorageDirectory(), packageName + "/photos");
    }

    public void getShareIntent(final String url, String packageName, final Context context) {
        File photoFile = getPhotoFile(url, packageName);
        Observable.just(photoFile)
                  .compose(RxUtils.<File>subscribeIo(mView))
                  .compose(RxUtils.<File>bindToLifecycle(mView))
                  .map(new Function<File, Intent>() {
                      @Override
                      public Intent apply(File file) {
                          Intent intent;
                          if(file.exists()) {
                              Uri uri = AndPermission.getFileUri(context, file);
                              intent = IntentUtil.getShareImageIntent("分享到", url, uri);
                          } else {
                              intent = IntentUtil.getShareTextIntent("分享到", url);
                          }

                          return intent;
                      }
                  }).subscribe(new BaseObserver<Intent>(this) {
            @Override
            public void onNext(Intent intent) {
                mView.onGotShareIntent(intent);
            }
        });

    }
}
