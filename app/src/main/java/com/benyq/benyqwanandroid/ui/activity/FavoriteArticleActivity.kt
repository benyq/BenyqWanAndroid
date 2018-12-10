package com.benyq.benyqwanandroid.ui.activity

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.FavoriteArticleActivityContract
import android.view.LayoutInflater
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.api.model.ArticleFavoriteModel
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.adapter.OnItemChildClickListener
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.mvp.presenter.FavoriteArticleActivityPresenter
import com.benyq.benyqwanandroid.ui.adapter.FavoriteArticleAdapter
import kotlinx.android.synthetic.main.activity_favorite_article.*
import kotlinx.android.synthetic.main.common_head.*
import javax.inject.Inject


@Route(path = ARouterPath.pathFavoriteArticleActivity)
class FavoriteArticleActivity : BaseActivity(), FavoriteArticleActivityContract.View {

    private val TAG = javaClass.simpleName
    private val mAdapter: FavoriteArticleAdapter by lazy { FavoriteArticleAdapter(this) }
    private var mCurPage = 1
    private var mPageCount = 100
    private var mLoading: Boolean = false

    @Inject
    lateinit var mPresenter: FavoriteArticleActivityPresenter

    override fun layoutId() = R.layout.activity_favorite_article

    override fun initView() {
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = "收藏列表"
        rvFavoriteArticle.layoutManager = LinearLayoutManager(this)
        rvFavoriteArticle.adapter = mAdapter
        rvFavoriteArticle.itemAnimator = DefaultItemAnimator()
        rvFavoriteArticle.addOnScrollListener(object : RecyclerView.OnScrollListener(){
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
                        loadMoreDate()
                    }
                }
            }
        })
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val article = mAdapter.mData[position]
                ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                        .withString("url", article.link)
                        .withString("title", article.title)
                        .withBoolean("favorite", true)
                        .withInt("id", article.id)
                        .withInt("originId", article.originId)
                        .navigation()
            }
        })
        mAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(view: View, position: Int) {
                Toast.makeText(this@FavoriteArticleActivity, "ivShare", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun initData() {
        mPresenter.getCollectedArticles(mCurPage - 1)
    }

    override fun initTheme() {

        LayoutInflaterCompat.setFactory2(layoutInflater, object : LayoutInflater.Factory2 {
            //'这个方法是Factory接口里面的，因为Factory2是继承Factory的'
            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                return null
            }

            //'这个方法是Factory2里面定义的方法'
            override fun onCreateView(parent: View?, name: String?, context: Context, attrs: AttributeSet): View? {
//                Log.e(TAG, "parent:$parent,name = $name")
//                val n = attrs.attributeCount
//                for (i in 0 until n) {
//                    Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i))
//                }
                return delegate.createView(parent, name, context, attrs)
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        mLoading = false
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }

    override fun showCollectedArticles(articleFavoriteModel: ArticleFavoriteModel) {
        articleFavoriteModel.run {
            mCurPage = curPage
            mPageCount = pageCount
            if (mCurPage == 1){
                mAdapter.addNewData(articleFavoriteModel.datas.toMutableList())
            }else {
                mAdapter.addData(articleFavoriteModel.datas.toMutableList())
            }
        }
    }

    fun loadMoreDate(){
        mPresenter.getCollectedArticles(mCurPage)
    }
}
