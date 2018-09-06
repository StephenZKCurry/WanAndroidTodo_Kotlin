package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils

/**
 * @description:     添加待办事项Presenter
 * @author:         zhukai
 * @date:     2018/9/5 11:15
 */
class AddTodoPresenter : AddTodoContract.AddTodoPresenter() {

    private val mIModel: AddTodoModel by lazy {
        AddTodoModel()
    }

    /**
     * 添加待办清单
     */
    override fun addTodo(title: String, content: String, date: String, type: Int) {
        mIView?.showLoadingDialog("请稍后...")
        mRxManager.register(mIModel.addTodo(title, content, date, type)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 添加待办清单成功
                            showAddTodoSuccess(MyApplication.mContext.getString(R.string.add_todo_success))
                        } else {
                            // 添加待办清单失败
                            showToast(MyApplication.mContext.getString(R.string.add_todo_failed))
                        }
                        hideLoadingDialog()
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
                        hideLoadingDialog()
                    }
                }))
    }
}