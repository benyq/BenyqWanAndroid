package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.BaseResponse
import com.benyq.benyqwanandroid.api.model.BannerModel
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView
import io.reactivex.Observable

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/27
 */
interface HomeFragmentContract {

    interface View: IBaseView {
        fun showBanner(banners: List<BannerModel>)
    }

    interface Presenter: IBasePresenter {
        fun getBanner()
    }

}