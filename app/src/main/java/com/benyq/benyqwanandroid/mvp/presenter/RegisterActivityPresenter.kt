package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.*
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.base.Constants
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.RegisterActivityContract
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/23
 */
class RegisterActivityPresenter@Inject constructor(val mRootView: RegisterActivityContract.View, val api: AppServiceAPi)
    :RxPresenter<RegisterActivityContract.View>(mRootView), RegisterActivityContract.Presenter{

    override fun register(username: String, password: String, rePassword: String){

        api.register(username, password, rePassword)
                .flatMap {
                    if (it.errorCode == Constants.CODE_ZERO){
                        api.login(username, password)
                    }else{
                        Observable.error(ApiException(it.errorCode, it.errorMsg))
                    }
                }
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<LoginModel>(mRootView){
                    override fun onNext(t: LoginModel) {
                        CacheManager.username = username
                        mRootView.showRegisterResponse(t)
                    }
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }
}