package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.HotWordMoedel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.SearchActivityContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
class SearchActivityPresenter@Inject constructor(private val mRootView: SearchActivityContract.View, private val api: AppServiceAPi)
    :RxPresenter<SearchActivityContract.View>(mRootView), SearchActivityContract.Presenter{
    override fun getHotWords() {
        api.getHotWords()
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<List<HotWordMoedel>>(mRootView){
                    override fun onNext(t: List<HotWordMoedel>) {
                        mRootView.showHotWords(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }


}