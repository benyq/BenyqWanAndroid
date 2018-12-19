package com.benyq.benyqwanandroid.ui.entitiy.event

import com.benyq.benyqwanandroid.api.model.TodoModel

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/19
 */
data class TodoEvent(val status: Int = 0, val todo: TodoModel.Todo)