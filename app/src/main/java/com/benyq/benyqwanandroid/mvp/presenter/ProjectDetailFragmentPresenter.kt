package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.ProjectModel
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.ProjectDetailFragmentContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class ProjectDetailFragmentPresenter@Inject constructor(private val mRootView: ProjectDetailFragmentContract.View, private val api : AppServiceAPi)
    : RxPresenter<ProjectDetailFragmentContract.View>(mRootView), ProjectDetailFragmentContract.Presenter{

    override fun getProject(index: Int, id: Int) {
        api.getProject(index, id)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<ProjectModel>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                    override fun onNext(t: ProjectModel) {
                        mRootView.showProjects(t)
                    }

                })
    }
}