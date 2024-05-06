package com.tools.talkbacktool

import android.app.Application
import android.content.Context
import com.hm.lifecycle.api.ApplicationLifecycleManager


class App : Application() {

    //全局设置smartrefreshlayout
    companion object {
        lateinit var wdApplicationContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        wdApplicationContext = applicationContext
        ApplicationLifecycleManager.init()
        ApplicationLifecycleManager.onCreate(this)

        //初始化
        InitUtil.init()
    }

    override fun onTerminate() {
        super.onTerminate()
        ApplicationLifecycleManager.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ApplicationLifecycleManager.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        ApplicationLifecycleManager.onTrimMemory(level)
    }


}