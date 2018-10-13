package com.ayvytr.knowledge.contract;

import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class AndroidContract {
    public interface Model extends IModel {
        Observable<BaseGank> getGankByType(String gankType, int pageSize, int currentPage);
    }

    public interface View extends IView {
        void showGank(BaseGank gank);
    }
}
