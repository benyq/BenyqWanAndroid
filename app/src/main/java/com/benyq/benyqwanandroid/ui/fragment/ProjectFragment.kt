package com.benyq.benyqwanandroid.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.text.Html
import android.util.Log
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ProjectCategoryModel
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.mvp.contract.ProjectFragmentContract
import com.benyq.benyqwanandroid.mvp.presenter.ProjectFragmentPresenter
import com.benyq.benyqwanandroid.ui.adapter.ProjectViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_project.*
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
class ProjectFragment: BaseFragment(), ProjectFragmentContract.View {


    companion object {
        fun getInstance(): ProjectFragment{
            val projectFragment = ProjectFragment()
            return projectFragment
        }
    }

    @Inject
    lateinit var mPresenter: ProjectFragmentPresenter

    override fun getLayoutId() = R.layout.fragment_project

    override fun initView() {
    }

    override fun lazyLoad() {
        mPresenter.getProjectCategory()
    }

    override fun showProjectCategory(data: List<ProjectCategoryModel>) {

        val fragments = arrayListOf<ProjectDetailFragment>()
        val titles = arrayListOf<String>()

        data.forEach {
            fragments.add(ProjectDetailFragment.getInstance(it.id))
            titles.add(Html.fromHtml(it.name).toString())
        }

        val projectViewPagerAdapter = ProjectViewPagerAdapter(childFragmentManager)
        projectViewPagerAdapter.setFragmentAndTitles(fragments, titles)
        vpProject.offscreenPageLimit = data.size + 1
        vpProject.adapter = projectViewPagerAdapter
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(vpProject)
        vpProject.currentItem = 0//在设置adapter之后生效
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
    }
}