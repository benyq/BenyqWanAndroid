package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.HotWordMoedel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
interface SearchActivityContract {

    interface View: IBaseView{
        fun showHotWords(data: List<HotWordMoedel>)
    }

    interface Presenter: IBasePresenter{
        fun getHotWords()
    }
}