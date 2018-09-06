package com.zk.wanandroidtodo.ui.todo

import android.text.TextUtils
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.bean.TodoSection

/**
 * @description:     清单列表Adapter
 * @author:         zhukai
 * @date:     2018/9/3 15:51
 */
class TodoListAdapter : BaseSectionQuickAdapter<TodoSection, BaseViewHolder> {

    private var isDone: Boolean = false

    constructor(data: MutableList<TodoSection>, isDone: Boolean) : super(R.layout.item_todo, R.layout.item_todo_head, data) {
        this.isDone = isDone
    }

    override fun convertHead(helper: BaseViewHolder, item: TodoSection) {
        helper.setText(R.id.tv_date, item.header)
        if (isDone) {
            helper.setBackgroundColor(R.id.tv_date, mContext.resources.getColor(R.color.color_done_light))
            helper.setTextColor(R.id.tv_date, mContext.resources.getColor(R.color.color_done))
        } else {
            helper.setBackgroundColor(R.id.tv_date, mContext.resources.getColor(R.color.color_todo_light))
            helper.setTextColor(R.id.tv_date, mContext.resources.getColor(R.color.color_todo))
        }
    }

    override fun convert(helper: BaseViewHolder, item: TodoSection) {
        if (isDone) {
            helper.setImageResource(R.id.iv_done, R.mipmap.ic_reset)
            helper.setVisible(R.id.tv_date_done, true)
            helper.setText(R.id.tv_date_done, "完成时间：" + item.t.completeDateStr)
        } else {
            helper.setImageResource(R.id.iv_done, R.mipmap.ic_complete)
            helper.setVisible(R.id.tv_date_done, false)
        }
        helper.setText(R.id.tv_title, item.t.title)
        if (TextUtils.isEmpty(item.t.content)) {
            helper.setVisible(R.id.tv_content, false)
        } else {
            helper.setVisible(R.id.tv_content, true)
            helper.setText(R.id.tv_content, item.t.content)
        }

        // 添加点击事件
        helper.addOnClickListener(R.id.iv_done)
        helper.addOnClickListener(R.id.iv_delete)
    }
}