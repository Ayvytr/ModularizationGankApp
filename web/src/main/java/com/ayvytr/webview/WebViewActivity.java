package com.ayvytr.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.mvp.BaseMvpActivity;
import com.ayvytr.mvp.IPresenter;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

@Route(path = WebConstant.WEBVIEW)
public class WebViewActivity extends BaseMvpActivity {
    @BindView(R2.id.flContainer)
    FrameLayout mFlContainer;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    private AgentWeb mAgentWeb;
    private String mUrl;
    private String mTitle;
    private boolean mUseWebTitle;

    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    private void initAgentWeb() {
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AgentWeb goBack不起作用
                finish();
            }
        });
        mWebViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
        mWebChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(mUseWebTitle) {
                    mToolbar.setTitle(title);
                }
            }
        };
        mAgentWeb = AgentWeb.with(this)
                            .setAgentWebParent(mFlContainer,
                                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                            FrameLayout.LayoutParams.MATCH_PARENT))
                            .useDefaultIndicator()
                            .setWebViewClient(mWebViewClient)
                            .setWebChromeClient(mWebChromeClient)
                            .createAgentWeb()
                            .ready()
                            .go(TextUtils.isEmpty(mUrl) ? "" : mUrl);
    }

    @Override
    public void initExtra() {
        mUrl = getIntent().getStringExtra(WebConstant.EXTRA_URL);
        mTitle = getIntent().getStringExtra(WebConstant.EXTRA_TITLE);
        mUseWebTitle = getIntent().getBooleanExtra(WebConstant.EXTRA_USE_WEB_TITLE, true);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        initAgentWeb();
        setSupportActionBar(mToolbar);
        if(!mUseWebTitle) {
            mToolbar.setTitle(mTitle);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
