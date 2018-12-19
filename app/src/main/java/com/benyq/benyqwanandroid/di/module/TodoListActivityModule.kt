package com.benyq.benyqwanandroid.di.module

import com.benyq.benyqwanandroid.mvp.contract.TodoListActivityContract
import com.benyq.benyqwanandroid.ui.activity.TodoListActivity
import dagger.Module
import dagger.Provides

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
@Module
class TodoListActivityModule {

    @Provides
    fun providesView(view : TodoListActivity): TodoListActivityContract.View{
        return view
    }
}