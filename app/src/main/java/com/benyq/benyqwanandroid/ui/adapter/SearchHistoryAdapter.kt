package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.QueryModel
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.adapter.BaseHolder

/**
 * @author benyq
 * @date 18-12-16 21:34
 * @E-mail： 1520063035@qq.com
 * @description
 */
class SearchHistoryAdapter(mContext: Context): BaseAdapter<String>(mContext, R.layout.item_search_history){

    override fun convert(holder: BaseHolder, position: Int, bean: String) {
        holder.setText(R.id.tvSearch, bean)
    }
}