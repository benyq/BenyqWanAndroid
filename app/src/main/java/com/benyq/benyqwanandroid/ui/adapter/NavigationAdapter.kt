package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.base.adapter.BaseHolder
import com.benyq.benyqwanandroid.base.adapter.BaseSectionAdapter
import com.benyq.benyqwanandroid.base.adapter.SectionEntity
import kotlinx.android.synthetic.main.item_head.view.*

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
class NavigationAdapter(mContext: Context, headLayoutId: Int, itemLayoutId: Int)
    : BaseSectionAdapter<NavigationModel.Article, NaviSectionEntity>(mContext, headLayoutId, itemLayoutId){

    override fun convert(holder: BaseHolder, position: Int, bean: NaviSectionEntity) {
        holder.setText(R.id.tvNavi, bean.data?.title ?: "默认")
    }

    override fun convertHead(holder: BaseHolder, position: Int, bean: NaviSectionEntity) {
        holder.setText(R.id.tvHead, bean.head)
    }

}