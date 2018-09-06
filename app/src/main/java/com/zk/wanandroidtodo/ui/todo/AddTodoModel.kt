package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.manager.RetrofitManager
import com.zk.wanandroidtodo.utils.RxSchedulers
import io.reactivex.Observable

/**
 * @description:     添加待办清单Model
 * @author:         zhukai
 * @date:     2018/9/5 11:16
 */
class AddTodoModel : AddTodoContract.IAddTodoModel {

    /**
     * 添加待办清单
     */
    override fun addTodo(title: String, content: String, date: String, type: Int): Observable<DataResponse<Any>> {
        return RetrofitManager.service.addTodo(title, content, date, type)
                .compose(RxSchedulers.applySchedulers())
    }
}