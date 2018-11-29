package com.benyq.benyqwanandroid.di.module

import android.util.Log
import com.benyq.benyqwanandroid.BuildConfig
import com.benyq.benyqwanandroid.Preference
import com.benyq.benyqwanandroid.api.AppServiceAPi
import com.benyq.benyqwanandroid.api.interceptor.AddCookiesInterceptor
import com.benyq.benyqwanandroid.api.interceptor.SaveCookiesInterceptor
import com.benyq.benyqwanandroid.app.App
import com.benyq.benyqwanandroid.base.Constants
import com.benyq.benyqwanandroid.isNetWorkConnected
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun providesOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun providesRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, AppServiceAPi.baseUrl)
    }

    @Singleton
    @Provides
    fun provideServerApi(retrofit: Retrofit): AppServiceAPi {
        Log.i("okhttp", "provideServerApi")
        return retrofit.create<AppServiceAPi>(AppServiceAPi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        Log.i("okhttp", "provideOkHttpClient")
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.i("okhttp", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addNetworkInterceptor(loggingInterceptor)

        val cacheFile = File(Constants.PATH_CACHE)
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        val cacheInterceptor = Interceptor {
            var request = it.request()
            if (!App.instance.applicationContext.isNetWorkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                Log.i("okhttp", "无网络")
            }
            val response = it.proceed(request)
            if (App.instance.applicationContext.isNetWorkConnected()) {
                val maxAge = 60 * 5
                // 有网络时, 缓存, 最大保存时长为300
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
            }

            response
        }
        //        Interceptor authId = chain -> {
        //            Request request = chain.request();
        //            request = ParamsInfoUtils.addPostParams(request);
        //            return chain.proceed(request);
        //        };
        // 设置统一的请求头部参数
        //        builder.addInterceptor(authId);
        //        builder.addInterceptor(new VInterceptor());
        //        RetrofitUrlManager.getInstance().with(builder);
        //设置缓存
        with(builder) {
            addNetworkInterceptor(cacheInterceptor)
            addInterceptor(SaveCookiesInterceptor())
            addInterceptor(AddCookiesInterceptor())
            cache(cache)
            //设置超时
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(90, TimeUnit.SECONDS)
            writeTimeout(90, TimeUnit.SECONDS)
            //错误重连
            retryOnConnectionFailure(true)
            build()
        }
        return builder.build()
    }

    private fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient, url: String): Retrofit {
        Log.i("okhttp", "createRetrofit")
        return builder.baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}