package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
interface TodoListActivityContract {

    interface View: IBaseView{
        fun showTodoList(todoModel: TodoModel, status: Int)

        fun showDelete(id: Int)

        fun showUpdateStatus(id: Int, data: TodoModel.Todo)

        fun showUpdate(data: TodoModel.Todo)

        fun addTodo(todo: TodoModel.Todo)
    }

    interface Presenter: IBasePresenter{
        fun addTodo(param: AddTodoParam)

        fun getTodoList(id: Int, status: Int, type: Int = 0, priority: Int = 0, orderby: Int = 4)

        fun deleteTodo(id: Int)

        fun UpdateStatusTodo(id: Int, status: Int)

        fun updateTodo(id: Int, content: String, title: String, date: String, status: Int, type: Int, priority:Int)

    }

}