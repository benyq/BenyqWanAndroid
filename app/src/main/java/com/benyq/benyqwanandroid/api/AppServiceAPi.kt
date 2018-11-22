package com.benyq.benyqwanandroid.api

import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.api.param.LoginParam
import io.reactivex.Observable
import retrofit2.http.*

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
interface AppServiceAPi {

    companion object {
        const val baseUrl = "http://wanandroid.com"
    }

    /**
     * 登陆
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username")username: String, @Field("password")password: String): Observable<BaseResponse<String>>
//    fun login(@Body param: LoginParam): Observable<BaseResponse<String>>


    /**
     * 增加一条待办
     * @param param
     * @return
     */
    @POST("/lg/todo/add/json")
    fun addTodo(@Body param: AddTodoParam): Observable<BaseResponse<String>>

}