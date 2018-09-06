package com.zk.wanandroidtodo.ui.todo

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.fragment.BaseFragment
import com.zk.wanandroidtodo.bean.TodoListBean
import com.zk.wanandroidtodo.bean.TodoSection
import com.zk.wanandroidtodo.rxbus.RxBus
import com.zk.wanandroidtodo.rxbus.Subscribe
import com.zk.wanandroidtodo.rxbus.ThreadMode
import com.zk.wanandroidtodo.toast
import com.zk.wanandroidtodo.ui.mine.LoginActivity
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.Preference
import kotlinx.android.synthetic.main.fragment_todo_list.*

/**
 * @description:     待办清单页面
 * @author:         zhukai
 * @date:     2018/8/22 10:59
 */
class TodoListFragment : BaseFragment(), TodoListContract.ITodoListView {

    private var isDone: Boolean = false

    /**
     * 是否登录
     */
    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val mPresenter: TodoListPresenter by lazy {
        TodoListPresenter(isDone)
    }

    /**
     * 数据源
     */
    private val mData = mutableListOf<TodoSection>()

    /**
     * Adapter
     */
    private val mAdapter: TodoListAdapter by lazy {
        TodoListAdapter(mData, isDone)
    }

    companion object {
        fun newInstance(isDone: Boolean): TodoListFragment {
            val args = Bundle()
            args.putBoolean(Constant.IS_DONE, isDone)
            val fragment = TodoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            isDone = bundle.getBoolean(Constant.IS_DONE)
        }
    }

    override fun getContentViewId(): Int = R.layout.fragment_todo_list

    /**
     * 初始化页面
     */
    override fun initView() {
        super.initView()
        mPresenter.attachView(this)
        refresh_todo.run {
            setColorSchemeResources(R.color.color_main)
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        rv_todo.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.run {
            bindToRecyclerView(rv_todo)
            setOnLoadMoreListener(onRequestLoadMoreListener)
            onItemClickListener = this@TodoListFragment.onItemClickListener
            onItemChildClickListener = this@TodoListFragment.onItemChildClickListener
            setEmptyView(R.layout.layout_empty_view)
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        super.initData()
        mPresenter.getTodoList(1, isDone)
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {
        super.initEvent()
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh_todo.isRefreshing = true
        mPresenter.refresh()
    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        mPresenter.loadMore()
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        // 头部不设置点击事件
        if (!mAdapter.getItem(position)!!.isHeader) {
            Intent(mContext, TodoDetailActivity::class.java).run {
                putExtra(Constant.TODO_BEAN, mAdapter.getItem(position)!!.t)
                putExtra(Constant.IS_DONE, isDone)
                startActivity(this)
            }
        }
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.iv_done -> {
                MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_update_todo_status)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive { dialog, which ->
                            // 更新清单状态
                            if (isDone) mPresenter.updateTodoStatus(mAdapter.getItem(position)!!.t.id, 0)
                            else mPresenter.updateTodoStatus(mAdapter.getItem(position)!!.t.id, 1)
                        }.show()
            }
            R.id.iv_delete -> {
                MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_delete_todo)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive { dialog, which ->
                            // 删除清单
                            mPresenter.deleteTodo(mAdapter.getItem(position)!!.t.id)
                        }.show()
            }
        }
    }

    override fun showTodoList(todoListBean: TodoListBean, loadType: Int) {
        refresh_todo.isRefreshing = false
        val list = mutableListOf<TodoSection>()
        todoListBean.datas.forEachIndexed { i, todoBean ->
            if (i == 0 || todoBean.dateStr != todoListBean.datas[i - 1].dateStr) {
                list.add(TodoSection(true, todoListBean.datas[i].dateStr))
            }
            list.add(TodoSection(todoBean))
        }
        when (loadType) {
            Constant.TYPE_REFRESH_SUCCESS -> {
                mAdapter.setNewData(list)
            }
            Constant.TYPE_LOAD_MORE_SUCCESS -> {
                mAdapter.addData(list)
            }
        }
        if (todoListBean.datas == null || todoListBean.datas.isEmpty() ||
                todoListBean.datas.size < Constant.PAGE_SIZE) {
            // 没有更多数据了
            mAdapter.loadMoreEnd()
        } else {
            // 加载完成
            mAdapter.loadMoreComplete()
        }
    }

    /**
     * 删除清单成功
     */
    override fun deleteTodoSuccess() {
        mContext.toast(getString(R.string.delete_todo_success))
        // 刷新列表
        mPresenter.refresh()
    }

    /**
     * 更新清单状态成功
     */
    override fun updateTodoStatusSuccess() {
        // 通知列表页刷新
        RxBus.get().send(Constant.RX_BUS_CODE_REFRESH_STATUS)
    }

    override fun showLoginExpired(message: String) {
        mContext.toast(message)
        // 清除本地保存的用户信息
        Preference.clearPreference(Constant.USER_ID)
        Preference.clearPreference(Constant.USER_NAME)
        isLogin = false
        // 跳转登录页面
        Intent(mContext, LoginActivity::class.java).run {
            startActivity(this)
            activity?.finish()
        }
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
        mContext.toast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    /**
     * 新增或更新待办清单成功，刷新列表
     */
    @Subscribe(code = Constant.RX_BUS_CODE_REFRESH, threadMode = ThreadMode.MAIN)
    fun refreshTodoList() {
        if (!isDone) {
            // 只刷新待办清单页面
            mPresenter.refresh()
        }
    }

    /**
     * 更新清单状态成功，刷新列表
     */
    @Subscribe(code = Constant.RX_BUS_CODE_REFRESH_STATUS, threadMode = ThreadMode.MAIN)
    fun refreshList() {
        mPresenter.refresh()
    }
}