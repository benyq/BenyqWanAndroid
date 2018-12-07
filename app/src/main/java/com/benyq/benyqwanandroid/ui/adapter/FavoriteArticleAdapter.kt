package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ArticleFavoriteModel
import com.benyq.benyqwanandroid.base.BaseAdapter
import com.benyq.benyqwanandroid.base.BaseHolder

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/5
 */
class FavoriteArticleAdapter(mContext: Context): BaseAdapter<ArticleFavoriteModel.ArticleFavorite>(mContext, R.layout.item_favorite_article) {

    override fun convert(holder: BaseHolder, position: Int, bean: ArticleFavoriteModel.ArticleFavorite) {
        bean.run {
            holder.setText(R.id.tvTitle, title)
                    .setText(R.id.tvAuthor, author)
                    .setText(R.id.tvNiceDate, niceDate)
                    .addOnItemChildClickListener(R.id.ivShare, mOnItemChildClickListener)
                    .addOnItemChildClickListener(R.id.ivFavorite, mOnItemChildClickListener)
        }
    }
}