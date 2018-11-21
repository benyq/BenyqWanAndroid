package com.benyq.benyqwanandroid.app

import android.app.Application
import android.content.Context

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/20
 */
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}