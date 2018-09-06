package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.TodoListBean
import com.zk.wanandroidtodo.manager.RetrofitManager
import com.zk.wanandroidtodo.utils.RxSchedulers
import io.reactivex.Observable

/**
 * @description:     清单列表Model
 * @author:         zhukai
 * @date:     2018/9/3 15:12
 */
class TodoListModel : TodoListContract.ITodoListModel {

    /**
     * 获取清单列表
     */
    override fun getTodoList(page: Int, isDone: Boolean): Observable<DataResponse<TodoListBean>> {
        if (!isDone) return RetrofitManager.service.getTodoList(page)
                .compose(RxSchedulers.applySchedulers())
        else return RetrofitManager.service.getDoneList(page)
                .compose(RxSchedulers.applySchedulers())
    }

    /**
     * 删除清单
     */
    override fun deleteTodo(id: Int): Observable<DataResponse<Any>> {
        return RetrofitManager.service.deleteTodo(id)
                .compose(RxSchedulers.applySchedulers())
    }

    /**
     * 更新清单状态
     */
    override fun updateTodoStatus(id: Int, status: Int): Observable<DataResponse<Any>> {
        return RetrofitManager.service.updateTodoStatus(id, status)
                .compose(RxSchedulers.applySchedulers())
    }
}