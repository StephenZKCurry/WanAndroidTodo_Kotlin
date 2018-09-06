package com.zk.wanandroidtodo.ui.todo

import android.view.View
import cn.qqtheme.framework.picker.DatePicker
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity
import com.zk.wanandroidtodo.formatCurrentDate
import com.zk.wanandroidtodo.rxbus.RxBus
import com.zk.wanandroidtodo.toast
import com.zk.wanandroidtodo.utils.Constant
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.*

/**
 * @description:     添加待办清单页面
 * @author:         zhukai
 * @date:     2018/9/5 10:45
 */
class AddTodoActivity : BaseActivity(), AddTodoContract.IAddTodoView {

    private val mPresenter: AddTodoPresenter by lazy {
        AddTodoPresenter()
    }

    override fun getContentViewId(): Int = R.layout.activity_add_todo

    override fun showHomeAsUp(): Boolean = true

    override fun initView() {
        super.initView()
        tvTitle.text = getString(R.string.add_todo_title)
        tv_date.text = formatCurrentDate()
        mPresenter.attachView(this)
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
        tv_date.setOnClickListener(onClickListener)
        tv_submit.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tv_date -> {
                // 选择日期
                createDatePicker()
            }
            R.id.tv_submit -> {
                val title = et_title.text.toString().trim()
                val content = et_content.text.toString().trim()
                val date = tv_date.text.toString().trim()
                if (title.isEmpty()) {
                    toast(getString(R.string.add_todo_title_hint))
                } else {
                    // 添加待办清单
                    mPresenter.addTodo(title, content, date, 0)
                }
            }
        }
    }

    /**
     * 选择日期
     */
    private fun createDatePicker() {
        val picker = DatePicker(this)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        picker.setRangeStart(currentYear, currentMonth + 1, currentDay)
        picker.setRangeEnd(2030, 12, 31)
        picker.setSelectedItem(currentYear, currentMonth + 1, currentDay)
        picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
            tv_date.text = "$year-$month-$day"
        })
        picker.show()
    }

    /**
     * 添加待办清单成功
     */
    override fun showAddTodoSuccess(message: String) {
        toast(message)
        // 通知列表页刷新数据
        RxBus.get().send(Constant.RX_BUS_CODE_REFRESH)
        finish()
    }

    override fun showLoadingDialog(message: String) {
        if (mWaitPorgressDialog.isShowing) {
            mWaitPorgressDialog.dismiss()
        }
        mWaitPorgressDialog.setMessage(message)
        mWaitPorgressDialog.show()
    }

    override fun hideLoadingDialog() {
        mWaitPorgressDialog.dismiss()
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}