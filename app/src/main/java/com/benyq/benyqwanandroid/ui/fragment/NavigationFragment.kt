package com.benyq.benyqwanandroid.ui.fragment

import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.mvp.contract.NavigationFragmentContract
import com.benyq.benyqwanandroid.mvp.presenter.NavigationFragmentPresenter
import com.benyq.benyqwanandroid.ui.entitiy.NaviSectionEntity
import com.benyq.benyqwanandroid.ui.adapter.NavigationAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_navigation.*
import javax.inject.Inject
import com.google.android.flexbox.JustifyContent
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap


/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
class NavigationFragment: BaseFragment(), NavigationFragmentContract.View {


    companion object {
        fun getInstance(): NavigationFragment{
            val navigationFragment = NavigationFragment()
            return navigationFragment
        }
    }

    @Inject
    lateinit var mPresenter: NavigationFragmentPresenter

    private val mAdapter by lazy { NavigationAdapter(mContext, R.layout.item_head, R.layout.item_navi) }


    override fun getLayoutId() = R.layout.fragment_navigation

    override fun initView() {
        rvNavi.run {
            val flexboxLayoutManager = FlexboxLayoutManager(context)
            flexboxLayoutManager.flexDirection = FlexDirection.ROW
            flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
            flexboxLayoutManager.flexWrap = FlexWrap.WRAP
            layoutManager = flexboxLayoutManager
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val article = mAdapter.mData[position].data
                article?.run {
                    ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                            .withString("url", link)
                            .withString("title", title)
                            .withInt("id", id)
                            .navigation()
                }
            }
        })
    }

    override fun lazyLoad() {
        mPresenter.getNavigation()
    }

    override fun showLoading() {
        Toast.makeText(mContext, "showLoading", Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoading() {
        Toast.makeText(mContext, "dismissLoading", Toast.LENGTH_SHORT).show()
    }

    override fun showError(t: String) {
    }

    override fun showNavigation(data: List<NavigationModel>) {
        mAdapter.clearData()
        val naviSectionEntityList = mutableListOf<NaviSectionEntity>()
        data.forEach {
            naviSectionEntityList.add(NaviSectionEntity(true, it.name))
            it.articles.forEach { article->
                naviSectionEntityList.add(NaviSectionEntity(article))
            }
        }
        mAdapter.addNewData(naviSectionEntityList)
    }
}