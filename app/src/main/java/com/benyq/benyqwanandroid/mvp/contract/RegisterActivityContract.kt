package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.BaseResponse
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView
import io.reactivex.Observable

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/23
 */
interface RegisterActivityContract {

    interface View: IBaseView{
        fun showRegisterResponse(loginModel: LoginModel)
    }

    interface Presenter: IBasePresenter{
        fun register(username: String, password: String , rePassword: String)

    }
}