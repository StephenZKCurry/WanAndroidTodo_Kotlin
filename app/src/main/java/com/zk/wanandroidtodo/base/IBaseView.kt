package com.zk.wanandroidtodo.base

/**
 * @description:     View基类接口
 * @author:         zhukai
 * @date:     2018/8/31 10:25
 */
interface IBaseView {

    /**
     * 显示等待dialog
     */
    fun showLoadingDialog(message: String)

    /**
     * 隐藏等待dialog
     */
    fun hideLoadingDialog()

    /**
     * 弹出toast
     */
    fun showToast(message: String)
}