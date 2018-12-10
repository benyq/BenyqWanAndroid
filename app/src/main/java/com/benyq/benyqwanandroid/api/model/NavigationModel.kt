package com.benyq.benyqwanandroid.api.model

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */

data class NavigationModel(
    val articles: List<Article>,
    val cid: Int,
    val name: String
){
    data class Article(
            val apkLink: String,
            val author: String,
            val chapterId: Int,
            val chapterName: String,
            val collect: Boolean,
            val courseId: Int,
            val desc: String,
            val envelopePic: String,
            val fresh: Boolean,
            val id: Int,
            val link: String,
            val niceDate: String,
            val origin: String,
            val projectLink: String,
            val publishTime: Long,
            val superChapterId: Int,
            val superChapterName: String,
            val tags: List<Tag>,
            val title: String,
            val type: Int,
            val userId: Int,
            val visible: Int,
            val zan: Int
    ){
        data class Tag(
                val name: String,
                val url: String
        )
    }

}

