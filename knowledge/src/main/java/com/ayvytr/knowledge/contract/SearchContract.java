package com.ayvytr.knowledge.contract;

import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author wangdunwei
 */
public class SearchContract {
    public interface Model extends IModel {
        Observable<BaseGank> search(String key, int currentPage, int pageSize);
    }

    public interface View extends IView {
        void showSearchResult(List<Gank> results);
    }
}
