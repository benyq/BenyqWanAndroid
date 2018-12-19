package com.benyq.benyqwanandroid.ui.entitiy

import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.base.adapter.SectionEntity

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
class TodoSectionEntity(isHeader: Boolean, head: String, data: TodoModel.Todo? = null): SectionEntity<TodoModel.Todo>(isHeader, head, data) {
    constructor(data: TodoModel.Todo): this(false, "", data)
}