package com.benyq.benyqwanandroid.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.SimpleActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.common_head.*

@Route(path = ARouterPath.pathAboutActivity)
class AboutActivity : SimpleActivity() {

    override fun layoutId() = R.layout.activity_about
    override fun initView() {
        toolbar_title.setText(R.string.about)
        llApi.setOnClickListener {
            ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                    .withString("url", "http://wanandroid.com").navigation()
        }

//        llAuthor.setOnClickListener {
//            ARouter.getInstance().build(ARouterPath.pathArticleActivity)
//                    .withString("url", "http://wanandroid.com").navigation()
//        }

        llGithub.setOnClickListener {
            ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                    .withString("url", "https://github.com/benyq").navigation()
        }

        toolbar_back.setOnClickListener {
            finish()
        }
    }


    override fun initTheme() {
    }

}
