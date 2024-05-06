package com.tools.talkbacktool

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.changjia.commonbusiness.base.BaseActivity
import com.cj.poputil.CJToast
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import com.zackratos.ultimatebarx.ultimatebarx.bean.BarBackground
import com.zackratos.ultimatebarx.ultimatebarx.bean.BarConfig

/**
日期 :2023/12/16
作者:ChenQiXin
描述：
版本：
 */
abstract class BaseActivity <VB : ViewBinding> : BaseActivity<VB>(){

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        //禁止横屏
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//部分机型不起作用，如：Redmi Note 8 Pro
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            val intentAction = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null
                && intentAction == Intent.ACTION_MAIN
            ) {
                finish()
                return
            }
        }

        val background = BarBackground.newInstance()
            .colorRes(getStatusColor())
        val config = BarConfig.newInstance()
            .fitWindow(isFitWindow())
            .background(background)
            .light(isStatusLight())
        UltimateBarX.with(this)
            .config(config)
            .applyStatusBar()

    }

    open fun networkError() {}

    open fun getStatusColor(): Int {
        return R.color.white
    }

    open fun isStatusLight(): Boolean {
        return true
    }

    open fun isFitWindow(): Boolean {
        return true
    }

    override fun toLogin() {
        super.toLogin()
        finish()
    }

    //设置字体为默认大小，不随系统字体大小改而改变
    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1.0f) //非默认值
            resources
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res: Resources = super.getResources()
        if (res.configuration.fontScale != 1.0f) { //非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults() //设置默认
            createConfigurationContext(newConfig)
        }
        return res
    }

    /**
     * Activity是否已被销毁
     * @return
     */
    fun isActivityEnable(): Boolean {
        if (isDestroyed || isFinishing) {
            return false
        }
        return true
    }

    override fun showToast(msg: String) {
        CJToast.showCenterToast(msg)
    }
}