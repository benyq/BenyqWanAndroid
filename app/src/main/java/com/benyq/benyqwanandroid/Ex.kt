package com.benyq.benyqwanandroid

import android.content.Context
import android.net.ConnectivityManager

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */

fun Context.isNetWorkConnected(): Boolean{
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (cm.activeNetworkInfo != null){
        cm.activeNetworkInfo.isConnected
    }else{
        false
    }

}