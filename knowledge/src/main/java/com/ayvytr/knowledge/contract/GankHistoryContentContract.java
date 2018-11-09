package com.ayvytr.knowledge.contract;

import com.ayvytr.commonlibrary.bean.GankHistoryContent;
import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GankHistoryContentContract {
    public interface Model extends IModel {
        Observable<GankHistoryContent> getGankByDate(String year, String month, String dayOfMonth);
    }

    public interface View extends IView {
        void showGankByDate(GankHistoryContent gankHistoryContent);
    }
}
