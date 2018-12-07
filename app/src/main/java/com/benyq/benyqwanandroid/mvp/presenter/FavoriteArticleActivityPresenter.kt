package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.ArticleFavoriteModel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.FavoriteArticleActivityContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/3
 */
class FavoriteArticleActivityPresenter@Inject constructor(private val mRootView: FavoriteArticleActivityContract.View, private val api: AppServiceAPi)
    :RxPresenter<FavoriteArticleActivityContract.View>(mRootView), FavoriteArticleActivityContract.Presenter{

    override fun getCollectedArticles(id: Int) {
        api.getCollectedArticles(id)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<ArticleFavoriteModel>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                    override fun onNext(t: ArticleFavoriteModel) {
                        mRootView.showCollectedArticles(t)
                    }

                })
    }


}