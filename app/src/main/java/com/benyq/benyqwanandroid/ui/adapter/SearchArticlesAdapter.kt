package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.QueryModel
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.adapter.BaseHolder

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/17
 */
class SearchArticlesAdapter(mContext: Context): BaseAdapter<QueryModel.Data>(mContext, R.layout.item_search) {

    override fun convert(holder: BaseHolder, position: Int, bean: QueryModel.Data) {
        with(bean) {
            holder.setText(R.id.tvAuthor, author)
                    .setText(R.id.tvTitle, title)
                    .setText(R.id.tvNiceDate, niceDate)
                    .setText(R.id.tvClassification, "$superChapterName / $chapterName")
        }
    }
}