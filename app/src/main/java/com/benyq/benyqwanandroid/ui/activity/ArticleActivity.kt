package com.benyq.benyqwanandroid.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.mvp.contract.ArticleActivityContract
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.common_head.*


@Route(path = ARouterPath.pathArticleActivity)
class ArticleActivity : BaseActivity(), ArticleActivityContract.View {

    @Autowired(name = "url")
    @JvmField var url: String = "http://wanandroid.com"

    @Autowired(name = "title")
    @JvmField var title: String = ""

    private lateinit var mWebView: WebView

    override fun layoutId() = R.layout.activity_article

    override fun initView() {
        ARouter.getInstance().inject(this)

        toolbar_back.setOnClickListener { finish() }

        toolbar_title.text = title

        toolbar_right.visibility = View.VISIBLE

        mWebView = WebView(this)
        val webSetting = mWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.useWideViewPort = true// 设置此属性，可任意比例缩放
        webSetting.loadWithOverviewMode = true//适配
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        webSetting.builtInZoomControls = true
        webSetting.setSupportZoom(true)
        webSetting.loadWithOverviewMode = true
        webSetting.setGeolocationEnabled(true)
        webSetting.domStorageEnabled = true//false导致js不能转跳
        webSetting.pluginState = WebSettings.PluginState.ON
        mWebView.requestFocus()
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        mWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (url.startsWith("http") || url.startsWith("https")){
                    mWebView.loadUrl(url)
                }else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }catch (e: Exception){
                        return true
                    }
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                title?.let {
                    if (title.isEmpty()){
                        toolbar_title.text = it
                    }
                }
            }
        }
        mWebView.webChromeClient = object : WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if (title.isEmpty()){
                        toolbar_title.text = it
                    }
                }
            }
        }

        flContainer.addView(mWebView)
        mWebView.loadUrl(url)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        } else {
            onBackPressed()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initData() {
    }

    override fun initTheme() {
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }

}
