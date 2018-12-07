package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.ProjectModel
import com.benyq.benyqwanandroid.base.BaseAdapter
import com.benyq.benyqwanandroid.base.BaseHolder

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/7
 */
class ProjectDetailAdapter(private val mContext: Context): BaseAdapter<ProjectModel.Project>(mContext, R.layout.item_project_detail) {
    override fun convert(holder: BaseHolder, position: Int, bean: ProjectModel.Project) {
        holder.setText(R.id.tvProjectTitle, bean.title)
                .setText(R.id.tvProjectAuthor, bean.author)
                .setText(R.id.tvProjectDesc, bean.desc)
                .setText(R.id.tvProjectNiceDate, bean.niceDate)
                .setImageUrl(R.id.ivProjectIcon, bean.envelopePic)
    }
}