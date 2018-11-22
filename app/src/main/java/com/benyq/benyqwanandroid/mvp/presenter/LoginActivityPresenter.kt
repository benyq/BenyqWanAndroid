package com.benyq.benyqwanandroid.mvp.presenter

import android.util.Log
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.param.LoginParam
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
                    Log.e("benyq", "doOnSubscribe")
                }
                .doFinally {
                    Log.e("benyq", "doFinally")
                }
                .subscribe(object : CommonSubscriber<String>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }
                    override fun onNext(t: String) {

                    }
                })
    }
}