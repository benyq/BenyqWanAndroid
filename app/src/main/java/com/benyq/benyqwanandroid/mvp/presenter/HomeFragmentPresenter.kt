package com.benyq.benyqwanandroid.mvp.presenter

import android.util.Log
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.BannerModel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
class HomeFragmentPresenter@Inject constructor(private val mRootView: HomeFragmentContract.View, private val api: AppServiceAPi)
    :RxPresenter<HomeFragmentContract.View>(mRootView), HomeFragmentContract.Presenter {

    override fun getBanner(){
        api.getBanner()
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<List<BannerModel>>(mRootView){
                    override fun onNext(t: List<BannerModel>) {
                        mRootView.showBanner(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }

}