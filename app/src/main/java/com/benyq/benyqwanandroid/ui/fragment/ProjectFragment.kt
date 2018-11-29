package com.benyq.benyqwanandroid.ui.fragment

import android.util.Log
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.BaseFragment

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
class ProjectFragment: BaseFragment() {

    companion object {
        fun getInstance(): ProjectFragment{
            val projectFragment = ProjectFragment()
            return projectFragment
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("benyq", "onHiddenChanged" + javaClass.name)
    }

    override fun getLayoutId() = R.layout.fragment_project

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}