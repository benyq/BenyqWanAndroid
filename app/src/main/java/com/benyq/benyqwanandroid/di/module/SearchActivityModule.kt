package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.RegisterActivityContract
import com.benyq.benyqwanandroid.mvp.contract.SearchActivityContract
import com.benyq.benyqwanandroid.ui.activity.RegisterActivity
import com.benyq.benyqwanandroid.ui.activity.SearchActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
@Module
class SearchActivityModule {

    @Provides
    fun providesView(view : SearchActivity): SearchActivityContract.View{
        return view
    }

}