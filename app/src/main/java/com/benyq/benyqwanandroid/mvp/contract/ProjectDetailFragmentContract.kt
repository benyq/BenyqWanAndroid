package com.benyq.benyqwanandroid.mvp.contract

import com.benyq.benyqwanandroid.api.model.ProjectCategoryModel
import com.benyq.benyqwanandroid.api.model.ProjectModel
import com.benyq.benyqwanandroid.mvp.IBasePresenter
import com.benyq.benyqwanandroid.mvp.IBaseView

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
interface ProjectDetailFragmentContract {

    interface View: IBaseView {
        fun showProjects(data: ProjectModel)
    }

    interface Presenter: IBasePresenter {
        fun getProject(index: Int, id: Int)
    }
}