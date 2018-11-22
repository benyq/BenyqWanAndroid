package com.benyq.benyqwanandroid.base

import com.benyq.benyqwanandroid.app.App
import java.io.File

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
object Constants {

    private val PATH_DATA = App.instance.cacheDir.absolutePath + File.separator + "data"

    val PATH_CACHE = "$PATH_DATA/androidFrameDemo"

    const val CODE_ZERO = 0

}