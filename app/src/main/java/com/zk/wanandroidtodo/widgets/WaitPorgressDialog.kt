package com.zk.wanandroidtodo.widgets

import android.app.ProgressDialog
import android.content.Context

/**
 * @description:     等待提示对话框
 * @author:         zhukai
 * @date:     2018/8/22 13:38
 */
class WaitPorgressDialog : ProgressDialog {

    constructor(context: Context) : this(context, 0) {
    }

    constructor(context: Context, theme: Int) : super(context, theme) {
        setCanceledOnTouchOutside(false)
    }
}