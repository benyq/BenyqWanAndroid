package com.benyq.benyqwanandroid.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class NetCacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 300//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=$onlineCacheTime")
                .removeHeader("Pragma")
                .build()
    }
}