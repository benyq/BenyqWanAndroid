package com.benyq.benyqwanandroid.di.component

import com.benyq.benyqwanandroid.app.App
import com.benyq.benyqwanandroid.di.module.AllActivitiesModule
import com.benyq.benyqwanandroid.di.module.AllFragmentsModule
import com.benyq.benyqwanandroid.di.module.ApiModule

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author benyq
 * @e-mail 1520063035@qq.com
 * @Date 2018/11/21
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AllActivitiesModule::class,
    ApiModule::class,
    AllFragmentsModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent{
    fun inject(app: App)
}
