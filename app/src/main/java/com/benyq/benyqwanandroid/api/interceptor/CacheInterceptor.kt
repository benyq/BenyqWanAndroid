package com.benyq.benyqwanandroid.api.interceptor

import android.util.Log
import com.benyq.benyqwanandroid.app.App
import com.benyq.benyqwanandroid.isNetWorkConnected
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class CacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("okzxhttp", "无网络${App.instance.applicationContext.isNetWorkConnected()}")
        var request = chain.request()
        if (!App.instance.applicationContext.isNetWorkConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            Log.i("okzxhttp", "无网络${App.instance.applicationContext.isNetWorkConnected()}")
        }
        val response = chain.proceed(request)
        if (App.instance.applicationContext.isNetWorkConnected()) {
            val maxAge = 0
            // 有网络时, 缓存, 最大保存时长为300
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
        }else {
            val maxAge = 60 * 5
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxAge")
                    .build()
        }

        return response
    }
}