package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.BasePresenter
import com.zk.wanandroidtodo.base.IBaseModel
import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.TodoListBean
import io.reactivex.Observable

/**
 * @description:     清单列表Contract
 * @author:         zhukai
 * @date:     2018/9/3 10:48
 */
interface TodoListContract {

    abstract class TodoListPresenter : BasePresenter<ITodoListView>() {
        /**
         * 获取清单列表
         */
        abstract fun getTodoList(page: Int, isDone: Boolean)

        /**
         * 下拉刷新
         */
        abstract fun refresh()

        /**
         * 上拉加载
         */
        abstract fun loadMore()

        /**
         * 删除清单
         */
        abstract fun deleteTodo(id: Int)

        /**
         * 更新清单状态
         */
        abstract fun updateTodoStatus(id: Int, status: Int)
    }

    interface ITodoListModel : IBaseModel {
        /**
         * 获取清单列表
         */
        fun getTodoList(page: Int, isDone: Boolean): Observable<DataResponse<TodoListBean>>

        /**
         * 删除清单
         */
        fun deleteTodo(id: Int): Observable<DataResponse<Any>>

        /**
         * 更新清单状态
         */
        fun updateTodoStatus(id: Int, status: Int): Observable<DataResponse<Any>>
    }

    interface ITodoListView : IBaseView {
        /**
         * 显示清单列表
         */
        fun showTodoList(todoListBean: TodoListBean, loadType: Int)

        /**
         * 删除清单成功
         */
        abstract fun deleteTodoSuccess()

        /**
         * 更新清单状态成功
         */
        fun updateTodoStatusSuccess()

        /**
         * 提示登录过期
         */
        fun showLoginExpired(message: String)
    }
}