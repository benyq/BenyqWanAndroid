package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.MainActivityContract
import com.benyq.benyqwanandroid.ui.activity.MainActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
@Module
class MainActivityModule {

    @Provides
    fun providesView(view : MainActivity): MainActivityContract.View{
        return view
    }
}