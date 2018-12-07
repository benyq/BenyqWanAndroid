package com.benyq.benyqwanandroid.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.LoginModel
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.base.IntentErtra
import com.benyq.benyqwanandroid.local.CacheManager
import com.benyq.benyqwanandroid.mvp.contract.RegisterActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.RegisterActivityPresenter
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.common_head.*
import javax.inject.Inject

@Route(path = ARouterPath.pathRegisterActivity)
class RegisterActivity : BaseActivity(), RegisterActivityContract.View, View.OnClickListener {

    @Inject
    lateinit var mPresenter: RegisterActivityPresenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.toolbar_back -> {
                finish()
            }
            R.id.btnRegister -> {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val rePassword = etRePassword.text.toString()
                if (username.isEmpty()){

                }
                if (password.isEmpty()){

                }
                if (rePassword.isEmpty()){

                }

                if (password != rePassword){

                }
                mPresenter.register(username, password, rePassword)

            }
            R.id.ivPasswordDelete -> etPassword.setText("")
            R.id.ivUsernameDelete -> etUsername.setText("")
            R.id.ivRePasswordDelete -> etRePassword.setText("")
        }
    }

    override fun showRegisterResponse(loginModel: LoginModel) {
        ARouter.getInstance().build(ARouterPath.pathMainActivity).navigation()
        finish()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }

    override fun initView() {
        toolbar_title.text = resources.getText(R.string.register)
        toolbar_back.setOnClickListener(this)
        ivPasswordDelete.setOnClickListener(this)
        ivUsernameDelete.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun initTheme() {
    }

    override fun layoutId() = R.layout.activity_register

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
