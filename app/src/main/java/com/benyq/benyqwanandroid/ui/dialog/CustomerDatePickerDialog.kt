package com.benyq.benyqwanandroid.ui.dialog

import android.widget.LinearLayout
import android.widget.NumberPicker
import android.view.ViewGroup
import android.widget.FrameLayout
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import java.util.*
import android.widget.DatePicker
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.benyq.benyqwanandroid.R
import com.benyq.benyqwanandroid.base.Constants
import org.greenrobot.eventbus.EventBus


/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/19
 */
class CustomerDatePickerDialog : DialogFragment() {
    private lateinit var mDatePicker: DatePicker

    companion object {


        fun getInstance(date: Date): CustomerDatePickerDialog {
            val args = Bundle()
            args.putSerializable(Constants.ARG_DATE, date)
            val fragment = CustomerDatePickerDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(Constants.ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)
        mDatePicker = view.findViewById(R.id.dialog_date_date_picker)
        mDatePicker.init(year, month, day, null)

        resizePicker(mDatePicker)

        return AlertDialog.Builder(activity!!)
                .setTitle("选择时间")
                .setView(view)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    val date = GregorianCalendar(mDatePicker.year, mDatePicker.month, mDatePicker.dayOfMonth).time
                    sendResult(Activity.RESULT_OK, date)
                }
                .create()
    }


    private fun sendResult(resultCode: Int, date: Date) {
        EventBus.getDefault().post(date)
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    private fun resizePicker(tp: FrameLayout) {
        val npList = findNumberPicker(tp)
        for (np in npList) {
            resizeNumberPicker(np)
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private fun findNumberPicker(viewGroup: ViewGroup?): List<NumberPicker> {
        val npList = mutableListOf<NumberPicker>()
        var child: View? = null
        if (null != viewGroup) {
            for (i in 0 until viewGroup.childCount) {
                child = viewGroup.getChildAt(i)
                if (child is NumberPicker) {
                    npList.add(child)
                } else if (child is LinearLayout) {
                    val result = findNumberPicker(child as ViewGroup?)
                    if (result.isNotEmpty()) {
                        return result
                    }
                }
            }
        }
        return npList
    }

    //    调整大小
    private fun resizeNumberPicker(np: NumberPicker) {
        val params = LinearLayout.LayoutParams(140, 140)
        params.setMargins(10, 0, 10, 0)
        np.layoutParams = params
    }


}
