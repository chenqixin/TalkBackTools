package com.tools.talkbacktool

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.changjia.crashreport.CJCrashReport
import com.changjia.sv_service.CJVideoManage
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cj.common.http.service.HttpManage
import com.cj.common.log.CJLog
import com.cj.common.log.CJLogManager
import com.cj.poputil.CJLoading
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
/**
 * 日期: 2022/4/20
 * 作者: feiyu
 * 描述:
 * 版本:
 */
object InitUtil {

    /**
     * 初始化
     */
    fun init() {
        initBeforeAuth()
    }

    /**
     * 授权前初始化
     */
    fun initBeforeAuth() {
        initCJLog()
    }




    fun initCJLog() {
        if (BuildConfig.DEBUG) {
            CJLogManager.setCjLogConfig("TalkBack日志", true)
        } else {
            CJLogManager.setCjLogConfig("TalkBack日志", false)
        }
    }


    /**
     * 初始化腾讯短视频
     */
    fun initTxShortVideo() {
        CJVideoManage.init(
            App.wdApplicationContext,
            "https://license.vod2.myqcloud.com/license/v2/1308393449_1/v_cube.license",
            "86627637c99cb57a1e379ca7da9beb05",
            BuildConfig.DEBUG
        )
    }

    /**
     * 初始化tbsSdk
     */
    fun initTBSSdk() {
        QbSdk.setDownloadWithoutWifi(true)
        val map = mutableMapOf<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        val cb: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                CJLog.e("onViewInitFinished: $arg0")
            }

            override fun onCoreInitFinished() {
                CJLog.e("onCoreInitFinished")
            }
        }
        QbSdk.initX5Environment(App.wdApplicationContext, cb)
    }
}