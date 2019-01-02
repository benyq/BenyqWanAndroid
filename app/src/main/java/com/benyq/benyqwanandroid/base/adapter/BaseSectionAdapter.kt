package com.benyq.benyqwanandroid.base.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
abstract class BaseSectionAdapter<K, T : SectionEntity<K>>(private val mContext: Context, private val headLayoutId: Int, private val itemLayoutId: Int)
    : BaseAdapter<T>(mContext) {

    private val groupType = 0x00
    private val itemType = 0x01


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val view = if (viewType == groupType){
            LayoutInflater.from(parent.context).inflate(headLayoutId, parent, false)
        }else {
            LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        }
        return BaseHolder(mContext, view)
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        if (holder.itemViewType == groupType){
            convertHead(holder, position, mData[position])
        }else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData[position].isHeader){
            groupType
        }else {
            itemType
        }
    }

//    fun addNewData(data:MutableList<T>){
//        if (data.size > 0 ){
//            mData.clear()
//            mData.addAll(data)
//            notifyDataSetChanged()
//        }
//    }
//
//    fun addData(data:MutableList<T>){
//        if (data.size > 0 ){
//            mData.addAll(data)
//            notifyDataSetChanged()
//        }
//    }
//
//    fun addData(data: T){
//        mData.add(data)
//        notifyItemInserted(mData.size - 1)
//    }
    //abstract fun convert(holder: BaseHolder, position: Int, bean: K)
    abstract fun convertHead(holder: BaseHolder, position: Int, bean: T)
}