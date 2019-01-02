package com.benyq.benyqwanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.benyq.benyqwanandroid.R
import dagger.android.AndroidInjection

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/20
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        initTheme()
        initStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initView()
        initData()
//        ThemeManager.registerThemeChangeListener(this)
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()


    /**
     * 初始化状态栏
     */
    protected fun initStatusBar(){
    }

    /**
     * 设置主题
     */
    abstract fun initTheme()

    //关闭软键盘
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    /**
     * 初始化Toolbar
     *
     * @param title
     * @param homeAsUpEnabled
     */
    fun initToolbar(toolbar: Toolbar, title: String, homeAsUpEnabled: Boolean) {
//        val textView: TextView = toolbar.findViewById(R.id.tvTitle)
//        textView.text = title
        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (homeAsUpEnabled) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}