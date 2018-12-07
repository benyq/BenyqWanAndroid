package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.ProjectCategoryModel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.ProjectFragmentContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
 */
class ProjectFragmentPresenter@Inject constructor(val mRootView: ProjectFragmentContract.View, private val api: AppServiceAPi)
    : RxPresenter<ProjectFragmentContract.View>(mRootView), ProjectFragmentContract.Presenter{

    override fun getProjectCategory() {
        api.getProjectCategory()
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<List<ProjectCategoryModel>>(mRootView){
                    override fun onNext(t: List<ProjectCategoryModel>) {
                        mRootView.showProjectCategory(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                       addSubscribe(d)
                    }
                })
    }
}