package com.benyq.benyqwanandroid.api.interceptor

import com.benyq.benyqwanandroid.app.App
import com.benyq.benyqwanandroid.isNetWorkConnected
import okhttp3.Interceptor
import okhttp3.Response

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class OfflineCacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!App.instance.applicationContext.isNetWorkConnected()) {
            val offlineCacheTime = 60 * 5//离线的时候的缓存的过期时间
            request = request.newBuilder()
//                        .cacheControl(new CacheControl
//                                .Builder()
//                                .maxStale(60,TimeUnit.SECONDS)
//                                .onlyIfCached()
//                                .build()
//                        ) 两种方式结果是一样的，写法不同
                    .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                    .build()
        }
        return chain.proceed(request)
    }
}