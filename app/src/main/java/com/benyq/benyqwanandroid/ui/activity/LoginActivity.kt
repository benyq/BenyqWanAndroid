package com.benyq.benyqwanandroid.ui.activity

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.api.param.LoginParam
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.contract.LoginActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.LoginActivityPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_head.*
import javax.inject.Inject

@Route(path = ARouterPath.pathLoginActivity)
class LoginActivity : BaseActivity(), LoginActivityContract.View, View.OnClickListener {

    @Inject
    lateinit var mPresenter: LoginActivityPresenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.toolbar_back -> finish()
            R.id.ivPasswordDelete -> etPassword.setText("")
            R.id.ivUsernameDelete -> etUsername.setText("")
            R.id.btnLogin -> {
                val userName = etUsername.text.toString()
                val password = etPassword.text.toString()
                if (userName.isEmpty()){

                }
                if (password.isEmpty()){

                }
                mPresenter.login(LoginParam(userName, password))
            }
            R.id.tvRegister -> {
                ARouter.getInstance().build(ARouterPath.pathRegisterActivity).navigation()
            }
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(loginModel: LoginModel) {
        ARouter.getInstance().build(ARouterPath.pathMainActivity).navigation()
    }


    override fun initData() {

    }

    override fun initView() {
        toolbar_title.text = resources.getText(R.string.login)
        toolbar_back.setOnClickListener(this)
        ivPasswordDelete.setOnClickListener(this)
        ivUsernameDelete.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
    }

    override fun initTheme() {
    }

    override fun layoutId() = R.layout.activity_login

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}
