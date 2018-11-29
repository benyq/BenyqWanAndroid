package com.benyq.benyqwanandroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.BuildConfig
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.*
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/20
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DaggerAppComponent.create().inject(this)
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                ActivityManager.removeActivity(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActivityManager.addActivity(activity)
            }

        })
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return mAndroidInjector
    }



}