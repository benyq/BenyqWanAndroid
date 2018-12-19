package com.benyq.benyqwanandroid.api.model

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */

data class TodoModel(
    val curPage: Int,
    val datas: List<Todo>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
){
    data class Todo(
            val completeDate: Long = 0,
            val completeDateStr: String = "",
            val content: String = "",
            val date: Long = System.currentTimeMillis(),
            val dateStr: String = "",
            val id: Int = -1,
            val priority: Int = 0,
            val status: Int = 0,
            val title: String = "",
            val type: Int = 0,
            val userId: Int = 0
    )
}

