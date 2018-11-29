package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.di.scope.FragmentScope
import com.benyq.benyqwanandroid.ui.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
@Module
abstract class AllFragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeHomeFragmentInjector(): HomeFragment

}