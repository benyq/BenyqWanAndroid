package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.ArticleActivityContract
import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/5
 */
class ArticleActivityPresenter@Inject constructor(private val mRootView: ArticleActivityContract.View, private val api: AppServiceAPi)
    : RxPresenter<ArticleActivityContract.View>(mRootView), ArticleActivityContract.Presenter{

    override fun unCollectArticle(id: Int) {
        api.unCollectArticleOriginId(id)
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
                        mRootView.showUnCollectResponse()
                    }

                })
    }

    override fun unCollectArticle(id: Int, originId: Int) {
        api.unCollectArticle(id, originId)
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
                        mRootView.showUnCollectResponse()
                    }

                })
    }

    override fun collectArticle(id: Int) {
        api.collectArticle(id)
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
                        mRootView.showCollectArticleResponse()
                    }

                })
    }
}