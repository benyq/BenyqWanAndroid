package com.benyq.benyqwanandroid.ui.activity

import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.LoginActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.LoginActivityPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginActivityContract.View {
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Inject
    lateinit var mPresenter: LoginActivityPresenter

    override fun initData() {

    }

    override fun initView() {
        btnLogin.setOnClickListener {
            val userName = etUserName.text.toString()
            val password = etPassword.text.toString()
            mPresenter.login(LoginParam("benyq&susan", "yezijian520"))
        }
    }

    override fun initTheme() {
    }

    override fun layoutId() = R.layout.activity_login

}
