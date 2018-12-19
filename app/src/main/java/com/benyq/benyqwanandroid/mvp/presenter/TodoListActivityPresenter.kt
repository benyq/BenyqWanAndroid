package com.benyq.benyqwanandroid.mvp.presenter

import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.CommonSubscriber
import com.benyq.benyqwanandroid.api.ResponseTransformer
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.mvp.RxPresenter
import com.benyq.benyqwanandroid.mvp.contract.TodoListActivityContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
class TodoListActivityPresenter@Inject constructor(private val mRootView: TodoListActivityContract.View, private val api: AppServiceAPi)
    : RxPresenter<TodoListActivityContract.View>(mRootView), TodoListActivityContract.Presenter {

    override fun updateTodo(id: Int, content: String, title: String, date: String, status: Int, type: Int, priority: Int) {
        api.updateTodo(id, content, title, date, status, type, priority)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()

                }.subscribe(object : CommonSubscriber<TodoModel.Todo>(mRootView){
                    override fun onNext(t: TodoModel.Todo) {
                        mRootView.showUpdate(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }

    override fun UpdateStatusTodo(id: Int, status: Int) {
        api.updateStatusTodo(id, status)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object: CommonSubscriber<TodoModel.Todo>(mRootView){
                    override fun onNext(t: TodoModel.Todo) {
                        mRootView.showUpdateStatus(id, t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }

    override fun deleteTodo(id: Int) {
        api.deleteTodo(id)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                    mRootView.showDelete(id)
                }.subscribe(object: CommonSubscriber<String>(mRootView){
                    override fun onNext(t: String) {

                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }

    override fun getTodoList(id: Int, status: Int, type: Int, priority: Int, orderby: Int) {
        api.getTodoList(id, status, type, priority, orderby)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<TodoModel>(mRootView){
                    override fun onNext(t: TodoModel) {
                        mRootView.showTodoList(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                })
    }


    override fun addTodo(param: AddTodoParam) {
        api.addTodo(param.content, param.title, param.date, param.type, 1)
                .compose(ResponseTransformer.rxSchedulerHelper())
                .compose(ResponseTransformer.handleFinanceResult())
                .doOnSubscribe {
                    mRootView.showLoading()
                }.doFinally {
                    mRootView.dismissLoading()
                }.subscribe(object : CommonSubscriber<TodoModel.Todo>(mRootView){
                    override fun onSubscribe(d: Disposable) {
                        addSubscribe(d)
                    }

                    override fun onNext(t: TodoModel.Todo) {
                        mRootView.addTodo(t)
                    }

                })
    }
}