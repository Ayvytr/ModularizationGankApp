package com.ayvytr.commonlibrary.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author admin
 */
public class LoadingView extends RelativeLayout {
    public static final int NONE = 0;
    public static final int LOADING = 1;
    public static final int ERROR = 2;
    public static final int EMPTY = 3;

    @IntDef({LOADING, ERROR, EMPTY, NONE})
    private @interface Status {}

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showLoading() {
        showLoading(null);
    }

    private void showLoading(String msg) {
    }


    public void showError() {
        showError(null);
    }

    private void showError(String msg) {

    }

    public void showEmpty() {
        showEmpty(null);
    }

    private void showEmpty(String msg) {
    }
}
