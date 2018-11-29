package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.RegisterActivityContract
import com.benyq.benyqwanandroid.ui.activity.RegisterActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/23
 */
@Module
class RegisterActivityModule {

    @Provides
    fun providesView(view : RegisterActivity): RegisterActivityContract.View{
        return view
    }
}