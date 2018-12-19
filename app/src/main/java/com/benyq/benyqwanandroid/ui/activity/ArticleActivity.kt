package com.benyq.benyqwanandroid.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.webkit.*
import android.widget.Button
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.ArticleActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.ArticleActivityPresenter
import com.benyq.benyqwanandroid.ui.dialog.ArticleDialog
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.common_head.*
import javax.inject.Inject


@Route(path = ARouterPath.pathArticleActivity)
class ArticleActivity : BaseActivity(), ArticleActivityContract.View{

    @Autowired(name = "url")
    @JvmField var mUrl: String = "http://wanandroid.com"

    @Autowired(name = "title")
    @JvmField var mTitle: String = ""

    @Autowired(name = "favorite")
    @JvmField var mFavorite: Boolean = false

    @Autowired(name = "id")
    @JvmField var mArticleId: Int = -1

    @Autowired(name = "originId")
    @JvmField var mOriginId: Int = -1

    @Inject
    lateinit var mPresenter: ArticleActivityPresenter

    private lateinit var mWebView: WebView

    val mWindowManager by lazy { getSystemService(WINDOW_SERVICE) as WindowManager }

    private val mLayoutParams by lazy { WindowManager.LayoutParams() }

    override fun layoutId() = R.layout.activity_article

    private val OVERLAY_PERMISSION_REQ_CODE = 1

//    private val mArticleDialog by lazy { ArticleDialog.getInstance(favorite) }
    private lateinit var mArticleDialog: ArticleDialog

    private val mFloatButton by lazy { Button(this) }

    override fun initView() {
        ARouter.getInstance().inject(this)
        mArticleDialog = ArticleDialog.getInstance(mFavorite)
        toolbar_back.setOnClickListener { finish() }
        toolbar_title.text = mTitle
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
                        if (mFavorite){
                            if (mOriginId != -1){
                                mPresenter.unCollectArticle(mArticleId, mOriginId)
                            }else{
                                mPresenter.unCollectArticle(mArticleId)
                            }
                        }else{
                            mPresenter.collectArticle(mArticleId)
                        }
                        Toast.makeText(this@ArticleActivity, "tvFavorite", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvShare -> {
                        Toast.makeText(this@ArticleActivity, "tvShare", Toast.LENGTH_SHORT).show()
                        Intent(Intent.ACTION_SEND).run {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, mUrl)
                            putExtra(Intent.EXTRA_SUBJECT, mTitle)
                            startActivity(this)
                        }
                    }
                    R.id.tvBrowser -> {
                        val uri = Uri.parse(mUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                        Toast.makeText(this@ArticleActivity, "tvBrowser", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvLink -> {
                        val cm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText(null, mUrl)
                        cm.primaryClip = clipData
                        Toast.makeText(this@ArticleActivity, "tvLink", Toast.LENGTH_SHORT).show()
                    }
                    R.id.tvRefresh -> {
                        mWebView.loadUrl(mUrl)
                        Toast.makeText(this@ArticleActivity, "tvRefresh", Toast.LENGTH_SHORT).show()
                    }

                    R.id.tvFloat -> {
                        if (requestDrawOverLays()){
                            showFloatButton()
                        }
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
//        webSetting.pluginState = WebSettings.PluginState.ON
        mWebView.requestFocus()
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        mWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (mUrl.startsWith("http") || mUrl.startsWith("https")){
                    mWebView.loadUrl(mUrl)
                }else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
                        startActivity(intent)
                    }catch (e: Exception){
                        return true
                    }
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mTitle.let {
                    if (!TextUtils.isEmpty(mTitle)){
                        toolbar_title.text = it
                    }
                }
            }
        }
        mWebView.webChromeClient = object : WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    toolbar_title.text = it
                }
            }
        }

        flContainer.addView(mWebView)
        mWebView.loadUrl(mUrl)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestDrawOverLays()){
                showFloatButton()
            }
        }
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
        mFavorite = false
        mArticleDialog.setFavoriteState(false)
    }

    override fun showError(t: String) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
    }

    override fun showCollectArticleResponse() {
        mFavorite = true
        mArticleDialog.setFavoriteState(true)
    }

    private fun requestDrawOverLays(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true
        }else {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent,OVERLAY_PERMISSION_REQ_CODE)
                return false
            }
            return true
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showFloatButton(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        mFloatButton.text = "悬浮窗"

        mFloatButton.setOnTouchListener(object : View.OnTouchListener{
            var isMove = false
            var lastX = 0.0
            var lastY = 0.0
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> {
                        isMove = false
                        lastX = event.rawX.toDouble()
                        lastY = event.rawY.toDouble()
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val x = event.rawX.toDouble()
                        val y = event.rawY.toDouble()
                        if (x != lastX || y != lastY){
                            mLayoutParams.x = (x - 100).toInt()
                            mLayoutParams.y = (y - 200).toInt()
                            mWindowManager.updateViewLayout(mFloatButton, mLayoutParams)
                            lastX = x
                            lastY = y
                            isMove = true
                        }else {
                            isMove = false
                        }

                    }

                    MotionEvent.ACTION_UP -> {
                        if (!isMove){
                            ARouter.getInstance().build(ARouterPath.pathArticleActivity)
                                    .withString("url", mUrl)
                                    .withString("title", mTitle)
                                    .withInt("id", mArticleId)
                                    .navigation()
                            isMove = false
                        }
                    }
                }
                return true
            }

        })

        mLayoutParams.format = PixelFormat.RGBA_8888   //窗口透明
        mLayoutParams.gravity = Gravity.START or Gravity.TOP  //窗口位置
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mLayoutParams.width = 200
        mLayoutParams.height = 200
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        mLayoutParams.x = 0
        mLayoutParams.y = size.y / 2
        windowManager.addView(mFloatButton, mLayoutParams)
    }
}
