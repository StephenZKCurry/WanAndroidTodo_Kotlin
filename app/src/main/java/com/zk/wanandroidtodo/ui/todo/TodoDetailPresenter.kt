package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils

/**
 * @description:     清单详情Presenter
 * @author:         zhukai
 * @date:     2018/9/5 15:50
 */
class TodoDetailPresenter : TodoDetailContract.TodoDetailPresenter() {

    private val mIModel: TodoDetailModel by lazy {
        TodoDetailModel()
    }

    /**
     * 更新清单内容
     */
    override fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int) {
        mIView?.showLoadingDialog("请稍后...")
        mRxManager.register(mIModel.updateTodo(id, title, content, date, status, type)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 更新清单内容成功
                            showUpdateTodoSuccess(MyApplication.mContext.getString(R.string.update_todo_success))
                        } else {
                            // 更新清单内容失败
                            showToast(MyApplication.mContext.getString(R.string.update_todo_failed))
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