package com.benyq.benyqwanandroid.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/29
 */
abstract class BaseAdapter<T>(private val mContext: Context, private val layoutId: Int ): RecyclerView.Adapter<BaseHolder>() {

    protected var mData: List<T> = mutableListOf()
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BaseHolder(mContext, view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        mOnItemClickListener?.run {
            holder.itemView.setOnClickListener {
                onItemClick(holder.itemView, position)
            }
        }

        mOnItemLongClickListener?.run {
            holder.itemView.setOnLongClickListener {
                onItemLongClick(holder.itemView, position)
                false
            }
        }
    }

    abstract fun convert(holder: BaseHolder, position: Int, bean: T)

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        this.mOnItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener){
        this.mOnItemLongClickListener = itemLongClickListener
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int)
    }
}