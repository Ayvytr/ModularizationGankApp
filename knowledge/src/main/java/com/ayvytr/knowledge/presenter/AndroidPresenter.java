package com.ayvytr.knowledge.presenter;


import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.model.AndroidModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;
import com.ayvytr.commonlibrary.BaseObserver;

/**
 * @author admin
 */
public class AndroidPresenter extends BasePresenter<AndroidContract.Model, AndroidContract.View> {
    public AndroidPresenter(AndroidContract.View rootView) {
        super(new AndroidModel(), rootView);
    }

    public void requestGankByType(String gankType, int pageSize, int currentPage) {
        mModel.getGankByType(gankType, pageSize, currentPage)
              .compose(RxUtils.<BaseGank>applySchedulers(mView))
              .compose(RxUtils.<BaseGank>bindToLifecycle(mView))
              .subscribe(new BaseObserver<BaseGank>(this) {
                  @Override
                  public void onNext(BaseGank gank) {
                      if(gank.isError()) {
                          mView.showError(R.string.request_data_error);
                      } else {
                          mView.showGank(gank);
                      }
                  }
              });
    }
}
