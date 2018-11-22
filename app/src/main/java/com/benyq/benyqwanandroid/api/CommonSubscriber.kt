package com.benyq.benyqwanandroid.api

import com.benyq.benyqwanandroid.mvp.IBaseView
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
abstract class CommonSubscriber<T>(private val mRootView: IBaseView): Observer<T> {

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        when (e) {
            is ApiException -> {

            }
            is JsonParseException -> {
                //json解析错误
            }
            is HttpException ->{
                when(e.code()){
                    405, 500 ->{

                    }
                    else -> {

                    }
                }
            }
        }
    }

}