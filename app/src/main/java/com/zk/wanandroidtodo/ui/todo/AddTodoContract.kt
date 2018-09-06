package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.BasePresenter
import com.zk.wanandroidtodo.base.IBaseModel
import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.bean.DataResponse
import io.reactivex.Observable

/**
 * @description:     添加待办清单Contract
 * @author:         zhukai
 * @date:     2018/9/5 11:10
 */
interface AddTodoContract {

    abstract class AddTodoPresenter : BasePresenter<IAddTodoView>() {
        /**
         * 添加待办清单
         */
        abstract fun addTodo(title: String, content: String, date: String, type: Int)
    }

    interface IAddTodoModel : IBaseModel {
        /**
         * 添加待办清单
         */
        fun addTodo(title: String, content: String, date: String, type: Int): Observable<DataResponse<Any>>
    }

    interface IAddTodoView : IBaseView {
        /**
         * 添加待办事项成功
         */
        fun showAddTodoSuccess(message: String)
    }
}