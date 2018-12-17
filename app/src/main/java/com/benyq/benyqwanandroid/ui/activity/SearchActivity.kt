package com.benyq.benyqwanandroid.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.HotWordModel
import com.benyq.benyqwanandroid.api.model.QueryModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.contract.SearchActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.SearchActivityPresenter
import com.benyq.benyqwanandroid.ui.adapter.SearchArticlesAdapter
import com.benyq.benyqwanandroid.ui.adapter.SearchHistoryAdapter
import com.benyq.benyqwanandroid.ui.adapter.SearchHotWordsAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

@Route(path = ARouterPath.pathSearchActivity)
class SearchActivity : BaseActivity(), SearchActivityContract.View {

    @Inject
    lateinit var mPresenter: SearchActivityPresenter

    private val mHotWordAdapter by lazy { SearchHotWordsAdapter(this) }
    private val mHistoryAdapter by lazy { SearchHistoryAdapter(this) }
    private val mSearchArticleAdapter by lazy { SearchArticlesAdapter(this) }
    private val mSearchView by lazy { SearchView(this) }

    private var mLoading: Boolean = false
    private var mSearchKey = ""
    private var mCurPage = 1
    private var mPageCount = 100

    override fun layoutId() = R.layout.activity_search

    override fun initView() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        rvHotWord.run {
            val flexboxLayoutManager = FlexboxLayoutManager(context)
            flexboxLayoutManager.flexDirection = FlexDirection.ROW
            flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
            flexboxLayoutManager.flexWrap = FlexWrap.WRAP
            layoutManager = flexboxLayoutManager
            adapter = mHotWordAdapter
        }
        mHotWordAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                mSearchView.setQuery(mHotWordAdapter.mData[position].name, true)
            }
        })

        tvClean.setOnClickListener {
            mHistoryAdapter.clearData()
            CacheManager.searchHistory = mutableListOf()
        }

        rvQuery.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mSearchArticleAdapter
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
                            loadMoreDate()
                        }
                    }
                }
            })
        }
        mSearchArticleAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val queryData = mSearchArticleAdapter.mData[position]
                ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                        .withString("url", queryData.link)
                        .withString("title", queryData.title)
                        .withInt("id", queryData.id)
                        .navigation()
            }
        })

        rvHistory.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mHistoryAdapter
        }
        mHistoryAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val history = mHistoryAdapter.mData[position]
                mSearchView.setQuery(history, true)
            }
        })
        mHistoryAdapter.addData(CacheManager.searchHistory)
    }

    override fun initData() {
        mPresenter.getHotWords()
    }

    override fun initTheme() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_group_customer_search, AMv.menu)
        setSearchView(AMv.menu)
        return super.onCreateOptionsMenu(AMv.menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun showHotWords(data: List<HotWordModel>) {
        mHotWordAdapter.addNewData(data.toMutableList())
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        mLoading = false
    }

    override fun showError(t: String) {
    }

    override fun showSearch(data: QueryModel) {
        data.run {
            mCurPage = curPage
            mPageCount = pageCount
            val s = data.datas.map {
                it.copy(title = Html.fromHtml(it.title).toString())
            }
            if (mCurPage == 1){
                mSearchArticleAdapter.addNewData(s.toMutableList())
            }else {
                mSearchArticleAdapter.addData(s.toMutableList())
            }
        }
    }

    private fun setSearchView(menu: Menu) {
        val item = menu.findItem(R.id.action_search)

        mSearchView.onActionViewExpanded()// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        mSearchView.isIconified = false//输入框内icon不显示
        mSearchView.setIconifiedByDefault(true)//设置展开后图标的样式,这里只有两种,false一种图标在搜索框外,true一种在搜索框内
        mSearchView.requestFocus()//输入焦点
        mSearchView.isSubmitButtonEnabled = true//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
        mSearchView.isFocusable = true//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        mSearchView.requestFocusFromTouch()//模拟焦点点击事件
        mSearchView.queryHint = "请输入搜索词"
        val ivSubmit: ImageView = mSearchView.findViewById(R.id.search_go_btn)
        //这样就可以修改图片了
        ivSubmit.setImageResource(R.drawable.ic_search)
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
//                if (TextUtils.isEmpty(query)){
//                    llHotAndHistory.visibility = View.VISIBLE
//                    rvQuery.visibility = View.GONE
//                }else {
//                    llHotAndHistory.visibility = View.GONE
//                    rvQuery.visibility = View.VISIBLE
//                }
                llHotAndHistory.visibility = View.VISIBLE
                rvQuery.visibility = View.GONE
                return true
            }

            override fun onQueryTextSubmit(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)){
                    llHotAndHistory.visibility = View.VISIBLE
                    rvQuery.visibility = View.GONE
                }else {
                    searchArticles(newText)
                }
                return true
            }

        })
        item.actionView = mSearchView
    }

    private fun searchArticles(query: String?) {
        llHotAndHistory.visibility = View.GONE
        rvQuery.visibility = View.VISIBLE
        mSearchArticleAdapter.clearData()
        mSearchKey = query!!
        mPresenter.searchArticle(0, mSearchKey)
        val historyList = CacheManager.searchHistory
        if (historyList.size == 7){
            historyList.removeAt(CacheManager.searchHistory.size - 1)
        }
        historyList.add(0, query)
        CacheManager.searchHistory = historyList
        mHistoryAdapter.addNewData(historyList)
    }

    fun loadMoreDate(){
        mPresenter.searchArticle(mCurPage, mSearchKey)
    }
}
