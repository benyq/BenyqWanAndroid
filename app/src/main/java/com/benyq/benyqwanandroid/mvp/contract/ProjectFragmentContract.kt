package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.ProjectCategoryModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
 */
interface ProjectFragmentContract {

    interface View: IBaseView{
        fun showProjectCategory(data: List<ProjectCategoryModel>)
    }

    interface Presenter: IBasePresenter{
        fun getProjectCategory()
    }
}