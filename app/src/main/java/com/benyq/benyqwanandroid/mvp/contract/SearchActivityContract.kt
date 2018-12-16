package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.HotWordModel
import com.benyq.benyqwanandroid.api.model.QueryModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
interface SearchActivityContract {

    interface View: IBaseView{
        fun showHotWords(data: List<HotWordModel>)

        fun showSearch(data: QueryModel)
    }

    interface Presenter: IBasePresenter{
        fun getHotWords()

        fun searchArticle(id: Int, key: String)
    }
}