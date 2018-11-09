package com.ayvytr.girl.contract;

import android.content.Intent;
import android.graphics.Bitmap;

import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author admin
 */
public class PhotoViewContract {
    public interface Model extends IModel {
        Observable<ResponseBody> getImage(String url);
    }

    public interface View extends IView {
        void onGotShareIntent(Intent intent);

        void onSettingWallpaper(Bitmap bitmap);
    }
}
