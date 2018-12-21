package com.ayvytr.girl.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.IntentUtil
import com.ayvytr.girl.contract.PhotoViewContract
import com.ayvytr.girl.model.PhotoViewModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils
import com.yanzhenjie.permission.AndPermission
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * @author admin
 */
class PhotoViewPresenter(view: PhotoViewContract.View) :
    BasePresenter<PhotoViewContract.Model, PhotoViewContract.View>(PhotoViewModel(), view) {

    fun savePhoto(url: String, packageName: String, isSettingWallpaper: Boolean) {
        mModel.getImage(url)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .subscribe(object : BaseObserver<ResponseBody>() {
                override fun onNext(responseBody: ResponseBody) {
                    performSavePhoto(url, responseBody, packageName, isSettingWallpaper)
                }
            })
    }

    private fun performSavePhoto(url: String, responseBody: ResponseBody, packageName: String,
                                 isSettingWallpaper: Boolean) {
        val path = getPhotoSavePath(packageName)
        Observable.just(path)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .map { file ->
                if (!file.exists()) {
                    file.mkdirs()
                }

                val photo = getPhotoFile(file, url)
                if (photo.exists() && !isSettingWallpaper) {
                    throw Exception("文件已存在，无需保存")
                } else {
                    photo.createNewFile()
                }

                photo
            }
            .map { file ->
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(file)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

                val bitmap = BitmapFactory.decodeStream(responseBody.byteStream())
                val succeed = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                if (!succeed) {
                    throw Exception("读取图片错误")
                }

                fos!!.close()
                responseBody.close()
                bitmap
            }
            .subscribe(object : BaseObserver<Bitmap>(this@PhotoViewPresenter) {
                override fun onNext(bitmap: Bitmap) {
                    if (isSettingWallpaper) {
                        mView.onSettingWallpaper(bitmap)
                    } else {
                        mView.showError(path.absolutePath)
                    }
                }
            })
    }

    private fun getPhotoFile(file: File, url: String): File {
        return File(file, url.substring(url.lastIndexOf("/")))
    }

    private fun getPhotoFile(url: String, packageName: String): File {
        return File(File(Environment.getExternalStorageDirectory(), "$packageName/photos"),
                    url.substring(url.lastIndexOf("/")))
    }

    private fun getPhotoSavePath(packageName: String): File {
        return File(Environment.getExternalStorageDirectory(), "$packageName/photos")
    }

    fun getShareIntent(url: String, packageName: String, context: Context) {
        val photoFile = getPhotoFile(url, packageName)
        Observable.just(photoFile)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .map { file ->
                val intent: Intent
                if (file.exists()) {
                    val uri = AndPermission.getFileUri(context, file)
                    intent = IntentUtil.getShareImageIntent("分享到", url, uri)
                } else {
                    intent = IntentUtil.getShareTextIntent("分享到", url)
                }

                intent
            }.subscribe(object : BaseObserver<Intent>(this@PhotoViewPresenter) {
                override fun onNext(intent: Intent) {
                    mView.onGotShareIntent(intent)
                }
            })

    }
}
