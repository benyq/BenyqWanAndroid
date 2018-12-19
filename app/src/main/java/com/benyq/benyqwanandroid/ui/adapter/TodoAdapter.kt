package com.benyq.benyqwanandroid.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.base.adapter.BaseHolder
import com.benyq.benyqwanandroid.base.adapter.BaseSectionAdapter
import com.benyq.benyqwanandroid.ui.entitiy.TodoSectionEntity

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
class TodoAdapter(mContext: Context)
    : BaseSectionAdapter<TodoModel.Todo, TodoSectionEntity>(mContext, R.layout.item_head_todo, R.layout.item_todo) {

    override fun convertHead(holder: BaseHolder, position: Int, bean: TodoSectionEntity) {
        holder.setText(R.id.tvTodoNiceDate, bean.head)
    }

    override fun convert(holder: BaseHolder, position: Int, bean: TodoSectionEntity) {
        bean.data?.let{
            holder.setText(R.id.tvTodoTitle, it.title)
                    .setText(R.id.tvTodoContent, it.content)
                    .setText(R.id.tvTodoNiceDate, it.dateStr)
                    .addOnItemChildClickListener(R.id.ivStatus, mOnItemChildClickListener)
                    .addOnItemChildClickListener(R.id.ivDelete, mOnItemChildClickListener)
            if (it.status == 0){//未完成
                holder.setImageRes(R.id.ivStatus, R.drawable.ic_done)
            }else {//1,完成
                holder.setImageRes(R.id.ivStatus, R.drawable.ic_todo_not)
            }
        }
    }

}