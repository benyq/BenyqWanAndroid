package com.benyq.benyqwanandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.Preference
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.base.Constants
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.contract.MainActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.MainActivityPresenter
import com.benyq.benyqwanandroid.ui.fragment.HomeFragment
import com.benyq.benyqwanandroid.ui.fragment.NavigationFragment
import com.benyq.benyqwanandroid.ui.fragment.ProjectFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_toolbar.*
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector


@Route(path = ARouterPath.pathMainActivity)
class MainActivity : BaseActivity(), MainActivityContract.View, HasSupportFragmentInjector {

    @Inject
    lateinit var mPresenter: MainActivityPresenter

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    private var currentIndex = 0

    private lateinit var mTvUsername: TextView
    private lateinit var mIvIcon: ImageView
    private lateinit var mBtnLogin: Button

    private lateinit var mHomeFragment: HomeFragment
    private lateinit var mProjectFragment: ProjectFragment
    private lateinit var mNavigationFragment: NavigationFragment


    override fun showLogout() {
        //清除cookie
        CacheManager.cookies_pref = mutableSetOf()
        CacheManager.username = ""
        changeLoginState()
    }

    override fun showSuccess() {

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }


    override fun layoutId() = R.layout.activity_main

    override fun initData() {

    }

    override fun initView() {
        setSupportActionBar(toolBar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        mTvUsername = navigationView.getHeaderView(0).findViewById(R.id.tvUsername)
        mIvIcon = navigationView.getHeaderView(0).findViewById(R.id.ivIcon)
        mBtnLogin = navigationView.getHeaderView(0).findViewById(R.id.btnLogin)
        Glide.with(this).load("https://avatars0.githubusercontent.com/u/30992974?s=460&v=4")
                .apply(RequestOptions.circleCropTransform()).into(mIvIcon)
        changeLoginState()
        mBtnLogin.setOnClickListener {
            ARouter.getInstance().build(ARouterPath.pathLoginActivity).navigation()
        }

        mHomeFragment = HomeFragment.getInstance()
        mProjectFragment = ProjectFragment.getInstance()
        mNavigationFragment = NavigationFragment.getInstance()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_favorite -> {
                    ARouter.getInstance().build(ARouterPath.pathFavoriteArticleActivity).navigation()
                }

                R.id.action_Logout -> {
                    mPresenter.logout()
                }
            }
             true
        }

        fragmentManager.beginTransaction().apply {
            add(R.id.flContainer, mHomeFragment)
            add(R.id.flContainer, mProjectFragment)
            add(R.id.flContainer, mNavigationFragment)
        }.commit()

        navigation.setOnNavigationItemSelectedListener {
            setFragment(it.itemId)
            return@setOnNavigationItemSelectedListener when(it.itemId){
                R.id.action_navigation -> {
                    true
                }
                R.id.action_project -> {
                    true
                }

                R.id.action_home -> {
                    true
                }
                else -> false
            }
        }
        navigation.selectedItemId = R.id.action_home


    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun initTheme() {
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                drawerLayout.openDrawer(Gravity.START)
                //mPresenter.addTodo(AddTodoParam("新增标题", "新增内容", "2018-11-22", 0))
            }
            R.id.search -> {
                ARouter.getInstance().build(ARouterPath.pathSearchActivity).navigation()
            }

        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    /**
     * 防止重叠
     */
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is HomeFragment -> mHomeFragment = fragment
            is ProjectFragment -> mProjectFragment = fragment
            is NavigationFragment -> mNavigationFragment = fragment
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        changeLoginState()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    private fun changeLoginState() {
        if (CacheManager.username.isEmpty()) {
            mBtnLogin.visibility = View.VISIBLE
            mTvUsername.visibility = View.GONE
        } else {
            mBtnLogin.visibility = View.GONE
            mTvUsername.visibility = View.VISIBLE
            mTvUsername.text = CacheManager.username
        }
    }

    private fun setFragment(id: Int){
        fragmentManager.beginTransaction().apply {
            hide(mHomeFragment)
            hide(mProjectFragment)
            hide(mNavigationFragment)
            when(id){
                R.id.action_home -> {
                    setTitle(R.string.nav_main_home)
                    show(mHomeFragment)
                }
                R.id.action_project -> {
                    setTitle(R.string.nav_main_project)
                    show(mProjectFragment)
                }
                R.id.action_navigation -> {
                    setTitle(R.string.nav_main_navigation)
                    show(mNavigationFragment)
                }
            }
        }.commit()
    }

}
