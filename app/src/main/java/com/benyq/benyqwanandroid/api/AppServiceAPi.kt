package com.benyq.benyqwanandroid.api

import com.benyq.benyqwanandroid.api.model.BannerModel
import com.benyq.benyqwanandroid.api.model.LoginModel
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
    fun login(@Field("username")username: String, @Field("password")password: String): Observable<BaseResponse<LoginModel>>

    /**
     *注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username")username: String,
                 @Field("password")password: String,
                 @Field("repassword")rePassword: String): Observable<BaseResponse<LoginModel>>


    /**
     * 登出
     */
    @GET("/user/logout/json")
    fun logout(): Observable<BaseResponse<String>>


    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun getBanner(): Observable<BaseResponse<List<BannerModel>>>



    /**
     * 增加一条待办
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/todo/add/json")
    fun addTodo(@Field("content")content: String,
                @Field("title")title: String,
                @Field("date")date: String,
                @Field("type")type: Int): Observable<BaseResponse<String>>





}