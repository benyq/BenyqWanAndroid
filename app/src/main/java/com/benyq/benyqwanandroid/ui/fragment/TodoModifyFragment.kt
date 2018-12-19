package com.benyq.benyqwanandroid.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.BaseFragment
import com.benyq.benyqwanandroid.base.SimpleBaseFragment
import kotlinx.android.synthetic.main.fragment_todo_modify.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.Log
import android.view.Display
import com.benyq.benyqwanandroid.api.model.TodoModel
import com.benyq.benyqwanandroid.base.Constants
import com.benyq.benyqwanandroid.ui.dialog.CustomerDatePickerDialog
import com.benyq.benyqwanandroid.ui.entitiy.event.TodoEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*


/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/18
 */
class TodoModifyFragment: DialogFragment() {

    companion object {
        fun getInstance(todo: TodoModel.Todo? = null, status: Int = 0): TodoModifyFragment{
            val fragment = TodoModifyFragment()
            fragment.mTodo = todo
            fragment.mStatus = status
            return fragment
        }
    }

    private var mStatus = 0
    private var mTodo: TodoModel.Todo? = null
    private val REQUEST_DATE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.style_dialog_todo)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo_modify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        val display = activity!!.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun setData(todo: TodoModel.Todo){
        mTodo = todo
    }

    private fun initView() {
        tvCancel.setOnClickListener {
            dismiss()
        }
        mTodo = mTodo ?: TodoModel.Todo()
        mTodo?.let {
            if (id > -1){
                etTodoTitle.setText(it.title)
                etTodoContent.setText(it.content)
                tvNiceDate.text = it.dateStr
            }
        }

        btnCommit.setOnClickListener {
            val title = etTodoTitle.text.toString()
            val content = etTodoContent.text.toString()
            val dateStr = tvNiceDate.text.toString()
            val todo = mTodo!!.copy(title = title, content = content, dateStr = dateStr)
            EventBus.getDefault().post(TodoEvent(mStatus, todo))
            dismiss()
        }

        llDate.setOnClickListener {
            val dialog = CustomerDatePickerDialog.getInstance(Date(mTodo?.date!!))
            dialog.show(childFragmentManager, Constants.ARG_DATE)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventDate(date: Date){
        tvNiceDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date)
    }
}