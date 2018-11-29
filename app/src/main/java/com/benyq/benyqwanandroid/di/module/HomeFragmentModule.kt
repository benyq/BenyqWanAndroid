package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import com.benyq.benyqwanandroid.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
@Module
class HomeFragmentModule {

    @Provides
    fun providesView(view : HomeFragment): HomeFragmentContract.View{
        return view
    }
}