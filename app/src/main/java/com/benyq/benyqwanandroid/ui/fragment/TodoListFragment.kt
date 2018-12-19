package com.benyq.benyqwanandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.base.SimpleBaseFragment
import com.benyq.benyqwanandroid.base.adapter.OnItemChildClickListener
import com.benyq.benyqwanandroid.base.adapter.OnItemClickListener
import com.benyq.benyqwanandroid.ui.activity.TodoListActivity
import com.benyq.benyqwanandroid.ui.adapter.TodoAdapter
import com.benyq.benyqwanandroid.ui.entitiy.TodoSectionEntity
import kotlinx.android.synthetic.main.fragment_todo.*
import org.greenrobot.eventbus.EventBus

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
class TodoListFragment: SimpleBaseFragment() {

    companion object {
        fun getInstance(status : Int): TodoListFragment{
            val fragment = TodoListFragment()
            fragment.mStatus = status
            return fragment
        }
    }

    private var mStatus = -1
    private var mCurrentIndex = -1
    private var mLoading = false
    private var mCurPage = 1

    private val mAdapter by lazy { TodoAdapter(mContext) }
    private val mTodoModifyFragment by lazy { TodoModifyFragment.getInstance(status = mStatus) }


    override fun getLayoutId() = R.layout.fragment_todo

    override fun initView() {
        rvTodo.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val manager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    //向下滚动
                    if (dy > 0){
                        val visibleItemCount = manager.childCount
                        val totalItemCount = manager.itemCount
                        val pastVisibleItems = manager.findFirstVisibleItemPosition()
                        //距还剩5个时加载
                        if (!mLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount - 5) {
                            mLoading = true
                            loadMoreData()
                        }
                    }
                }
            })
        }
        mAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                mCurrentIndex = position
                val todoSectionEntity = mAdapter.mData[position]
                if (!todoSectionEntity.isHeader){
                    mTodoModifyFragment.setData(todoSectionEntity.data!!)
                    mTodoModifyFragment.show(childFragmentManager, "")
                }
            }
        })

        mAdapter.setOnItemChildClickListener(object : OnItemChildClickListener{
            override fun onItemChildClick(view: View, position: Int) {
                when(view.id){
                    R.id.ivDelete -> {
                        mCurrentIndex = position
                        (mContext as TodoListActivity).deleteTodo(mAdapter.mData[position].data?.id!!, mStatus)
                    }
                    R.id.ivStatus -> {
                        mCurrentIndex = position
                        (mContext as TodoListActivity).updateStatusTodo(mAdapter.mData[position].data?.id!!, mStatus)
                    }
                }
            }
        })
    }


    override fun lazyLoad() {
    }

    fun showTodoList(todoModel: TodoModel){
        todoModel.run {
            mLoading = false
            mCurPage = curPage
            val list = mutableListOf<TodoSectionEntity>()
            if (mCurPage == 1){
               todoModel.datas.groupBy { it.dateStr }.forEach{
                    list.add(TodoSectionEntity(true, it.key, null))
                    it.value.forEach {  todo->
                        list.add(TodoSectionEntity(todo))
                    }
                }
                mAdapter.addNewData(list)
            }else {
                val lastData = mAdapter.mData.last()
                val tempList = todoModel.datas.dropWhile { it.dateStr == lastData.data!!.dateStr }
                mAdapter.addData(todoModel.datas.takeWhile { it.dateStr == lastData.data!!.dateStr }.map { TodoSectionEntity(it)}.toMutableList())
                tempList.groupBy { it.dateStr }.forEach {
                    list.add(TodoSectionEntity(true, it.key, null))
                    it.value.forEach {  todo->
                        list.add(TodoSectionEntity(todo))
                    }
                }
                mAdapter.addData(list.toMutableList())
            }
        }


    }

    fun deleteTodo(){
        mAdapter.removeData(mCurrentIndex)
        if (mAdapter.mData[mCurrentIndex - 1].isHeader){
            mAdapter.removeData(mCurrentIndex - 1)
        }
    }

    fun addTodo(data: TodoModel.Todo){
        mAdapter.mData.add(TodoSectionEntity(data))
        val list = mAdapter.mData.filter { !it.isHeader }.sortedByDescending { it.data!!.date }.map { it.data!! }.toMutableList()
        val map = list.groupBy { it.dateStr }
        val tempList = mutableListOf<TodoSectionEntity>()
        map.forEach{
            tempList.add(TodoSectionEntity(true, it.key, null))
            it.value.forEach {  todo->
                tempList.add(TodoSectionEntity(todo))
            }
        }
        mAdapter.addNewData(tempList)
    }

    fun updateTodo(data: TodoModel.Todo){
        mAdapter.mData.add(TodoSectionEntity(data))
        val list = mAdapter.mData.slice(listOf(mCurrentIndex)).filter { !it.isHeader }.map { it.data!! }.toMutableList()
        val map = list.groupBy { it.dateStr }
        val tempList = mutableListOf<TodoSectionEntity>()
        map.forEach{
            tempList.add(TodoSectionEntity(true, it.key, null))
            it.value.forEach {  todo->
                tempList.add(TodoSectionEntity(todo))
            }
        }
        mAdapter.addNewData(tempList)

    }

    fun loadMoreData(){
        (mContext as TodoListActivity).getMoreTodoList(mCurPage + 1, mStatus)
    }
}