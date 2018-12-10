package com.benyq.benyqwanandroid.ui.activity

import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.HotWordMoedel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.SearchActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.SearchActivityPresenter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

@Route(path = ARouterPath.pathSearchActivity)
class SearchActivity : BaseActivity(), SearchActivityContract.View {

    @Inject
    lateinit var mPresenter: SearchActivityPresenter

    override fun layoutId() = R.layout.activity_search

    override fun initView() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun initData() {
        mPresenter.getHotWords()
    }

    override fun initTheme() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_group_customer_search, menu)
//        val searchItem = menu.findItem(R.id.action_search)
//        val searchView = searchItem.actionView as SearchView
//        searchView.run {
//            setIconifiedByDefault(true)
//            isIconified = false
//            isSubmitButtonEnabled = true
//        }
        setSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showHotWords(data: List<HotWordMoedel>) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
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
        searchView.queryHint = "请输入书名或作者名      "
        val ivSubmit: ImageView = searchView.findViewById(R.id.search_go_btn)
        //这样就可以修改图片了
        ivSubmit.setImageResource(R.drawable.ic_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(newText: String?): Boolean {
                return true
            }

        })

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                tvClear.setEnabled(true);
//                saveSearchHistory(query);
//                mPresenter.getSearchResultList(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                LogUtils.e(newText);
//                if (newText.length() == 0){
//                    showHistory();
//                }else {
//                    mPresenter.getAutoCompleteResultList(newText);
//                }
//                return true;
//            }
//        });
        item.actionView = searchView
    }
}
