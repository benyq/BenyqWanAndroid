package com.benyq.benyqwanandroid.mvp.presenter

import android.util.Log
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.MainActivityContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
class MainActivityPresenter@Inject constructor(private val mRootView: MainActivityContract.View, private val api: AppServiceAPi)
    : RxPresenter<MainActivityContract.View>(mRootView), MainActivityContract.Presenter {

    override fun logout() {
        api.logout()
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.showLogout()
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<String>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }
                    override fun onNext(t: String) {
                    }

                })
    }

    override fun addTodo(param: AddTodoParam) {
        api.addTodo(param.content, param.title, param.date, param.type)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<String>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                    override fun onNext(t: String) {

                    }

                })
    }
}