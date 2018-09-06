package com.zk.wanandroidtodo.bean

import java.io.Serializable

/**
 * @description:     清单列表实体类
 * @author:         zhukai
 * @date:     2018/9/3 10:53
 */
data class TodoListBean(
        var curPage: Int,
        var offset: Int,
        var isOver: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int,
        var datas: List<DatasBean>
) {
    data class DatasBean(
            var completeDate: Long?,
            var completeDateStr: String,
            var content: String,
            var date: Long,
            var dateStr: String,
            var id: Int,
            var status: Int,
            var title: String,
            var type: Int,
            var userId: Int
    ) : Serializable
}