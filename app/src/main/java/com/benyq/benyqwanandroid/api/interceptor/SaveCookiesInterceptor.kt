package com.benyq.benyqwanandroid.api.interceptor

import android.util.Log
import com.benyq.benyqwanandroid.local.CacheManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/23
 */
class SaveCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = mutableSetOf<String>()
            originalResponse.headers("Set-Cookie").forEach {
                Log.e("SaveCookiesInterceptor", it)
                cookies.add(it)
            }
            CacheManager.cookies_pref = cookies
        }
        return originalResponse

    }
}