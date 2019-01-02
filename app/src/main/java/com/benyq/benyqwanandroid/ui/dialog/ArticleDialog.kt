package com.benyq.benyqwanandroid.ui.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.benyq.benyqwanandroid.R
import kotlinx.android.synthetic.main.dialog_article.*

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/3
 */
class ArticleDialog : DialogFragment(), View.OnClickListener {

    companion object {
        fun getInstance(favorite: Boolean): ArticleDialog{
            val articleDialog = ArticleDialog()
            articleDialog.favorite = favorite
            return articleDialog
        }

    }

    private var favorite:Boolean = false
    private var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.style_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvLink.setOnClickListener(this)
        tvBrowser.setOnClickListener(this)
        tvFavorite.setOnClickListener(this)
        tvRefresh.setOnClickListener(this)
        tvShare.setOnClickListener(this)
        tvFloat.setOnClickListener(this)
        setFavoriteState(favorite)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val window = it.window
            val lp = window!!.attributes
            lp.gravity = Gravity.BOTTOM // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT // 宽度持平
            window.attributes = lp
        }
    }


    fun addCallBack(callback: Callback){
        this.callback = callback
    }

    override fun onClick(v: View) {
        callback?.response(v.id)
        dismiss()
    }

    fun setFavoriteState(flag: Boolean){
        favorite = flag
        if (flag){
            val dra = resources.getDrawable(R.drawable.ic_favorite, null)
            dra.setBounds(0, 0, dra.minimumWidth, dra.minimumHeight)
            tvFavorite.setCompoundDrawables(null, dra, null, null)
            tvFavorite.text = "已收藏"
        }else {
            val dra = resources.getDrawable(R.drawable.ic_favorite_empty, null)
            dra.setBounds(0, 0, dra.minimumWidth, dra.minimumHeight)
            tvFavorite.setCompoundDrawables(null, dra, null, null)
            tvFavorite.text = "收藏"
        }
    }

    interface Callback {
        fun response(id: Int)
    }

}