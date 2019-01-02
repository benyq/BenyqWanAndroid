package com.benyq.benyqwanandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
 * 暂时可通用
 */
class ProjectViewPagerAdapter(childFragmentManager: FragmentManager) : FragmentStatePagerAdapter(childFragmentManager) {

    private lateinit var mFragments: List<Fragment>
    private lateinit var mTitles: List<String>

    override fun getItem(p0: Int) = mFragments[p0]

    override fun getCount() = mFragments.size

    override fun getPageTitle(position: Int) = mTitles[position]

    fun setFragmentAndTitles(fragments: List<Fragment>, titles: List<String>){
        mFragments = fragments
        mTitles = titles
    }
}