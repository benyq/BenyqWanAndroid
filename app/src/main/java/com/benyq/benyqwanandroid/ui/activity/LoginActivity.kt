package com.benyq.benyqwanandroid.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.LoginContract
import com.benyq.benyqwanandroid.mvp.presenter.LoginPresenter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {
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
    lateinit var mPresenter: LoginPresenter

    override fun initData() {
        AndroidInjection.inject(this)

    }

    override fun initView() {
        btnLogin.setOnClickListener {
            val userName = etUserName.text.toString()
            val password = etPassword.text.toString()
            mPresenter.login(LoginParam(userName, password))
        }
    }

    override fun initTheme() {
    }

    override fun layoutId() = R.layout.activity_login

}
