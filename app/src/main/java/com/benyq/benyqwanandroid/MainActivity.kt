package com.benyq.benyqwanandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import com.benyq.benyqwanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_toolbar.*

class MainActivity : BaseActivity() {

    private lateinit var tvTitle: TextView

    override fun layoutId() =  R.layout.activity_main

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
        }
        return true
    }


}
