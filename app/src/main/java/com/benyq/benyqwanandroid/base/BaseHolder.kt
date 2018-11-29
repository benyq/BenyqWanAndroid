package com.benyq.benyqwanandroid.base

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.util.SparseArray
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide









/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/29
 */
class BaseHolder(private val mContext: Context, baseView: View): RecyclerView.ViewHolder(baseView) {

    private val mViews = SparseArray<View>()
    private val mLayoutId: Int = 0

    fun <V : View> getView(viewId: Int): V {
        val view = mViews.get(viewId) ?: itemView.findViewById(viewId)
        mViews.put(viewId, view)
        return view as V
    }

    fun setText(@IdRes viewId: Int, text: String): BaseHolder{
        val view: TextView = getView(viewId)
        view.text = text
        return this
    }

    fun setImageUrl(@IdRes viewId: Int, imgUrl: String): BaseHolder {
        val view: ImageView = getView(viewId)
        Glide.with(mContext).load(imgUrl).into(view)
        return this
    }
}