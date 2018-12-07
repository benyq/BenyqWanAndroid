package com.benyq.benyqwanandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/6
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