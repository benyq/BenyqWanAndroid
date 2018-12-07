package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.ArticleFavoriteModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/3
 */
interface FavoriteArticleActivityContract {

    interface View: IBaseView {
        fun showCollectedArticles(articleFavoriteModel: ArticleFavoriteModel)
    }

    interface Presenter: IBasePresenter {
        fun getCollectedArticles(id : Int)
    }
}