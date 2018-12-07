package com.benyq.benyqwanandroid.ui.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.mvp.contract.ArticleActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.ArticleActivityPresenter
import com.benyq.benyqwanandroid.ui.dialog.ArticleDialog
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.common_head.*
import javax.inject.Inject
import android.content.Context.CLIPBOARD_SERVICE
import android.content.ClipData






@Route(path = ARouterPath.pathArticleActivity)
class ArticleActivity : BaseActivity(), ArticleActivityContract.View{

    @Autowired(name = "url")
    @JvmField var url: String = "http://wanandroid.com"

    @Autowired(name = "title")
    @JvmField var title: String = ""

    @Autowired(name = "favorite")
    @JvmField var favorite: Boolean = false

    @Autowired(name = "id")
    @JvmField var articleId: Int = -1

    @Autowired(name = "originId")
    @JvmField var originId: Int = -1

    @Inject
    lateinit var mPresenter: ArticleActivityPresenter

    private lateinit var mWebView: WebView

    override fun layoutId() = R.layout.activity_article

//    private val mArticleDialog by lazy { ArticleDialog.getInstance(favorite) }
    private lateinit var mArticleDialog: ArticleDialog


    override fun initView() {
        ARouter.getInstance().inject(this)
        mArticleDialog = ArticleDialog.getInstance(favorite)
        toolbar_back.setOnClickListener { finish() }
        toolbar_title.text = title
        toolbar_right.visibility = View.VISIBLE
        toolbar_right.setOnClickListener {
            if (!mArticleDialog.isVisible){
                mArticleDialog.show(supportFragmentManager, "dialog")
            }
        }

        mArticleDialog.addCallBack(object : ArticleDialog.Callback {
            override fun response(id: Int) {
                when(id){
                    R.id.tvFavorite -> {
                        if (favorite){
                            if (originId != -1){
                                mPresenter.unCollectArticle(articleId, originId)
                            }else{
                                mPresenter.unCollectArticle(articleId)
                            }
                        }else{
                            mPresenter.collectArticle(articleId)
                        }
                        Toast.makeText(this@ArticleActivity, "tvFavorite", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvShare -> {
                        Toast.makeText(this@ArticleActivity, "tvShare", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvBrowser -> {
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                        Toast.makeText(this@ArticleActivity, "tvBrowser", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvLink -> {
                        val cm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText(null, url)
                        cm.primaryClip = clipData
                        Toast.makeText(this@ArticleActivity, "tvLink", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvRefresh -> {
                        mWebView.loadUrl(url)
                        Toast.makeText(this@ArticleActivity, "tvRefresh", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

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
                title.let {
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

    override fun showUnCollectResponse() {
        favorite = false
        mArticleDialog.setFavoriteState(false)
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }

    override fun showCollectArticleResponse() {
        favorite = true
        mArticleDialog.setFavoriteState(true)
    }

}
