package com.benyq.benyqwanandroid.local

import com.benyq.benyqwanandroid.Preference
import com.benyq.benyqwanandroid.base.Constants

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/23
 */
object CacheManager {

    var cookies_pref: MutableSet<String> by Preference(Constants.PREF_COOKIE, mutableSetOf())

    var username: String by Preference(Constants.USERNAME, "")

}