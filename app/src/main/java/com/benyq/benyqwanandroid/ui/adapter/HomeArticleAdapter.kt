package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ArticleModel
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.adapter.BaseHolder

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/11/30
 */
class HomeArticleAdapter(mContext: Context)
    : BaseAdapter<ArticleModel.Article>(mContext, R.layout.item_home_article) {

    override fun convert(holder: BaseHolder, position: Int, bean: ArticleModel.Article) {
        bean.run {
            holder.setText(R.id.tvTitle, title)
                    .setText(R.id.tvAuthor, author)
                    .setText(R.id.tvNiceDate, niceDate)
                    .setText(R.id.tvClassification, "$superChapterName / $chapterName")
                    .addOnItemChildClickListener(R.id.ivShare, mOnItemChildClickListener)
                    .addOnItemChildClickListener(R.id.ivFavorite, mOnItemChildClickListener)
        }
    }
}