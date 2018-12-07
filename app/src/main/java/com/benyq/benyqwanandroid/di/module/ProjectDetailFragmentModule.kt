package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.ProjectDetailFragmentContract
import com.benyq.benyqwanandroid.mvp.contract.ProjectFragmentContract
import com.benyq.benyqwanandroid.ui.fragment.ProjectDetailFragment
import com.benyq.benyqwanandroid.ui.fragment.ProjectFragment
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
@Module
class ProjectDetailFragmentModule {

    @Provides
    fun providesView(view : ProjectDetailFragment): ProjectDetailFragmentContract.View{
        return view
    }

}