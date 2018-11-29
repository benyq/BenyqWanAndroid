package com.benyq.benyqwanandroid.api.model

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
data class BannerModel(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)