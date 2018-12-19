package com.benyq.benyqwanandroid.api

import com.benyq.benyqwanandroid.api.model.*
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
     * 首页文章
     */
    @GET("/article/list/{id}/json")
    fun getHomeArticles(@Path("id") id: Int): Observable<BaseResponse<ArticleModel>>


    /**
     * 搜索热词
     */
    @GET("/hotkey/json")
    fun getHotWords(): Observable<BaseResponse<List<HotWordModel>>>

    /**
     * 收藏文章列表
     */
    @GET("/lg/collect/list/{id}/json")
    fun getCollectedArticles(@Path("id") id: Int): Observable<BaseResponse<ArticleFavoriteModel>>

    /**
     * 收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<BaseResponse<String>>

    /**
     * 取消收藏文章1
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticleOriginId(@Path("id") id: Int): Observable<BaseResponse<String>>

    /**
     * 取消收藏文章2,收藏页面
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun unCollectArticle(@Path("id") id: Int, @Field("originId") originId: Int): Observable<BaseResponse<String>>


    /**
     *项目分类
     */
    @GET("/project/tree/json")
    fun getProjectCategory(): Observable<BaseResponse<List<ProjectCategoryModel>>>


    /**
     *项目列表数据
     */
    @GET("/project/list/{index}/json")
    fun getProject(@Path("index") index: Int, @Query("cid") id: Int): Observable<BaseResponse<ProjectModel>>


    /**
     *项目列表数据
     */
    @GET("/navi/json")
    fun getNavigation(): Observable<BaseResponse<List<NavigationModel>>>


    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("/article/query/{id}/json")
    fun queryArticle(@Path("id") id: Int, @Field("k") query: String): Observable<BaseResponse<QueryModel>>

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
                @Field("type")type: Int = 0,
                @Field("priority ")priority:Int = 0): Observable<BaseResponse<TodoModel.Todo>>



    /**
     * 更新一条待办
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/todo/update/{id}/json")
    fun updateTodo(@Path("id")id: Int,
                   @Field("content")content: String,
                   @Field("title")title: String,
                   @Field("date")date: String,
                   @Field("status")status: Int,
                   @Field("type")type: Int,
                   @Field("priority")priority:Int): Observable<BaseResponse<TodoModel.Todo>>


    /**
     * 删除一条待办
     * @param param
     * @return
     */
    @GET("/lg/todo/delete/{id}/json")
    fun deleteTodo(@Path("id")id: Int): Observable<BaseResponse<String>>


    /**
     * 完成一条待办
     */
    @FormUrlEncoded
    @POST("/lg/todo/done/{id}/json")
    fun updateStatusTodo(@Path("id")id: Int,
                 @Field("status")status: Int): Observable<BaseResponse<TodoModel.Todo>>

    /**
     * todo列表
     */
    @GET("/lg/todo/v2/list/{id}/json")
    fun getTodoList(@Path("id")id: Int,
                 @Query("status")status: Int,
                 @Query("type")type:Int,
                 @Query("priority")priority:Int,
                 @Query("orderby")orderby: Int): Observable<BaseResponse<TodoModel>>

}