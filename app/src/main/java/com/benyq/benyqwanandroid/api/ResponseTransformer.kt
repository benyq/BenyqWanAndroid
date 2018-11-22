package com.benyq.benyqwanandroid.api

import com.benyq.benyqwanandroid.base.Constants
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/22
 */
object ResponseTransformer {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {    //compose简化线程

        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }


    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> handleFinanceResult(): ObservableTransformer<BaseResponse<T>, T> {   //compose判断结果
        return ObservableTransformer { upstream ->
            upstream.flatMap {
                if (Constants.CODE_ZERO == it.errorCode && it.data != null){
                    createData(it.data)
                }else{
                    Observable.error{
                        ApiException(it.errorCode, it.errorMsg)
                    }
                }
            }
        }
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
    </T> */
    private fun <T> createData(t: T): Observable<T> {
        return Observable.create {
            try {
                it.onNext(t)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}