package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.NavigationFragmentContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class NavigationFragmentPresenter@Inject constructor(private val mRootView: NavigationFragmentContract.View, private val api: AppServiceAPi)
    :RxPresenter<NavigationFragmentContract.View>(mRootView), NavigationFragmentContract.Presenter{

    override fun getNavigation() {
        api.getNavigation()
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object: CommonSubscriber<List<NavigationModel>>(mRootView){
                    override fun onNext(t: List<NavigationModel>) {
                        mRootView.showNavigation(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }


}