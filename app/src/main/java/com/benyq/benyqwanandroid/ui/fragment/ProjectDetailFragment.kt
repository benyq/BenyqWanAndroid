package com.benyq.benyqwanandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ProjectModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.mvp.contract.ProjectDetailFragmentContract
import com.benyq.benyqwanandroid.mvp.presenter.ProjectDetailFragmentPresenter
import com.benyq.benyqwanandroid.ui.adapter.ProjectDetailAdapter
import kotlinx.android.synthetic.main.fragment_project_detail.*
import javax.inject.Inject

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
 */
class ProjectDetailFragment : BaseFragment() , ProjectDetailFragmentContract.View{


    companion object {
        fun getInstance(id : Int): ProjectDetailFragment{
            val fragment = ProjectDetailFragment()
            fragment.categoryId = id
            return fragment
        }
    }


    @Inject
    lateinit var mPresenter: ProjectDetailFragmentPresenter

    private val mAdapter by lazy { ProjectDetailAdapter(mContext) }

    private var mCurPage = 1
    private var mPageCount = 100
    private var mLoading: Boolean = false

    private var categoryId = -1

    override fun getLayoutId() = R.layout.fragment_project_detail

    override fun initView() {
        rvProject.run {
            layoutManager = LinearLayoutManager(mContext)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val manager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    //向下滚动
                    if (dy > 0){
                        val visibleItemCount = manager.childCount
                        val totalItemCount = manager.itemCount
                        val pastVisibleItems = manager.findFirstVisibleItemPosition()
                        //距还剩5个时加载
                        Log.e("benyq", "${!mLoading} && ${(visibleItemCount + pastVisibleItems) >= totalItemCount - 5}")
                        if (!mLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount - 5) {
                            mLoading = true
                            loadMoreDate()
                        }
                    }
                }
            })
        }
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val project = mAdapter.mData[position]
                ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                        .withString("url", project.link)
                        .withString("title", project.title)
                        .withInt("id", project.id)
                        .navigation()
            }
        })
        rvProject.adapter = mAdapter
    }

    override fun lazyLoad() {
        mPresenter.getProject(0, categoryId)
    }

    override fun showProjects(data:ProjectModel) {
        data.run {
            mCurPage = curPage
            mPageCount = pageCount
            Log.e("benyq", "curPage$curPage")
            val s = data.datas.map {
                it.copy(title = Html.fromHtml(it.title).toString())
            }
            if (mCurPage == 1){
                mAdapter.addNewData(s.toMutableList())
            }else {
                mAdapter.addData(s.toMutableList())
            }
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        mLoading = false
    }

    override fun showError(t: String) {

    }

    private fun loadMoreDate(){
        mPresenter.getProject(mCurPage + 1, categoryId)
    }
}