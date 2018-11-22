package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.LoginContract
import com.benyq.benyqwanandroid.ui.activity.LoginActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
@Module
class LoginModule {

    @Provides
    fun providesView(view : LoginActivity): LoginContract.View{
        return view
    }

}