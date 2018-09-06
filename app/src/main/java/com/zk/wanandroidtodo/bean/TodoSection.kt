package com.zk.wanandroidtodo.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @description:     事项清单分组实体类
 * @author:         zhukai
 * @date:     2018/9/3 15:58
 */
class TodoSection : SectionEntity<TodoListBean.DatasBean> {

    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(todoBean: TodoListBean.DatasBean) : super(todoBean)
}