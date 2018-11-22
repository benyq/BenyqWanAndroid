package com.benyq.benyqwanandroid.ui.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.MainActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.LoginActivityPresenter
import com.benyq.benyqwanandroid.mvp.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View{
    override fun showSuccess() {

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    @Inject
    lateinit var mPresenter: MainActivityPresenter

    private lateinit var tvTitle: TextView

    override fun layoutId() = R.layout.activity_main

    override fun initData() {

    }

    override fun initView() {
        setSupportActionBar(toolBar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        tvTitle = navigationView.getHeaderView(0).findViewById(R.id.tvTitle)
        tvTitle.text = "dsdsds"
    }


    override fun initTheme() {
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                //drawerLayout.openDrawer(Gravity.START)
                mPresenter.addTodo(AddTodoParam("新增标题", "新增内容", "2018-11-22", 0))
            }
            R.id.search ->{
                Intent(this@MainActivity, LoginActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

}
