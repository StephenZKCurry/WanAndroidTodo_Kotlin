package com.zk.wanandroidtodo.ui.todo

import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.manager.RetrofitManager
import com.zk.wanandroidtodo.utils.RxSchedulers
import io.reactivex.Observable

/**
 * @description:     清单详情Model
 * @author:         zhukai
 * @date:     2018/9/5 15:51
 */
class TodoDetailModel : TodoDetailContract.ITodoDetailModel {

    /**
     * 更新清单内容
     */
    override fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int): Observable<DataResponse<Any>> {
        return RetrofitManager.service.updateTodo(id, title, content, date, status, type)
                .compose(RxSchedulers.applySchedulers())
    }
}