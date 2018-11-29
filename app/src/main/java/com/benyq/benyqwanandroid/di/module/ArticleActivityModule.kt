package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.ArticleActivityContract
import com.benyq.benyqwanandroid.ui.activity.ArticleActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
@Module
class ArticleActivityModule {

    @Provides
    fun providesView(view : ArticleActivity): ArticleActivityContract.View{
        return view
    }

}