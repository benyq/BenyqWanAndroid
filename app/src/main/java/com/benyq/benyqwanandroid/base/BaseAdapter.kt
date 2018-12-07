package com.benyq.benyqwanandroid.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View


/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/29
 */
abstract class BaseAdapter<T>(private val mContext: Context, private val layoutId: Int ): RecyclerView.Adapter<BaseHolder>() {

    var mData = mutableListOf<T>()
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    var mOnItemChildClickListener: OnItemChildClickListener? = null
    var mOnItemChildLongClickListener: OnItemChildLongClickListener? = null

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
        convert(holder, position, mData[position])
    }

    abstract fun convert(holder: BaseHolder, position: Int, bean: T)

    fun addNewData(data:MutableList<T>){
        if (data.size > 0 ){
            mData.clear()
            mData.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun addData(data:MutableList<T>){
        if (data.size > 0 ){
            mData.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun addData(data: T){
        mData.add(data)
        notifyItemInserted(mData.size - 1)
    }


    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        this.mOnItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener){
        this.mOnItemLongClickListener = itemLongClickListener
    }


    fun setOnItemChildClickListener(itemChildClickListener: OnItemChildClickListener){
        this.mOnItemChildClickListener = itemChildClickListener
    }

    fun setOnItemChildLongClickListener(itemChildLongClickListener: OnItemChildLongClickListener){
        this.mOnItemChildLongClickListener = itemChildLongClickListener
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int)
    }

    interface OnItemChildClickListener{
        fun onItemChildClick(view: View, position: Int)
    }

    interface OnItemChildLongClickListener{
        fun onItemChildLongClick(view: View, position: Int)
    }
}