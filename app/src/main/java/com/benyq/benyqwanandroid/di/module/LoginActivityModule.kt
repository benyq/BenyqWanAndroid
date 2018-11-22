package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.LoginActivityContract
import com.benyq.benyqwanandroid.ui.activity.LoginActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
@Module
class LoginActivityModule {

    @Provides
    fun providesView(view : LoginActivity): LoginActivityContract.View{
        return view
    }

}