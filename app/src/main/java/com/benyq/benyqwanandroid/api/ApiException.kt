package com.benyq.benyqwanandroid.api

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
class ApiException(val errorCode: Int, errorMsg: String): Exception(errorMsg) {

}