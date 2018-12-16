package com.benyq.benyqwanandroid.ui.activity

import android.support.v7.widget.SearchView
import android.text.TextUtils
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
import com.benyq.benyqwanandroid.mvp.contract.SearchActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.SearchActivityPresenter
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

            }
        })

        tvClean.setOnClickListener {
            mHistoryAdapter.clearData()
        }
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
    }

    override fun showError(t: String) {
    }

    override fun showSearch(data: QueryModel) {

    }

    private fun setSearchView(menu: Menu) {
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView(this)
        searchView.onActionViewExpanded()// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        searchView.isIconified = false//输入框内icon不显示
        searchView.setIconifiedByDefault(true)//设置展开后图标的样式,这里只有两种,false一种图标在搜索框外,true一种在搜索框内
        searchView.requestFocus()//输入焦点
        searchView.isSubmitButtonEnabled = true//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
        searchView.isFocusable = true//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        searchView.requestFocusFromTouch()//模拟焦点点击事件
        searchView.queryHint = "请输入搜索词"
        val ivSubmit: ImageView = searchView.findViewById(R.id.search_go_btn)
        //这样就可以修改图片了
        ivSubmit.setImageResource(R.drawable.ic_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                if (TextUtils.isEmpty(query)){
                    llHotAndHistory.visibility = View.VISIBLE
                }else {

                }
                return true
            }

            override fun onQueryTextSubmit(newText: String?): Boolean {

                return true
            }

        })
        item.actionView = searchView
    }
}
