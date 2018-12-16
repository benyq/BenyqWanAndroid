package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ArticleFavoriteModel
import com.benyq.benyqwanandroid.api.model.HotWordModel
import com.benyq.benyqwanandroid.base.adapter.BaseAdapter
import com.benyq.benyqwanandroid.base.adapter.BaseHolder

/**
 * @author benyq
 * @date 18-12-16 21:14
 * @E-mail： 1520063035@qq.com
 * @description
 */
class SearchHotWordsAdapter(mContext: Context): BaseAdapter<HotWordModel>(mContext, R.layout.item_hot_word) {

    override fun convert(holder: BaseHolder, position: Int, bean: HotWordModel) {
        holder.setText(R.id.tvHotWord, bean.name);
    }
}