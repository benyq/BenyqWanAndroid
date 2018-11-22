package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.ui.activity.MainActivity
import com.benyq.benyqwanandroid.di.scope.ActivityScope
import com.benyq.benyqwanandroid.ui.activity.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
@Module
abstract class AllActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivityInjector(): LoginActivity
}