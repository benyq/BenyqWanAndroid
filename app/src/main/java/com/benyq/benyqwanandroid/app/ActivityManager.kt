package com.benyq.benyqwanandroid.app

import android.app.Activity
import java.util.*

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
object ActivityManager {

    private val activityStack: Stack<Activity> = Stack()

    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity){
        activityStack.remove(activity)
    }

    fun exitApp(){
        activityStack.forEach {
            it.finish()
        }
    }



}