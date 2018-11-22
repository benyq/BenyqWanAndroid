package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/21
 */
interface LoginActivityContract {

    interface View: IBaseView{
        fun showSuccess()
    }

    interface Presenter: IBasePresenter{
        fun login(param: LoginParam)
    }
}