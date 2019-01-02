package com.benyq.benyqwanandroid.ui.fragment

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ArticleModel
import com.benyq.benyqwanandroid.api.model.BannerModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.base.adapter.OnItemChildClickListener
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.mvp.contract.HomeFragmentContract
import com.benyq.benyqwanandroid.mvp.presenter.HomeFragmentPresenter
import com.benyq.benyqwanandroid.ui.adapter.HomeArticleAdapter
import com.bumptech.glide.Glide
import javax.inject.Inject
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*


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

    private val mAdapter by lazy { HomeArticleAdapter(mContext) }


    private var mCurPage = 1
    private var mPageCount = 100
    private var mLoading: Boolean = false



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
                    .withInt("id", mBanners[it].id)
                    .navigation()
        }

        rvArticle.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
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
                        if (!mLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount - 5) {
                            mLoading = true
                            loadMoreData()
                        }
                    }
                }
            })
        }

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val article = mAdapter.mData[position]
                ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                        .withString("url", article.link)
                        .withString("title", article.title)
                        .withInt("id", article.id)
                        .navigation()
            }
        })

        mAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(view: View, position: Int) {
                when(view.id){
                    R.id.ivShare -> {
                        Toast.makeText(activity, "ivShare", Toast.LENGTH_SHORT).show()
                    }
                    R.id.ivFavorite -> {
                        Toast.makeText(activity, "ivFavorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }

    override fun lazyLoad() {
        mPresenter.getBanner()
        mPresenter.getHomeArticles(mCurPage - 1)
    }

    override fun showLoading() {
        Toast.makeText(activity, "开始加载$mCurPage", Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoading() {
        mLoading = false
    }

    override fun showError(t: String) {
        Toast.makeText(activity, t, Toast.LENGTH_SHORT).show()
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

    fun loadMoreData(){
        mPresenter.getHomeArticles(mCurPage)
    }

    override fun showHomeArticles(articleModel: ArticleModel) {
        articleModel.run {
            mCurPage = curPage
            mPageCount = pageCount
            val s = articleModel.datas.map {
                it.copy(title = it.title.replace("&mdash;", "-"))
            }
            if (mCurPage == 1){
                mAdapter.addNewData(s.toMutableList())
            }else {
                mAdapter.addData(s.toMutableList())
            }
        }
    }
}