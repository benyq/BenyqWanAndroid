package com.benyq.benyqwanandroid.ui.activity

import android.os.Bundle
import androidx.core.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.api.param.AddTodoParam
import com.benyq.benyqwanandroid.base.ARouterPath
import com.benyq.benyqwanandroid.base.BaseActivity
import com.benyq.benyqwanandroid.mvp.contract.TodoListActivityContract
import com.benyq.benyqwanandroid.mvp.presenter.TodoListActivityPresenter
import com.benyq.benyqwanandroid.ui.adapter.ProjectViewPagerAdapter
import com.benyq.benyqwanandroid.ui.entitiy.event.TodoEvent
import com.benyq.benyqwanandroid.ui.fragment.TodoListFragment
import com.benyq.benyqwanandroid.ui.fragment.TodoModifyFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@Route(path = ARouterPath.pathTodoActivity)
class TodoListActivity : BaseActivity() , TodoListActivityContract.View{

    @Inject
    lateinit var mPresenter: TodoListActivityPresenter

    private val TODO = 0
    private val TODO_DONE = 1

    var mStatus = 0//当前操作的哪一个Fragment

    private val mTodoFragment by lazy { TodoListFragment.getInstance(TODO) }
    private val mTodoDoneFragment by lazy { TodoListFragment.getInstance(TODO_DONE) }
    private val mTodoModifyFragment by lazy { TodoModifyFragment.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initView() {

        toolbar_title.text = "TODO"

        toolbar_back.setOnClickListener {
            finish()
        }

        val fragments = arrayListOf<Fragment>()
        val titles = arrayListOf<String>()
        fragments.add(mTodoFragment)//未完成
        fragments.add(mTodoDoneFragment)//完成
        titles.add("未完成")
        titles.add("完成")
        val projectViewPagerAdapter = ProjectViewPagerAdapter(supportFragmentManager)
        projectViewPagerAdapter.setFragmentAndTitles(fragments, titles)
        vpProject.offscreenPageLimit = fragments.size
        vpProject.adapter = projectViewPagerAdapter
//        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(vpProject)
        vpProject.currentItem = 0//在设置adapter之后生效

        fabAddTodo.setOnClickListener {
            mTodoModifyFragment.show(supportFragmentManager, "")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initData() {
        mPresenter.getTodoList(1, TODO_DONE)
        mPresenter.getTodoList(1, TODO)
    }

    override fun initTheme() {

    }

    override fun layoutId() = R.layout.activity_todo

    override fun showTodoList(todoModel: TodoModel, status: Int) {
        if (status == TODO){
            mTodoFragment.showTodoList(todoModel)
        }else if (status == TODO_DONE){
            mTodoDoneFragment.showTodoList(todoModel)
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(t: String) {
    }

    override fun showDelete(index: Int) {
        if (mStatus == TODO){
            mTodoFragment.deleteTodo()
        }else if(mStatus == TODO_DONE){
            mTodoDoneFragment.deleteTodo()
        }
    }

    override fun showUpdate(todo: TodoModel.Todo) {
        if (mStatus == TODO){
            mTodoFragment.updateTodo(todo)
        }else if(mStatus == TODO_DONE){
            mTodoDoneFragment.updateTodo(todo)
        }
    }

    override fun showUpdateStatus(id: Int, data: TodoModel.Todo) {
        showDelete(id)//原item删除
        if (mStatus == TODO){
            mTodoDoneFragment.addTodo(data)
        }else if(mStatus == TODO_DONE){
            mTodoFragment.addTodo(data)
        }
    }

    override fun addTodo(todo: TodoModel.Todo) {
        mTodoFragment.addTodo(todo)
    }

    fun deleteTodo(id: Int, status: Int){
        mStatus = status
        mPresenter.deleteTodo(id)
    }

    fun updateStatusTodo(id: Int, status: Int){
        mStatus = status
        if (mStatus == TODO){
            mPresenter.UpdateStatusTodo(id, TODO_DONE)
        }else if(mStatus == TODO_DONE){
            mPresenter.UpdateStatusTodo(id, TODO)
        }

    }

    fun getMoreTodoList(page: Int, status: Int){
        mStatus = status
        mPresenter.getTodoList(page, status)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventTodo(event: TodoEvent){
        mStatus = event.status
        if (event.todo.id > -1){
            with(event.todo){
                mPresenter.updateTodo(id, content, title, dateStr, status, type, priority)
            }

        }else {
            with(event.todo){
                val addTodoParam = AddTodoParam(title, content, dateStr, 0)
                mPresenter.addTodo(addTodoParam)
            }

        }
    }

}
