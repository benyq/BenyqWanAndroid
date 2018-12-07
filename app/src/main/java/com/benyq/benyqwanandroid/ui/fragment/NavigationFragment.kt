package com.benyq.benyqwanandroid.ui.fragment

import android.util.Log
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.mvp.contract.NavigationFragmentContract

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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun getLayoutId() = R.layout.fragment_navigation

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
    }
}