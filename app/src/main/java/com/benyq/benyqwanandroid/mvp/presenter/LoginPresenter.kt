package com.benyq.benyqwanandroid.mvp.presenter

import android.util.Log
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.LoginContract
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
class LoginPresenter@Inject constructor(mRootView: LoginContract.View, val api: AppServiceAPi): RxPresenter<LoginContract.View>(mRootView), LoginContract.Presenter {


    override fun login(param: LoginParam) {

        addSubscribe(api.login(param.username, param.password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Log.e("benyq", "doOnSubscribe")
                }
                .doFinally {
                    Log.e("benyq", "doFinally")
                }
                .subscribe({

                },{

                }))
    }
}