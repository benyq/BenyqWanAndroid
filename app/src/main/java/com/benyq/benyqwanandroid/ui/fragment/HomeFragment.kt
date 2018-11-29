package com.benyq.benyqwanandroid.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.BannerModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import com.benyq.benyqwanandroid.mvp.presenter.HomeFragmentPresenter
import com.bumptech.glide.Glide
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader


/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/24
 */
class HomeFragment: BaseFragment(), HomeFragmentContract.View {

    companion object {
        fun getInstance(): HomeFragment{
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    @Inject
    lateinit var mPresenter: HomeFragmentPresenter

    private lateinit var mBanners: List<BannerModel>

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
        //设置图片加载器
        banner.setImageLoader(object : ImageLoader(){
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                Glide.with(context).load(path).into(imageView)
            }

        })
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(1500)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)

        banner.setOnBannerListener{
            ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                    .withString("url", mBanners[it].url)
                    .withString("title", mBanners[it].title)
                    .navigation()
        }

        rvArticle.layoutManager = LinearLayoutManager(activity)

    }

    override fun lazyLoad() {
        mPresenter.getBanner()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }

    override fun showBanner(banners: List<BannerModel>) {
        if (banners.isNotEmpty()){
            mBanners = banners
            //设置标题集合（当banner样式有显示title时）
            banner.setBannerTitles(banners.map { it.title })
            //设置图片集合
            banner.setImages(banners.map { it.imagePath })
            //banner设置方法全部调用完毕时最后调用
            banner.start()
        }
    }
}