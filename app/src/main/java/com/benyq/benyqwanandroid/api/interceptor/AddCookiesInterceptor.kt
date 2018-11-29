package com.benyq.benyqwanandroid.api.interceptor
import com.benyq.benyqwanandroid.local.CacheManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
class AddCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = CacheManager.cookies_pref
        cookies.forEach {
            builder.addHeader("Cookie", it)
        }
        return chain.proceed(builder.build())
    }
}