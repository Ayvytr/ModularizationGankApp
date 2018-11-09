package com.ayvytr.knowledge.contract;

import com.ayvytr.commonlibrary.bean.GankHistory;
import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

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
