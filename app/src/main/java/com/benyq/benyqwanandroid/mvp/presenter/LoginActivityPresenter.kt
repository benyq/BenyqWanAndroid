package com.benyq.benyqwanandroid.mvp.presenter

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.LoginActivityContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
class LoginActivityPresenter@Inject constructor(private val mRootView: LoginActivityContract.View, private val api: AppServiceAPi)
    : RxPresenter<LoginActivityContract.View>(mRootView), LoginActivityContract.Presenter {


    override fun login(param: LoginParam) {
        api.login(param.username,param.password)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .doFinally {
                    mRootView.dismissLoading()
                }
                .subscribe(object : CommonSubscriber<LoginModel>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }
                    override fun onNext(t: LoginModel) {
                        CacheManager.username = param.username
                        mRootView.showSuccess(t)
                    }
                })
    }
}