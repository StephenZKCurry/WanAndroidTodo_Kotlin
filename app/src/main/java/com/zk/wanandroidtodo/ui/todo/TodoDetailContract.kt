package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.BasePresenter
import com.zk.wanandroidtodo.base.IBaseModel
import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.bean.DataResponse
import io.reactivex.Observable

/**
 * @description:     清单详情Contract
 * @author:         zhukai
 * @date:     2018/9/5 15:46
 */
interface TodoDetailContract {

    abstract class TodoDetailPresenter : BasePresenter<ITodoDetailView>() {
        /**
         * 更新待办清单内容
         */
        abstract fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int)
    }

    interface ITodoDetailModel : IBaseModel {
        /**
         * 更新待办清单内容
         */
        fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int): Observable<DataResponse<Any>>
    }

    interface ITodoDetailView : IBaseView {
        /**
         * 更新待办清单内容成功
         */
        fun showUpdateTodoSuccess(message: String)
    }
}