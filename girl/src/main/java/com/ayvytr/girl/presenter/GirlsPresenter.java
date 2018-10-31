package com.ayvytr.girl.presenter;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.girl.R;
import com.ayvytr.girl.contract.GirlsContract;
import com.ayvytr.girl.model.GirlsModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;

/**
 * @author admin
 */
public class GirlsPresenter extends BasePresenter<GirlsContract.Model, GirlsContract.View> {
    public GirlsPresenter(GirlsContract.View view) {
        super(new GirlsModel(), view);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    public void requestGankMm(int pageSize, int currentPage) {
        mModel.getGankMm(pageSize, currentPage)
              .compose(RxUtils.<BaseGank>subscribeIo(mView))
              .compose(RxUtils.<BaseGank>bindToLifecycle(mView))
              .subscribe(new BaseObserver<BaseGank>(this) {
                  @Override
                  public void onNext(BaseGank gank) {
                      if(gank.isError()) {
                          mView.showError(R.string.request_data_error);
                      } else {
                          mView.showGankMm(gank);
                      }
                  }
              });
    }
}
