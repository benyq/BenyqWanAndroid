package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.NavigationFragmentContract
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class NavigationFragmentPresenter@Inject constructor(private val mRootView: NavigationFragmentContract.View, private val api: AppServiceAPi)
    :RxPresenter<NavigationFragmentContract.View>(mRootView), NavigationFragmentContract.Presenter{



}