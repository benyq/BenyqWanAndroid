package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.di.scope.FragmentScope
import com.benyq.benyqwanandroid.ui.fragment.HomeFragment
import com.benyq.benyqwanandroid.ui.fragment.NavigationFragment
import com.benyq.benyqwanandroid.ui.fragment.ProjectDetailFragment
import com.benyq.benyqwanandroid.ui.fragment.ProjectFragment
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


    @FragmentScope
    @ContributesAndroidInjector(modules = [ProjectFragmentModule::class])
    abstract fun contributeProjectFragmentInjector(): ProjectFragment


    @FragmentScope
    @ContributesAndroidInjector(modules = [ProjectDetailFragmentModule::class])
    abstract fun contributeProjectDetailFragmentInjector(): ProjectDetailFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NavigationFragmentModule::class])
    abstract fun contributeNavigationFragmentInjector(): NavigationFragment
}