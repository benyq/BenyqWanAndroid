package com.benyq.benyqwanandroid.ui.fragment

import android.util.Log
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.BaseFragment

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
class NavigationFragment: BaseFragment() {

    companion object {
        fun getInstance(): NavigationFragment{
            val navigationFragment = NavigationFragment()
            return navigationFragment
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("benyq", "onHiddenChanged" + javaClass.name)
    }

    override fun getLayoutId() = R.layout.fragment_navigation

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}