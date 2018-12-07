package com.benyq.benyqwanandroid.api.model

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
 */
data class ProjectCategoryModel(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)