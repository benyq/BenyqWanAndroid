package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
interface NavigationFragmentContract {


    interface View: IBaseView {
        fun showNavigation(data: List<NavigationModel>)
    }

    interface Presenter: IBasePresenter {

        fun getNavigation()
    }
}