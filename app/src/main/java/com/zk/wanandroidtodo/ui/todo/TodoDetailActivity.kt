package com.zk.wanandroidtodo.ui.todo

import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.qqtheme.framework.picker.DatePicker
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity
import com.zk.wanandroidtodo.bean.TodoListBean
import com.zk.wanandroidtodo.rxbus.RxBus
import com.zk.wanandroidtodo.toast
import com.zk.wanandroidtodo.utils.Constant
import kotlinx.android.synthetic.main.activity_todo_detail.*
import java.util.*

/**
 * @description:     清单详情页面
 * @author:         zhukai
 * @date:     2018/9/5 14:06
 */
class TodoDetailActivity : BaseActivity(), TodoDetailContract.ITodoDetailView {

    private val mPresenter: TodoDetailPresenter by lazy {
        TodoDetailPresenter()
    }

    private lateinit var todoBean: TodoListBean.DatasBean

    override fun getContentViewId(): Int = R.layout.activity_todo_detail

    override fun showHomeAsUp(): Boolean = true

    override fun initView() {
        super.initView()
        tvTitle.text = getString(R.string.todo_detail_title)
        mPresenter.attachView(this)
    }

    override fun initData() {
        super.initData()
        todoBean = intent.extras.getSerializable(Constant.TODO_BEAN) as TodoListBean.DatasBean
        et_title.setText(todoBean.title)
        et_content.setText(todoBean.content)
        tv_date.text = todoBean.dateStr
    }

    override fun initEvent() {
        super.initEvent()
        tv_date.setOnClickListener(onClickListener)
        tv_submit.setOnClickListener(onClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!intent.extras.getBoolean(Constant.IS_DONE)) {
            // 已完成清单不能修改内容
            menuInflater.inflate(R.menu.menu_detail, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit) {
            et_title.isEnabled = true
            et_content.isEnabled = true
            tv_date.isEnabled = true
            tv_submit.visibility = View.VISIBLE
        }
        return super.onOptionsItemSelected(item)
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
                    // 更新清单内容
                    mPresenter.updateTodo(todoBean.id, title, content, date, 0, 0)
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
     * 更新清单内容成功
     */
    override fun showUpdateTodoSuccess(message: String) {
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