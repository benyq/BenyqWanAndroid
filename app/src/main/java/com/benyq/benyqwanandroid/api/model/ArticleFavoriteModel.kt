package com.benyq.benyqwanandroid.api.model

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/5
 */
data class ArticleFavoriteModel(
    val curPage: Int,
    val datas: List<ArticleFavorite>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
){
    data class ArticleFavorite(
            val author: String,
            val chapterId: Int,
            val chapterName: String,
            val courseId: Int,
            val desc: String,
            val envelopePic: String,
            val id: Int,
            val link: String,
            val niceDate: String,
            val origin: String,
            val originId: Int,
            val publishTime: Long,
            val title: String,
            val userId: Int,
            val visible: Int,
            val zan: Int
    )
}

