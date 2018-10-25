package com.ayvytr.knowledge.presenter;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.SearchContract;
import com.ayvytr.knowledge.model.SearchModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;


/**
 * @author wangdunwei
 */
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    public SearchPresenter(SearchContract.View view) {
        super(new SearchModel(), view);
    }

    public void search(String key, int currentPage, int pageSize) {
        mModel.search(key, currentPage, pageSize)
              .compose(RxUtils.<BaseGank>applySchedulers(mView))
              .compose(RxUtils.<BaseGank>bindToLifecycle(mView))
              .subscribe(new BaseObserver<BaseGank>() {
                  @Override
                  public void onNext(BaseGank baseGank) {
                      if (baseGank.isError()) {
                          mView.showError(R.string.search_failed);
                      } else {
                         mView.showSearchResult(baseGank.getResults());
                      }
                  }
              });
    }
}
