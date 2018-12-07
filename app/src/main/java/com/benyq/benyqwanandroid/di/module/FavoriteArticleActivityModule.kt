package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.FavoriteArticleActivityContract
import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import com.benyq.benyqwanandroid.ui.activity.FavoriteArticleActivity
import com.benyq.benyqwanandroid.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/3
 */
@Module
class FavoriteArticleActivityModule {

    @Provides
    fun providesView(view : FavoriteArticleActivity): FavoriteArticleActivityContract.View{
        return view
    }

}