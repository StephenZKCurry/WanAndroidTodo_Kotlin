package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils

/**
 * @description:     清单列表Presenter
 * @author:         zhukai
 * @date:     2018/9/3 15:10
 */
class TodoListPresenter(val isDone: Boolean) : TodoListContract.TodoListPresenter() {

    private var mPage: Int = 0 // 当前页码

    private val mIModel: TodoListModel by lazy {
        TodoListModel()
    }

    /**
     * 下拉刷新
     */
    override fun refresh() {
        mPage = 1
        getTodoList(mPage, isDone)
    }

    /**
     * 上拉加载
     */
    override fun loadMore() {
        mPage++
        getTodoList(mPage, isDone)
    }

    /**
     * 获取清单列表
     */
    override fun getTodoList(page: Int, isDone: Boolean) {
        mRxManager.register(mIModel.getTodoList(page, isDone)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            if (page == 1) {
                                // 下拉刷新
                                showTodoList(dataResponse.data, Constant.TYPE_REFRESH_SUCCESS)
                            } else {
                                // 上拉加载
                                showTodoList(dataResponse.data, Constant.TYPE_LOAD_MORE_SUCCESS)
                            }
                        } else {
                            if (dataResponse.errorMsg == "请先登录！") {
                                showLoginExpired(MyApplication.mContext.getString(R.string.login_expired))
                            }
                        }
                    }
                }, { t ->
                    mIView?.apply {
                        val available = NetworkUtils.isNetworkAvailable(MyApplication.mContext)
                        if (!available) {
                            showToast(MyApplication.mContext.getString(R.string.no_network_connection))
                        } else {
                            showToast(t.message
                                    ?: MyApplication.mContext.getString(R.string.request_error))
                        }
                    }
                }))
    }

    /**
     * 删除清单
     */
    override fun deleteTodo(id: Int) {
        mRxManager.register(mIModel.deleteTodo(id)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 删除清单成功
                            deleteTodoSuccess()
                        } else {
                            // 删除清单失败
                            showToast(MyApplication.mContext.getString(R.string.delete_todo_failed))
                        }
                    }
                }, { t ->
                    mIView?.apply {
                        val available = NetworkUtils.isNetworkAvailable(MyApplication.mContext)
                        if (!available) {
                            showToast(MyApplication.mContext.getString(R.string.no_network_connection))
                        } else {
                            showToast(t.message
                                    ?: MyApplication.mContext.getString(R.string.request_error))
                        }
                    }
                }))
    }

    /**
     * 更新清单状态
     */
    override fun updateTodoStatus(id: Int, status: Int) {
        mRxManager.register(mIModel.updateTodoStatus(id, status)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 更新清单状态成功
                            updateTodoStatusSuccess()
                        } else {
                            // 更新清单状态失败
                            showToast(MyApplication.mContext.getString(R.string.update_todo_status_failed))
                        }
                    }
                }, { t ->
                    mIView?.apply {
                        val available = NetworkUtils.isNetworkAvailable(MyApplication.mContext)
                        if (!available) {
                            showToast(MyApplication.mContext.getString(R.string.no_network_connection))
                        } else {
                            showToast(t.message
                                    ?: MyApplication.mContext.getString(R.string.request_error))
                        }
                    }
                }))
    }
}