package com.benyq.benyqwanandroid.api

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
data class BaseResponse<T>(val errorCode: Int,
                      val errorMsg: String,
                      val data: T)