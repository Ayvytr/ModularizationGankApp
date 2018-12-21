package com.ayvytr.girl.contract

import android.content.Intent
import android.graphics.Bitmap

import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @author admin
 */
class PhotoViewContract {
    interface Model : IModel {
        fun getImage(url: String): Observable<ResponseBody>
    }

    interface View : IView {
        fun onGotShareIntent(intent: Intent)

        fun onSettingWallpaper(bitmap: Bitmap)
    }
}
