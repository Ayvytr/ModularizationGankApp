package com.ayvytr.gank.app

import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.Env
import com.ayvytr.commonlibrary.client.MobClient
import com.ayvytr.logger.L
import com.ayvytr.network.ApiClient
import com.maning.librarycrashmonitor.MCrashMonitor
import com.mob.MobSDK
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport

/**
 * @author admin
 */
class GankApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        CrashReport.initCrashReport(applicationContext, "1107970668", false)

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        //        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        initCrashMonitor()

        initArouter()
        L.settings().showLog(Env.isDebug)

        ApiClient.getInstance()
            .init(applicationContext, Env.BASE_URL, ChuckInterceptor(applicationContext))

        MobClient.getInstance()
            .init(applicationContext, Env.MOB_BASE_URL, ChuckInterceptor(applicationContext))
        MobSDK.init(this)
    }

    private fun initCrashMonitor() {
        MCrashMonitor.init(this, Env.isDebug) {
            //可以在这里保存标识，下次再次进入把日志发送给服务器
        }
    }

    private fun initArouter() {
        if (Env.isDebug) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this) // As early as possible, it is recommended to initialize in the Application
    }


}
