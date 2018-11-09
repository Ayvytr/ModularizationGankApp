package com.ayvytr.girl.contract;

import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GirlsContract {
    public interface Model extends IModel {
        Observable<BaseGank> getGankMm(int pageSize, int currentPage);
    }

    public interface View extends IView {
        void showGankMm(BaseGank gank);
    }
}
