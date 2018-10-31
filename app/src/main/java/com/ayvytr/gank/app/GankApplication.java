package com.ayvytr.gank.app;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.Env;
import com.ayvytr.logger.L;
import com.ayvytr.network.ApiClient;
import com.github.moduth.blockcanary.BlockCanary;
import com.maning.librarycrashmonitor.MCrashMonitor;
import com.maning.librarycrashmonitor.listener.MCrashCallBack;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

/**
 * @author admin
 */
public class GankApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        Env.setDebug(true);
        MCrashMonitor.init(this, Env.isDebug(), new MCrashCallBack() {
            @Override
            public void onCrash(File file) {
                //可以在这里保存标识，下次再次进入把日志发送给服务器
            }
        });

        initArouter();
        L.settings().showLog(Env.isDebug());

        ApiClient.getInstance()
                 .init(getApplicationContext(), Env.BASE_URL, new ChuckInterceptor(getApplicationContext()));
    }

    private void initArouter() {
        if(Env.isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }


}
