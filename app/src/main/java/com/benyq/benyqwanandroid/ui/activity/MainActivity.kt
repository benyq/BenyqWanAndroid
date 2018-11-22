package com.benyq.benyqwanandroid.ui.activity

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.R.id.*
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.LoginContract
import com.benyq.benyqwanandroid.mvp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_toolbar.*

class MainActivity : BaseActivity(){

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
                drawerLayout.openDrawer(Gravity.START)
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
