package com.ayvytr.knowledge.contract;

import com.ayvytr.commonlibrary.bean.GankHistory;
import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GankHistoryContract {
    public interface Model extends IModel {
        Observable<GankHistory> getGankHistory();
    }

    public interface View extends IView {
        void showGankHistory(List<String> results);
    }
}