package com.benyq.benyqwanandroid.api.Interceptor

import com.benyq.benyqwanandroid.Preference
import okhttp3.Interceptor
import okhttp3.Response
import java.util.prefs.Preferences

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
class AddCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val cookies: MutableSet<String> = Preference<MutableSet<String>>("", mutableSetOf())
        HashSet<String> preferences = (HashSet) Preferences.getDefaultPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build())
    }
}