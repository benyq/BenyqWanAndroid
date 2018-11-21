package com.benyq.benyqwanandroid.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/20
 */
open class RxPresenter<T : IBaseView> : IBasePresenter<T> {

    protected var mRootView: T? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    private fun unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.dispose()
        }
    }

    protected fun addSubscribe(subscription: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(subscription)
    }

    override fun attachView(view: T) {
        this.mRootView = view
    }

    override fun detachView() {
        this.mRootView = null
        unSubscribe()
    }

}
